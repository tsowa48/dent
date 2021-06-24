package gcg.dent.controller.api;

import gcg.dent.entity.Service;
import gcg.dent.repository.ServiceRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller("/api/service")
public class ServiceController {

    @Inject
    ServiceRepository serviceRepository;

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Service get(Long id) {
        return serviceRepository.findById(id);
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public List<Service> getAll() {
        return serviceRepository.getAll();
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public Service add(Service service) {
        return serviceRepository.addRecord(service);
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public Service change(Service service) {
        return serviceRepository.update(service);
    }

    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse remove(Long id) {
        serviceRepository.removeById(id);
        return HttpResponse.ok();
    }

    @Get(uri = "/type/{actType}", produces = MediaType.APPLICATION_JSON)
    public List<Service> getByActType(Long actType) {
        return serviceRepository.getByActType(actType);
    }
}