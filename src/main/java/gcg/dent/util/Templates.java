package gcg.dent.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.TagType;
import com.github.jknack.handlebars.Template;
import gcg.dent.entity.*;
import gcg.dent.repository.*;
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
        handlebars.registerHelper("in_words", new InWordsHelper());
        handlebars.registerHelper("yesno", new YesNoHelper());
        handlebars.registerHelper("empty", new EmptyHelper());
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    CardRepository cardRepository;

    @Inject
    CompanyRepository companyRepository;

    @Inject
    ContractRepository contractRepository;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    HistoryRepository historyRepository;

    @Inject
    PatientRepository patientRepository;

    public String load(Document templateDocument, Map<String, Object> params) {
        try {
            Template template = handlebars.compileInline(templateDocument.getTemplate());
            List<String> variables = template.collect(TagType.VAR);
            variables.addAll(template.collect(TagType.TRIPLE_VAR));
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
        final StringBuilder actServiceTable = new StringBuilder();

        if (params.get("employee") != null) {
            doctor = employeeRepository.findById((Long) params.get("employee"));
        }
        if (params.get("patient") != null) {
            patient = patientRepository.findById((Long) params.get("patient"));
        }
        if (params.get("card") != null) {
            card = cardRepository.findById((Long) params.get("card"));
        }
        if (params.get("contract") != null) {
            contract = contractRepository.findById((Long) params.get("contract"));
            fullSumm = (Double) entityManager
                    .createNativeQuery("select coalesce(sum(s.price * c.amount), 0)::::double precision " +
                            "from act a " +
                            "join act_service c on a.id = c.aid " +
                            "join service s on c.sid = s.id " +
                            "where a.atid = s.atid and a.did = (:did)")
                    .setParameter("did", contract.getId())
                    .getSingleResult();
        }
        if (params.get("history") != null) {
            history = historyRepository.findById((Long) params.get("history"));
            if (params.get("card") == null) {
                card = cardRepository.findById(history.getCard().getId());
            }
            patient = patientRepository.findById(card.getPid());
            JsonNode props = history.getProps();
            Iterator<JsonNode> dent = props.get("dent").elements();
            dent.forEachRemaining(d -> {
                int tr = d.get("tr").asInt();
                int td = d.get("td").asInt();
                String half = d.get("half").asText();
                String value = d.get("value").asText();
                String key = half.substring(0, 1) + (tr > 0 ? ("u" + tr) : ("d" + (-tr))) + td;
                filledParams.put(key, value);
            });
            filledParams.put("diagnosis", props.get("diagnosis").asText());
            filledParams.put("complaints", props.get("complaints").asText());
            filledParams.put("gepatit", props.get("gepatit").asBoolean());
            filledParams.put("tuber", props.get("tuber").asBoolean());
            filledParams.put("pedi", props.get("pedi").asBoolean());
            filledParams.put("break", props.get("break").asText());
            filledParams.put("manipulation", props.get("manipulation").asText());
            filledParams.put("sick", props.get("sick").asText());
            filledParams.put("visit", props.get("visit").asText());
            filledParams.put("allergy", props.get("allergy").asText());
            filledParams.put("outer", props.get("outer").asText());
            filledParams.put("bite", props.get("bite").asText());
            filledParams.put("mucous", props.get("mucous").asText());
            filledParams.put("lab", props.get("lab").asText());
        }
        if (doctor == null && patient != null) {
            doctor = (Employee) entityManager
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

        if (vars.contains("act_service_table")) {
            List<Object[]> rows = entityManager
                    .createNativeQuery("select T.name, string_agg(S.name||' - '||A_S.amount||'шт', ', ') from act A " +
                            "join act_type t on A.atid = t.id " +
                            "left join act_service A_S on A.id = A_S.aid " +
                            "left join service s on A_S.sid = s.id " +
                            "where A.did = (:did) " +
                            "group by A.id, T.name;")
                    .setParameter("did", contract.getId())
                    .getResultList();
            final String tblHeader = "<w:tbl><w:tblPr><w:tblW w:w=\"0\" w:type=\"auto\"/><w:tblInd w:w=\"720\" w:type=\"dxa\"/><w:tblBorders><w:top w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:left w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:bottom w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:right w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:insideH w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:insideV w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/></w:tblBorders><w:tblLook w:val=\"04A0\"/></w:tblPr><w:tblGrid><w:gridCol w:w=\"4779\"/><w:gridCol w:w=\"4696\"/></w:tblGrid>";
            actServiceTable.append(tblHeader);
            rows.forEach(r -> {
                String tblRow = "<w:tr wsp:rsidR=\"00AB6F6F\" wsp:rsidRPr=\"00FA29E9\" wsp:rsidTr=\"00FA29E9\"><w:tc><w:tcPr><w:tcW w:w=\"5097\" w:type=\"dxa\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"auto\"/></w:tcPr><w:p wsp:rsidR=\"00AB6F6F\" wsp:rsidRPr=\"00FA29E9\" wsp:rsidRDefault=\"00AB6F6F\" wsp:rsidP=\"00FA29E9\"><w:pPr><w:pStyle w:val=\"a4\"/><w:spacing w:after=\"0\" w:line=\"240\" w:line-rule=\"auto\"/><w:ind w:left=\"0\"/><w:jc w:val=\"both\"/></w:pPr><w:r wsp:rsidRPr=\"00FA29E9\">" +
                        "<w:t>" + r[0].toString() + "</w:t></w:r></w:p></w:tc><w:tc><w:tcPr><w:tcW w:w=\"5098\" w:type=\"dxa\"/><w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"auto\"/></w:tcPr><w:p wsp:rsidR=\"00AB6F6F\" wsp:rsidRPr=\"00FA29E9\" wsp:rsidRDefault=\"00AB6F6F\" wsp:rsidP=\"00FA29E9\"><w:pPr><w:pStyle w:val=\"a4\"/><w:spacing w:after=\"0\" w:line=\"240\" w:line-rule=\"auto\"/><w:ind w:left=\"0\"/><w:jc w:val=\"both\"/></w:pPr><w:r wsp:rsidRPr=\"00FA29E9\">" +
                        "<w:t>" + r[1].toString() + "</w:t></w:r></w:p></w:tc></w:tr>";
                actServiceTable.append(tblRow);
            });
            actServiceTable.append("</w:tbl>");
        }

        filledParams.put("company", company);
        filledParams.put("patient", patient);
        filledParams.put("employee", doctor);
        filledParams.put("card", card);
        filledParams.put("contract", contract);
        filledParams.put("history", history);
        filledParams.put("act", act);

        filledParams.put("full_summ", String.format("%.2f", fullSumm));
        filledParams.put("act_service_table", actServiceTable.toString());

        logger.info("VARS={}, PARAMS={}", vars, params);
        return filledParams;
    }
}
