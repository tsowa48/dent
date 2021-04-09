package gcg.dent.repository;

import gcg.dent.entity.Company;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class CompanyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Company addCompany(Company company) {
        company.setId(null);
        entityManager.persist(company);
        return company;
    }

    @Transactional
    @ReadOnly
    public Company findById(Long id) {
        return entityManager.find(Company.class, id);
    }

    @Transactional
    public void removeById(Long id) {
        entityManager.createQuery("delete from Company C where C.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Transactional
    public Company update(Company company) {
        entityManager.merge(company);
        return company;
    }

    @Transactional
    @ReadOnly
    public List<Company> getAll() {
        return entityManager.createQuery("select C from Company C order by C.name").getResultList();
    }
}