package gcg.dent.controller;

import gcg.dent.repository.ReferenceRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.util.HashMap;

@Controller("/reference")
public class ReferenceController {

    @Inject
    ReferenceRepository referenceRepository;

    @View("reference")
    @Get
    public HttpResponse<HashMap<String, Object>> get() {
        HashMap<String, Object> params = referenceRepository.getCompany();


        return HttpResponse.ok(params);
    }

}
