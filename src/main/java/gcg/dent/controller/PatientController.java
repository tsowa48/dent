package gcg.dent.controller;

import gcg.dent.entity.Client;
import gcg.dent.entity.FindOut;
import gcg.dent.entity.Patient;
import gcg.dent.repository.ClientRepository;
import gcg.dent.repository.FindOutRepository;
import gcg.dent.repository.PatientRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/patient")
public class PatientController {

    @Inject
    PatientRepository patientRepository;

    @Inject
    ClientRepository clientRepository;

    @Inject
    FindOutRepository findOutRepository;

    @View("patients")
    @Get
    public HttpResponse<Map<String, Object>> getAllPatient() {
        Map<String, Object> params = new HashMap<>();
        List<Patient> allPatients = patientRepository.getAll();
        List<Client> unattachedClients = clientRepository.getUnattached();
        List<FindOut> allFindOut = findOutRepository.getAll();
        params.put("patients", allPatients);
        params.put("clients", unattachedClients);
        params.put("findOut", allFindOut);
        return HttpResponse.ok(params);
    }

    @View("patient")
    @Get("/{id}")
    public HttpResponse<Map<String, Object>> getPatient(Long id) {
        Map<String, Object> params = new HashMap<>();
        Patient patient = patientRepository.findById(id);
        params.put("patient", patient);
        return HttpResponse.ok(params);
    }
}
