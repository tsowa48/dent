package gcg.dent.controller;

import gcg.dent.entity.Client;
import gcg.dent.repository.ClientRepository;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;
import java.util.List;

@Controller("/client")
public class ClientController {

    @Inject
    ClientRepository clientRepository;

    @Get(value = "/fetch", produces = MediaType.APPLICATION_JSON)
    public List<Client> subFind(String fioPart) {
        return clientRepository.findBySubFIO(fioPart);
    }
}
