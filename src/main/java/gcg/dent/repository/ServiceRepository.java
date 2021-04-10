package gcg.dent.repository;

import gcg.dent.entity.Service;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ServiceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public List<Service> getAll() {
        return entityManager
                .createQuery("select S from Service S", Service.class)
                .getResultList();
    }
}
