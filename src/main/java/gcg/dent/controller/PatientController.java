package gcg.dent.controller;

import gcg.dent.entity.*;
import gcg.dent.repository.*;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller("/patient")
public class PatientController {

    @Inject
    PatientRepository patientRepository;

    @Inject
    FindOutRepository findOutRepository;

    @Inject
    DocumentRepository documentRepository;

    @Inject
    ActTypeRepository actTypeRepository;

    @Inject
    EmployeeRepository employeeRepository;

    @View("patients")
    @Get
    public HttpResponse<Map<String, Object>> getAllPatient() {
        Map<String, Object> params = new HashMap<>();
        List<Patient> allPatients = patientRepository.getAll();
        List<FindOut> allFindOut = findOutRepository.getAll();
        params.put("patients", allPatients);
        params.put("findOut", allFindOut);
        return HttpResponse.ok(params);
    }

    @View("patient")
    @Get("/{id}")
    public HttpResponse<Map<String, Object>> getPatient(Long id) {
        Map<String, Object> params = new HashMap<>();
        Patient patient = patientRepository.findById(id);

        List<Document> docs = documentRepository.getAll();
        List<Document> otherDocs = docs.stream()
                .filter(d -> "other".equals(d.getType()))
                .collect(Collectors.toList());
        Document actUid = docs.stream()
                .filter(d -> "act".equals(d.getType()))
                .findFirst()
                .orElse(null);
        Document cardUid = docs.stream()
                .filter(d -> "card".equals(d.getType()))
                .findFirst()
                .orElse(null);
        Document contractUid = docs.stream()
                .filter(d -> "contract".equals(d.getType()))
                .findFirst()
                .orElse(null);

        List<ActType> actTypes = actTypeRepository.getAll();
        List<Employee> employee = employeeRepository.getScheduled();

        params.put("patient", patient);

        params.put("act_uid", actUid);
        params.put("card_uid", cardUid);
        params.put("contract_uid", contractUid);
        params.put("docs", otherDocs);

        params.put("act_type", actTypes);
        params.put("employee", employee);
        return HttpResponse.ok(params);
    }
}
