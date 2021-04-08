package gcg.dent.repository;

import gcg.dent.entity.Employee;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public List<Employee> getScheduled() {
        return entityManager
                .createQuery("select E from Employee E where E.isScheduled = true order by E.fio", Employee.class)
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public Employee getById(Long id) {
        return entityManager.find(Employee.class, id);
    }
}
