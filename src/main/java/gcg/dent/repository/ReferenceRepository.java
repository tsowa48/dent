package gcg.dent.repository;

import gcg.dent.entity.Company;
import gcg.dent.entity.Employee;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;

@Singleton
public class ReferenceRepository {

    @Inject
    CompanyRepository companyRepository;

    @Inject
    EmployeeRepository employeeRepository;

    public HashMap<String, Object> getCompany() {
        HashMap<String, Object> params = new HashMap<>();
        List<Company> company = companyRepository.getAll();
        List<Employee> employee = employeeRepository.getAll();

        params.put("company", company);
        params.put("employee", employee);
        return params;
    }
}
