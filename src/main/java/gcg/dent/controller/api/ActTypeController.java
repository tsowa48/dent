package gcg.dent.controller.api;

import gcg.dent.entity.ActType;
import gcg.dent.repository.ActTypeRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller("/api/act_type")
public class ActTypeController {

    @Inject
    ActTypeRepository actTypeRepository;

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public ActType get(Long id) {
        return actTypeRepository.findById(id);
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public List<ActType> getAll() {
        return actTypeRepository.getAll();
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public ActType add(ActType actType) {
        return actTypeRepository.addActType(actType);
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public ActType change(ActType actType) {
        return actTypeRepository.update(actType);
    }

    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse remove(Long id) {
        actTypeRepository.removeById(id);
        return HttpResponse.ok();
    }
}
