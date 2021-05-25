package gcg.dent.controller.api;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Error;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/api/js")
public class ErrorController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Post("/error")
    public HttpResponse jsError(String msg, String url, Long line) {
        logger.error("JS error in '{}' at line {}: \"{}\"", url, line, msg);
        return HttpResponse.ok();
    }

    @Error(global = true)
    public HttpResponse appError(HttpRequest<Object> request, Exception ex) {
        String stacktrace = ExceptionUtils.getStackTrace(ex);
        logger.error("Request: {}, ExceptionWithStacktrace: {}", request, stacktrace, ex);
        return HttpResponse.ok();
    }
}
