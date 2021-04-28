package gcg.dent.controller.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Put;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/api/sql")
public class SqlController {

    @PersistenceContext
    private EntityManager entityManager;

    static final ObjectMapper mapper = new ObjectMapper();

    @Put
    @Transactional
    public HttpResponse executeUpdate(String sql, JsonNode params) {
        HashMap<String, Object> mapParams = mapper.convertValue(params, new TypeReference<HashMap<String, Object>>() {});
        List<HashMap<String, Object>> arrParams = (List)mapParams.get("params");
        Query query = entityManager.createNativeQuery(sql);
        arrParams.forEach(p -> {
            Map.Entry<String, Object> entry = p.entrySet().iterator().next();
            query.setParameter(entry.getKey(), entry.getValue());
        });
        query.executeUpdate();
        return HttpResponse.ok();
    }
}
