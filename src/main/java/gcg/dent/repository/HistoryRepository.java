package gcg.dent.repository;

import gcg.dent.entity.Card;
import gcg.dent.entity.Contract;
import gcg.dent.entity.History;
import gcg.dent.entity.Patient;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Singleton
public class HistoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private ContractRepository contractRepository;

    /**
     * Get medical {@link History} by {@link Patient} id
     * @param pid {@link Patient} id
     * @return list of {@link History}
     */
    @Transactional
    @ReadOnly
    public List<History> getByPatientId(Long pid) {
        return entityManager
                .createQuery("select H from History H join fetch H.card C join fetch C.patient P where P.id = :pid", History.class)
                .setParameter("pid", pid)
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public History findById(Long id) {
        return entityManager.find(History.class, id);
    }

    @Transactional
    public History addHistory(History history) {
        history.setId(null);
        history.setDate(new Date());

        Card card = entityManager.find(Card.class, history.getCid());
        Contract contract = contractRepository.makeContract();

        history.setCard(card);
        history.setContract(contract);
        entityManager.persist(history);
        return history;
    }

    @Transactional
    public History update(History history) {
        History originalHistory = findById(history.getId());
        originalHistory.setProps(history.getProps());
        entityManager.flush();
        return originalHistory;
    }
}
