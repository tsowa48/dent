package gcg.dent.controller.api;

import gcg.dent.dto.Stat;
import gcg.dent.repository.StatisticsRepository;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Controller("/api/stat")
public class StatController {

    @Inject
    StatisticsRepository statisticsRepository;

    @Get(uri = "/{year}-{month}-{day}", produces = MediaType.APPLICATION_JSON)
    public Map<String, List<Stat>> get(int day, int month, int year) {
        return statisticsRepository.getAll(day, month, year);
    }
}
