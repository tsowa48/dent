package gcg.dent.repository;

import gcg.dent.entity.Card;
import gcg.dent.entity.Patient;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Singleton
public class CardRepository {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get medical {@link Card} by {@link Patient} id
     * @param pid {@link Patient} id
     * @return {@link Card}
     */
    @Transactional
    @ReadOnly
    public Card findByPatientId(Long pid) {
        return entityManager
                .createQuery("select C from Card C join fetch C.patient P where P.id = :pid", Card.class)
                .setParameter("pid", pid)
                .getSingleResult();
    }

    @Transactional
    public Card makeNew(Patient patient) {
        return (Card) entityManager.createNativeQuery("insert into card(number, \"date\", pid, cid) " +
                "values ((select max(number) + 1 from card), now(), (:pid), (select id from company order by name limit 1)) " +
                "returning *;", Card.class)
                .setParameter("pid", patient.getId())
                .getSingleResult();
    }
}
