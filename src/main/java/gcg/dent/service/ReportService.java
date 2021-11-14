package gcg.dent.service;

import gcg.dent.entity.Document;
import io.micronaut.transaction.annotation.ReadOnly;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

@Singleton
public class ReportService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    EntityManager entityManager;

    Map<String, Function<JasperPrint, byte[]>> exporters = new HashMap<>();

    @PostConstruct
    private void fillExporters() {
        exporters.put("docx", docxReport);
    }


    Function<JasperPrint, byte[]> docxReport = (JasperPrint jasperPrint) -> {
        JRDocxExporter exporter = new JRDocxExporter();
        SimpleDocxReportConfiguration reportConfig = new SimpleDocxReportConfiguration();

        ByteArrayOutputStream ret = new ByteArrayOutputStream();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(ret));
        exporter.setConfiguration(reportConfig);
        try {
            exporter.exportReport();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }
        return ret.toByteArray();
    };


    @Transactional
    @ReadOnly
    public Pair<String, byte[]> getReport(Document document, String type, Map<String, Object> parameters) throws JRException {
        java.sql.Connection conn = entityManager.unwrap(SessionImpl.class).connection();
        InputStream is = new ByteArrayInputStream(document.getJrxml().getBytes());
        JasperReport jasperReport = JasperCompileManager.compileReport(is);
        String name = jasperReport.getName();
        parameters.put("REPORT_NAME", document.getName());
        parameters.put(JRParameter.REPORT_LOCALE, new Locale("ru", "RU"));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
        if (exporters.containsKey(type.toLowerCase())) {
            return new MutablePair<>(name, exporters.get(type.toLowerCase()).apply(jasperPrint));
        }
        return new MutablePair<>(name, new byte[]{});
    }
}
