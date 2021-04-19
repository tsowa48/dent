package gcg.dent.controller.api;

import gcg.dent.entity.History;
import gcg.dent.repository.HistoryRepository;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;

@Controller("/api/history")
public class HistoryController {

    @Inject
    HistoryRepository historyRepository;

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public History get(Long id) {
        return historyRepository.findById(id);
    }
}
