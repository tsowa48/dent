package gcg.dent.repository;

import gcg.dent.entity.Patient;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class PatientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    CardRepository cardRepository;

    @Transactional
    @ReadOnly
    public List<Patient> getAll() {
        return entityManager
                .createQuery("select P from Patient P order by P.fio", Patient.class)
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public List<Patient> findByFio(String fioPart) {
        return entityManager
                .createQuery("select new Patient(P.id, P.fio, P.phone, P.address, P.birth, P.isMale, P.findOut) from Patient P " +
                        "where lower(P.fio) like (:fioPart) " +
                        "order by P.fio", Patient.class)
                .setParameter("fioPart", "%" + fioPart.toLowerCase() + "%")
                .setMaxResults(10)
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public Patient findById(Long id) {
        return entityManager
                .createQuery("select P from Patient P " +
                        "left join fetch P.findOut F " +
                        "left join fetch P.card C " +
                        "left join fetch C.history H " +
                        "left join fetch H.contract D " +
                        "where P.id = :id order by D.date desc", Patient.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public Patient addRecord(Patient patient) {
        patient.setId(null);
        entityManager.persist(patient);
        entityManager.flush();
        cardRepository.makeNew(patient);
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