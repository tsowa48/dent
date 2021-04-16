package gcg.dent.controller;

import gcg.dent.entity.Card;
import gcg.dent.entity.Client;
import gcg.dent.entity.History;
import gcg.dent.entity.Patient;
import gcg.dent.repository.CardRepository;
import gcg.dent.repository.ClientRepository;
import gcg.dent.repository.HistoryRepository;
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
    CardRepository cardRepository;

    @Inject
    HistoryRepository historyRepository;

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
        Card card = cardRepository.findByPatientId(id);
        List<History> history = historyRepository.getByPatientId(patient.getId());
        params.put("patient", patient);
        params.put("card", card);
        params.put("history", history);
        return HttpResponse.ok(params);
    }
}
