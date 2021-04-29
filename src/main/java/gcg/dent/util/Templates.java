package gcg.dent.util;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.TagType;
import com.github.jknack.handlebars.Template;
import gcg.dent.entity.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class Templates {
    private static final Logger logger = LoggerFactory.getLogger(Templates.class);
    private static Handlebars handlebars = new Handlebars();

    @PersistenceContext
    private EntityManager entityManager;

    public String load(Document templateDocument, Map<String, Object> params) {
        try {
            Template template = handlebars.compileInline(templateDocument.getTemplate());
            List<String> variables = template.collect(TagType.VAR);
            return template.apply(variablesToParams(variables, params));
        } catch(IOException iex) {
            logger.error("Can't compile raw template '{}'", templateDocument.getName(), iex);
            throw new RuntimeException();
        }
    }

    private Map<String, Object> variablesToParams(List<String> vars, Map<String, Object> params) {
        Map<String, Object> filledParams = new HashMap<>();
        //TODO: map 'vars' through sql by 'params'
        return filledParams;
    }
}
