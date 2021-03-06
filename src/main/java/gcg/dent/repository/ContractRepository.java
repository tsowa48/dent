package gcg.dent.repository;

import gcg.dent.entity.Contract;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Singleton
public class ContractRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public Contract findById(Long id) {
        return entityManager.find(Contract.class, id);
    }

    @Transactional
    public Contract makeContract() {
        return (Contract)entityManager
                .createNativeQuery("insert into contract(number, date) " +
                        "select coalesce(max(C.number), 0) + 1, now() " +
                        "from contract C where extract(year from C.date) = extract(year from now()) " +
                        "returning *", Contract.class)
                .getSingleResult();
    }
}
