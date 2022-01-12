package gcg.dent.util;

import gcg.dent.service.TraceService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Requires(property = "tracer.enabled", value = "true")
@Filter("/**")
public class Tracer implements HttpServerFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TraceService traceService;

    public Tracer(TraceService traceService) {
        this.traceService = traceService;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        final String realIP = request.getHeaders().get("X-Real-IP");
        final String remoteAddress = realIP != null ? realIP : request.getRemoteAddress().getHostString();
        if(!"127.0.0.1".equals(remoteAddress)) {
            Optional<?> body = request.getBody();
            if (body.isPresent()) {
                logger.info("{} {} {}: {}", remoteAddress, request.getMethod(), request.getUri(), body.get());
            } else {
                logger.info("{} {} {}", remoteAddress, request.getMethod(), request.getUri());
            }
        }

        return traceService.trace(request)
                .switchMap(aBoolean -> chain.proceed(request))
                .doOnNext(res -> {
                            
                        }
                );
    }
}
