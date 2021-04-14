package gcg.dent.repository;

import gcg.dent.entity.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class ReferenceRepository {

    @Inject
    CompanyRepository companyRepository;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    ScheduleRepository scheduleRepository;

    @Inject
    ActTypeRepository actTypeRepository;

    @Inject
    ServiceRepository serviceRepository;

    @Inject
    ManipulationRepository manipulationRepository;

    public Map<String, Object> getReferences() {
        HashMap<String, Object> params = new HashMap<>();
        List<Company> company = companyRepository.getAll();
        List<Employee> employee = employeeRepository.getAll();
        List<Schedule> schedule = scheduleRepository.getAll();
        List<ActType> actType = actTypeRepository.getAll();
        List<Service> service = serviceRepository.getAll();
        List<Manipulation> manipulation = manipulationRepository.getAll();

        params.put("company", company);
        params.put("schedule", schedule);
        params.put("employee", employee);
        params.put("act_type", actType);
        params.put("service", service);
        params.put("manipulation", manipulation);
        return params;
    }
}
