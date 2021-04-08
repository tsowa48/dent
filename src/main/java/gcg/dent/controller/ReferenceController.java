package gcg.dent.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

@Controller("/reference")
public class ReferenceController {

    @View("reference")
    @Get
    public HttpResponse get() {
        return HttpResponse.ok();
    }

}
