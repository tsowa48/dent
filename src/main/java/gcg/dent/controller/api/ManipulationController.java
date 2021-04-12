package gcg.dent.controller.api;

import gcg.dent.entity.Manipulation;
import gcg.dent.repository.ManipulationRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller("/api/manipulation")
public class ManipulationController {

    @Inject
    ManipulationRepository manipulationRepository;

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Manipulation get(Long id) {
        return manipulationRepository.findById(id);
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public List<Manipulation> getAll() {
        return manipulationRepository.getAll();
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public Manipulation add(Manipulation manipulation) {
        return manipulationRepository.addRecord(manipulation);
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public Manipulation change(Manipulation manipulation) {
        return manipulationRepository.update(manipulation);
    }

    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse remove(Long id) {
        manipulationRepository.removeById(id);
        return HttpResponse.ok();
    }

}