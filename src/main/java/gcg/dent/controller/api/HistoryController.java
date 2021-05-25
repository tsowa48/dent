package gcg.dent.controller.api;

import gcg.dent.entity.History;
import gcg.dent.repository.HistoryRepository;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;

import javax.inject.Inject;

@Controller("/api/history")
public class HistoryController {

    @Inject
    HistoryRepository historyRepository;

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public History get(Long id) {
        return historyRepository.findById(id);
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public History add(History history) {
        return historyRepository.addHistory(history);
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public History change(History history) {
        return historyRepository.update(history);
    }
}
