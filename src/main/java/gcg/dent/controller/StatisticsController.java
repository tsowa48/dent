package gcg.dent.controller;

import gcg.dent.repository.StatisticsRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Controller("/statistics")
public class StatisticsController {

    @Inject
    StatisticsRepository statisticsRepository;

    @View("statistics")
    @Get
    public HttpResponse<Map<String, Object>> getStatistics() {
        Map<String, Object> params = new HashMap<>();
        return HttpResponse.ok(params);
    }
}
