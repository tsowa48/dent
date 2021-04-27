package gcg.dent.controller.api;

import gcg.dent.entity.Patient;
import gcg.dent.repository.PatientRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller("/api/patient")
public class PatientController {

    @Inject
    PatientRepository patientRepository;

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Patient get(Long id) {
        return patientRepository.findById(id);
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public List<Patient> getAll() {
        return patientRepository.getAll();
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public Patient add(Patient patient) {
        return patientRepository.addRecord(patient);
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public Patient change(Patient patient) {
        return patientRepository.update(patient);
    }

    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse remove(Long id) {
        patientRepository.removeById(id);
        return HttpResponse.ok();
    }

    @Post(value = "/find", produces = MediaType.APPLICATION_JSON)
    public List<Patient> findByFio(String fio) {
        return patientRepository.findByFio(fio);
    }

}