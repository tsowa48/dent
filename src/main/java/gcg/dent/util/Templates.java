package gcg.dent.util;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.TagType;
import com.github.jknack.handlebars.Template;
import gcg.dent.entity.*;
import gcg.dent.repository.CompanyRepository;
import gcg.dent.repository.PatientRepository;
import gcg.dent.util.helpers.*;
import gcg.word.JustifyContent;
import gcg.word.Paragraph;
import gcg.word.table.TableColumn;
import gcg.word.table.TableRow;
import gcg.word.util.Rsid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Singleton
public class Templates {
    private static final Logger logger = LoggerFactory.getLogger(Templates.class);
    private static Handlebars handlebars = new Handlebars();

    static {
        handlebars.registerHelper("nullable", new NullableHelper());
        handlebars.registerHelper("declension", new DeclensionHelper());
        handlebars.registerHelper("join", new JoinHelper(", "));
        handlebars.registerHelper("sum", new MathHelper(MathHelper.SIGN.PLUS));
        handlebars.registerHelper("mult", new MathHelper(MathHelper.SIGN.MULT));
        handlebars.registerHelper("date", new DateHelper(new SimpleDateFormat("«dd» MMMM yyyy г.")));
        handlebars.registerHelper("lower", new ToCaseHelper(ToCaseHelper.CASE.LOWER));
        handlebars.registerHelper("upper", new ToCaseHelper(ToCaseHelper.CASE.UPPER));
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    CompanyRepository companyRepository;

    @Inject
    PatientRepository patientRepository;

    public String load(Document templateDocument, Map<String, Object> params) {
        try {
            Template template = handlebars.compileInline(templateDocument.getTemplate());
            List<String> variables = template.collect(TagType.VAR);
            return template.apply(variablesToParams(variables, params));
        } catch (IOException iex) {
            logger.error("Can't compile raw template '{}'", templateDocument.getName(), iex);
            throw new RuntimeException();
        }
    }

    @Transactional
    Map<String, Object> variablesToParams(List<String> vars, Map<String, Object> params) {
        Map<String, Object> filledParams = new HashMap<>();

        Company company = companyRepository.getAll().get(0);
        Employee doctor = null;
        Patient patient = null;
        Card card = null;
        Contract contract = null;
        History history = null;
        Act act = null;

        Double fullSumm = 0.00;

        if (params.get("employee") != null) {
            doctor = entityManager.find(Employee.class, params.get("employee"));
        }
        if (params.get("patient") != null) {
            patient = patientRepository.findById((Long) params.get("patient"));
            doctor = (Employee)entityManager
                    .createNativeQuery("select D.* from employee D " +
                            "join slot S on S.doc = D.id " +
                            "left join client C on C.id = S.cid " +
                            "left join patient P on P.id = C.pid " +
                            "where P.id = :id " +
                            "order by S.date desc, S.time desc", Employee.class)
                    .setParameter("id", patient.getId())
                    .setMaxResults(1)
                    .getSingleResult();
        }
        if (params.get("card") != null) {
            card = entityManager.find(Card.class, params.get("card"));
        }
        if (params.get("contract") != null) {
            contract = entityManager.find(Contract.class, params.get("contract"));
            fullSumm = (Double)entityManager
                    .createNativeQuery("select sum(s.price * c.amount) " +
                            "from act a " +
                            "join act_service c on a.id = c.aid " +
                            "join service s on c.sid = s.id " +
                            "where a.atid = s.atid and a.did = (:did)")
                    .setParameter("did", contract.getId())
                    .getSingleResult();
        }
        if (params.get("history") != null) {
            history = entityManager.find(History.class, params.get("history"));
        }
        if (params.get("act") != null) {
            act = entityManager.find(Act.class, params.get("act"));
            fullSumm = (Double) entityManager
                    .createNativeQuery("select sum(s.price * c.amount)::::double precision " +
                            "from act a " +
                            "join act_service c on a.id = c.aid " +
                            "join service s on c.sid = s.id " +
                            "where a.atid = s.atid and a.id = (:aid)")
                    .setParameter("aid", act.getId())
                    .getSingleResult();

            List<TableRow> tblServiceRows = new ArrayList<>();
            List<TableRow> tblManipulationRows = new ArrayList<>();
            final Rsid rsid1 = new Rsid("00E62517", "0064090B", "00E62517", "00E62517", "");
            final Rsid rsid2 = new Rsid("0064090B", "0064090B", "", "", "00E62517");
            final Rsid rsid3 = new Rsid("0064090B", "0064090B", "", "", "0064090B");
            act.getActServices().forEach(actService -> {
                Service service = actService.getService();
                String name = service.getName();
                final Integer amount = actService.getAmount();
                Double price = service.getPrice();
                Double summ = amount * price;
                TableColumn tcName = new TableColumn(new Paragraph(rsid1, "Times New Roman", 24, false, JustifyContent.LEFT, name), 4957);
                TableColumn tcAmount = new TableColumn(new Paragraph(rsid1, "Times New Roman", 24, false, JustifyContent.LEFT, String.format("%d", amount)), 1701);
                TableColumn tcPrice = new TableColumn(new Paragraph(rsid1, "Times New Roman", 24, false, JustifyContent.LEFT, String.format("%.2f", price)), 1701);
                TableColumn tcSumm = new TableColumn(new Paragraph(rsid1, "Times New Roman", 24, false, JustifyContent.LEFT, String.format("%.2f", summ)), 1553);
                tblServiceRows.add(new TableRow(rsid2, Arrays.asList(tcName, tcAmount, tcPrice, tcSumm)));

                service.getManipulations().forEach(manipulation -> {
                    String mName = manipulation.getName();
                    String mDate = ObjectUtils.dateFormat.format(actService.getDate());
                    TableColumn tcMDate = new TableColumn(new Paragraph(rsid1, "Times New Roman", 24, false, JustifyContent.LEFT, mDate), 2143);
                    TableColumn tcMName = new TableColumn(new Paragraph(rsid1, "Times New Roman", 24, false, JustifyContent.LEFT, mName), 7769);

                    tblManipulationRows.add(new TableRow(rsid3, Arrays.asList(tcMDate, tcMName)));
                });
            });

            filledParams.put("tblServiceRows", tblServiceRows);
            filledParams.put("tblManipulationRows", tblManipulationRows);
        }

        filledParams.put("company", company);
        filledParams.put("patient", patient);
        filledParams.put("employee", doctor);
        filledParams.put("card", card);
        filledParams.put("contract", contract);
        filledParams.put("history", history);
        filledParams.put("act", act);

        filledParams.put("full_summ", String.format("%.2f", fullSumm));

        logger.info("VARS={}, PARAMS={}", vars, params);
        return filledParams;
    }
}
