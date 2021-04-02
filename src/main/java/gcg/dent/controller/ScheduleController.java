package gcg.dent.controller;

import gcg.dent.entity.Client;
import gcg.dent.entity.Slot;
import gcg.dent.repository.ClientRepository;
import gcg.dent.repository.SlotRepository;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Controller("/schedule")
public class ScheduleController {

    @Inject
    SlotRepository slotRepository;

    @Inject
    ClientRepository clientRepository;

    @Get(uri="/{id}", produces = MediaType.APPLICATION_JSON)
    public Slot get(Long id) {
        return slotRepository.findById(id);
    }

    @Post(uri = "/add", produces = MediaType.APPLICATION_JSON)
    public Slot add(String fio, String phone, Date date, Integer time, Integer size) {
        List<Client> clients = clientRepository.findByFIO(fio);
        Client client = clients.stream()
                .filter(c -> phone.equals(c.getPhone()))
                .findFirst()
                .orElse(clientRepository.makeClient(fio, phone));
        Slot slot = slotRepository.makeSlot(date, time, size);
        slot.setClient(client);
        return slot;
    }

}
