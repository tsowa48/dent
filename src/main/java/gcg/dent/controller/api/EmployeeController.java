package gcg.dent.controller.api;

import gcg.dent.entity.Employee;
import gcg.dent.repository.EmployeeRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller("/api/employee")
public class EmployeeController {

    @Inject
    EmployeeRepository employeeRepository;

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Employee get(Long id) {
        return employeeRepository.findById(id);
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public Employee add(Employee employee) {
        return employeeRepository.addEmployee(employee);
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public Employee change(Employee employee) {
        return employeeRepository.update(employee);
    }

    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse remove(Long id) {
        employeeRepository.removeById(id);
        return HttpResponse.ok();
    }

}