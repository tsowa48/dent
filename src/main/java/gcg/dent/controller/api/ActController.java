package gcg.dent.controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import gcg.dent.entity.*;
import gcg.dent.repository.*;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller("/api/act")
public class ActController {

    @Inject
    ActRepository actRepository;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    ActTypeRepository actTypeRepository;

    @Inject
    ContractRepository contractRepository;

    @Inject
    ServiceRepository serviceRepository;

    @Post(produces = MediaType.APPLICATION_JSON)
    public Act add(JsonNode rawAct) throws ParseException {
        Act act = new Act();
        Long aid = rawAct.get("aid").asLong();
        Long did = rawAct.get("did").asLong();
        Long atid = rawAct.get("atid").asLong();
        Long doc = rawAct.get("doc").asLong();
        Long number = rawAct.get("number").asLong();
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(rawAct.get("date").asText());
        Iterator<JsonNode> services = rawAct.get("services").elements();

        Employee doctor = employeeRepository.findById(doc);
        ActType actType = actTypeRepository.findById(atid);
        Contract contract = contractRepository.findById(did);

        if(aid != 0) {
            act.setId(aid);
        }
        act.setContract(contract);
        act.setActType(actType);
        act.setDoctor(doctor);
        act.setNumber(number);
        act.setDate(date);
        final Act result = actRepository.update(act);

        List<ActService> actServices = new ArrayList<>();
        services.forEachRemaining(s -> {
            Long sid = s.get("sid").asLong();
            Integer count = s.get("count").asInt();
            Service service = serviceRepository.findById(sid);
            ActService actService = new ActService();
            actService.setAmount(count);
            actService.setService(service);
            actService.setDate(result.getDateAsDate());
            actService.setAct(result);
            actServices.add(actService);
        });
        actRepository.batchUpdate(actServices);
        return result;
    }

    @Get("/{id}")
    public Act get(Long id) {
        return actRepository.get(id);
    }
}
