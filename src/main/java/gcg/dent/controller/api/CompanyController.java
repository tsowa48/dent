package gcg.dent.controller.api;

import gcg.dent.entity.Company;
import gcg.dent.repository.CompanyRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller("/api/company")
public class CompanyController {

    @Inject
    CompanyRepository companyRepository;

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Company get(Long id) {
        return companyRepository.findById(id);
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public List<Company> getAll() {
        return companyRepository.getAll();
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public Company add(Company company) {
        return companyRepository.addCompany(company);
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public Company change(Company company) {
        return companyRepository.update(company);
    }

    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse remove(Long id) {
        companyRepository.removeById(id);
        return HttpResponse.ok();
    }

}