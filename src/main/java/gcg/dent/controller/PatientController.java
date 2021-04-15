package gcg.dent.controller;

import gcg.dent.entity.Client;
import gcg.dent.entity.Patient;
import gcg.dent.repository.ClientRepository;
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

    @View("patients")
    @Get
    public HttpResponse<Map<String, Object>> getAllPatient() {
        Map<String, Object> params = new HashMap<>();
        List<Patient> allPatients = patientRepository.getAll();
        List<Client> unattachedClients = clientRepository.getUnattached();
        params.put("patients", allPatients);
        params.put("clients", unattachedClients);
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
