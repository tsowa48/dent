package gcg.dent.controller.api;

import gcg.dent.entity.Client;
import gcg.dent.entity.Employee;
import gcg.dent.entity.Slot;
import gcg.dent.repository.ClientRepository;
import gcg.dent.repository.EmployeeRepository;
import gcg.dent.repository.SlotRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Time;
import java.util.Date;

@Controller("/api/slot")
public class SlotController {

    @Inject
    SlotRepository slotRepository;

    @Inject
    ClientRepository clientRepository;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    EntityManager entityManager;

    @Get(uri="/{id}", produces = MediaType.APPLICATION_JSON)
    public Slot get(Long id) {
        return slotRepository.findById(id);
    }

    @Transactional
    @Post(uri = "/add", produces = MediaType.APPLICATION_JSON)
    public Slot add(String fio, String phone, Long doc, Date date, String time, Integer size) {
        Client client = clientRepository.find(fio, phone);
        Employee doctor = employeeRepository.getById(doc);
        String sizeTime = String.format("%02d:%02d:00", (size / 60), size % 60);
        Slot slot = slotRepository.makeSlot(date, Time.valueOf(time + ":00"), Time.valueOf(sizeTime));
        slot.setClient(client);
        slot.setDoctor(doctor);
        entityManager.persist(slot);
        return slot;
    }

    @Delete(uri="/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse remove(Long id) {
        slotRepository.removeById(id);
        return HttpResponse.ok();
    }

}
