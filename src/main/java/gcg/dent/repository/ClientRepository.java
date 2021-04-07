package gcg.dent.repository;

import gcg.dent.entity.Client;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public List<Client> findByFIO(String fio) {
        return entityManager
                .createQuery("select C from Client C where lower(C.fio) = :fio")
                .setParameter("fio", fio.toLowerCase())
                .getResultList();
    }

    public Client makeClient(String fio, String phone) {
        Client client = new Client();
        client.setFio(fio);
        client.setPhone(phone);
        return client;
    }

    @Transactional
    @ReadOnly
    public List<Client> findBySubFIO(String subFio) {
        String subFioLike = "%" + subFio.replace(" ", "% %") + "%";
        return entityManager
                .createNativeQuery("select C.* from client C where C.fio like :subFio", Client.class)
                .setParameter("subFio", subFioLike)
                .getResultList();
    }

}
