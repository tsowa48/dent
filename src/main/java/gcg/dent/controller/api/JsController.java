package gcg.dent.controller.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/api/js")
public class JsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Post("/error")
    public HttpResponse jsError(String msg, String url, Long line) {
        logger.error("JS error in '{}' at line {}: \"{}\"", url, line, msg);
        return HttpResponse.ok();
    }
}
