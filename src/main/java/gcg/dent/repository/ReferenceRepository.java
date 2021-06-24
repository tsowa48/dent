package gcg.dent.repository;

import gcg.dent.entity.*;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class ReferenceRepository {

    @PersistenceContext
    private EntityManager entityManager;

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

    @Inject
    DocumentRepository documentRepository;

    public Map<String, Object> getReferences() {
        HashMap<String, Object> params = new HashMap<>();
        List<Company> company = companyRepository.getAll();
        List<Employee> employee = employeeRepository.getAll();
        List<Schedule> schedule = scheduleRepository.getAll();
        List<ActType> actType = actTypeRepository.getAll();
        List<Service> service = serviceRepository.getAll();
        List<Manipulation> manipulation = manipulationRepository.getAll();
        List<Document> document = documentRepository.getAll();

        params.put("company", company);
        params.put("schedule", schedule);
        params.put("employee", employee);
        params.put("act_type", actType);
        params.put("service", service);
        params.put("manipulation", manipulation);
        params.put("document", document);
        return params;
    }

    @Transactional
    @ReadOnly
    public Boolean isEmpty() {
        return (Integer)entityManager
                .createNativeQuery("select count(*)::::int from (select S.id, E.id, C.id from schedule S, employee E, company C limit 1) R;")
                .getSingleResult() == 0;
    }
}
