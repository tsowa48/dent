package gcg.dent.controller.api;

import gcg.dent.entity.FindOut;
import gcg.dent.repository.FindOutRepository;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller("/api/find_out")
public class FindOutController {

    @Inject
    FindOutRepository findOutRepository;

    @Get(produces = MediaType.APPLICATION_JSON)
    public List<FindOut> getAll() {
        return findOutRepository.getAll();
    }
}