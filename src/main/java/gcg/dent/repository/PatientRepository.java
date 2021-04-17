package gcg.dent.repository;

import gcg.dent.entity.Patient;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class PatientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public List<Patient> getAll() {
        return entityManager
                .createQuery("select P from Patient P order by P.fio", Patient.class)
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public Patient findById(Long id) {
        return entityManager
                .createQuery("select P from Patient P left join fetch P.findOut " +
                        "left join fetch P.card C left join fetch C.history where P.id = :id", Patient.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public Patient addRecord(Patient patient) {
        patient.setId(null);
        entityManager.persist(patient);
        return patient;
    }

    @Transactional
    public Patient update(Patient patient) {
        entityManager.merge(patient);
        return patient;
    }

    @Transactional
    public void removeById(Long id) {
        entityManager
                .createQuery("delete from Patient P where P.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}