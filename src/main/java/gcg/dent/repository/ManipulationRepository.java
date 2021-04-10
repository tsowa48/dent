package gcg.dent.repository;

import gcg.dent.entity.Manipulation;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ManipulationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public List<Manipulation> getAll() {
        return entityManager
                .createQuery("select S from Manipulation S", Manipulation.class)
                .getResultList();
    }
}
