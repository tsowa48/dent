package gcg.dent.controller.api;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import gcg.dent.entity.Patient;
import gcg.dent.entity.Employee;
import gcg.dent.entity.Slot;
import gcg.dent.repository.EmployeeRepository;
import gcg.dent.repository.PatientRepository;
import gcg.dent.repository.SlotRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Time;
import java.util.Date;
import java.util.Optional;

@Controller("/api/slot")
public class SlotController {
    private static final Logger logger = LoggerFactory.getLogger(SlotController.class);

    @Inject
    SlotRepository slotRepository;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    PatientRepository patientRepository;

    @Inject
    EntityManager entityManager;

    @Get(uri="/{id}", produces = MediaType.APPLICATION_JSON)
    public Slot get(Long id) {
        return slotRepository.findById(id);
    }

    @Transactional
    @Post(uri = "/add", produces = MediaType.APPLICATION_JSON)
    public Slot add(Long doc, Date date, String time, Integer size, Optional<String> note, Patient patient) throws Exception {
        try {
            Employee doctor = employeeRepository.findById(doc);
            String sizeTime = String.format("%02d:%02d:00", (size / 60), size % 60);
            Slot slot = slotRepository.makeSlot(date, Time.valueOf(time + ":00"), Time.valueOf(sizeTime));
            slot.setDoctor(doctor);
            if (StringUtils.isNotBlank(patient.getFio())) {
                if(patient.getId() == null || patient.getId() == 0) {
                    patient = patientRepository.addRecord(patient);
                }
                slot.setNote(note.orElse(""));
                slot.setPatient(patient);
                slot.setEnabled(true);
            }
            entityManager.persist(slot);
            return slot;
        } catch(Exception ex) {
            String stacktrace = ExceptionUtils.getStackTrace(ex);
            logger.error("Can't add slot: {}", stacktrace, ex);
            throw new Exception("Can't add slot");
        }
    }

    @Delete(uri="/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse remove(Long id) {
        slotRepository.removeById(id);
        return HttpResponse.ok();
    }

}
