package gcg.dent.repository;

import gcg.dent.entity.Company;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;

@Singleton
public class ReferenceRepository {

    @Inject
    CompanyRepository companyRepository;

    public HashMap<String, Object> getCompany() {
        HashMap<String, Object> params = new HashMap<>();
        List<Company> company = companyRepository.getAllCompany();
        params.put("company", company);
        return params;
    }
}
