package gcg.dent.controller;

import gcg.dent.repository.ReferenceRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.util.Map;

@Controller("/reference")
public class ReferenceController {

    @Inject
    ReferenceRepository referenceRepository;

    @View("reference")
    @Get
    public HttpResponse<Map<String, Object>> get() {
        Map<String, Object> params = referenceRepository.getReferences();

        return HttpResponse.ok(params);
    }

}
