package gcg.dent.repository;

import gcg.dent.entity.Client;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
public class ClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Client> findByFIO(String fio) {
        return entityManager
                .createQuery("select C from Client C where C.FIO = :fio")
                .setParameter("fio", fio)
                .getResultList();
    }

    public Client makeClient(String fio, String phone) {
        Client client = new Client();
        client.setFio(fio);
        client.setPhone(phone);
        entityManager.persist(client);
        return client;
    }

    public List<Client> findBySubFIO(String subFio) {
        String subFioLike = "%" + subFio.replace(" ", "% %") + "%";
        return entityManager
                .createNativeQuery("select C.* from client C where C.fio like :subFio", Client.class)
                .setParameter("subFio", subFioLike)
                .getResultList();
    }

}
