package gcg.dent.util;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.TagType;
import com.github.jknack.handlebars.Template;
import gcg.dent.entity.*;
import gcg.dent.repository.CompanyRepository;
import gcg.dent.repository.PatientRepository;
import gcg.dent.util.helpers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class Templates {
    private static final Logger logger = LoggerFactory.getLogger(Templates.class);
    private static Handlebars handlebars = new Handlebars();

    static {
        handlebars.registerHelper("nullable", new NullableHelper());
        handlebars.registerHelper("declension", new DeclensionHelper());
        handlebars.registerHelper("join", new JoinHelper(", "));
        handlebars.registerHelper("sum", new MathHelper("+"));
        handlebars.registerHelper("mult", new MathHelper("*"));
        handlebars.registerHelper("date", new DateHelper(new SimpleDateFormat("«dd» MMMM yyyy г.")));
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
        }
        if (params.get("history") != null) {
            history = entityManager.find(History.class, params.get("history"));
        }

        filledParams.put("company", company);
        filledParams.put("patient", patient);
        filledParams.put("employee", doctor);
        filledParams.put("card", card);
        filledParams.put("contract", contract);
        filledParams.put("history", history);

        logger.info("VARS={}, PARAMS={}", vars, params);
        return filledParams;
    }
}
