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
                .createQuery("select E from Employee E where E.isScheduled = true and E.faired = false order by E.fio", Employee.class)
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public List<Employee> getAll() {
        return entityManager
                .createQuery("select E from Employee E order by E.faired, E.fio")
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public Employee findById(Long id) {
        return entityManager.find(Employee.class, id);
    }

    @Transactional
    public Employee addEmployee(Employee employee) {
        employee.setId(null);
        entityManager.persist(employee);
        return employee;
    }

    @Transactional
    public void removeById(Long id) {
        entityManager.createQuery("delete from Employee E where E.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Transactional
    public Employee update(Employee employee) {
        entityManager.merge(employee);
        return employee;
    }

    @Transactional
    @ReadOnly
    public Boolean isEmpty() {
        return entityManager
                .createQuery("select E from Employee E")
                .setMaxResults(1)
                .getResultList()
                .isEmpty();
    }
}
