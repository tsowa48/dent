package gcg.dent.repository;

import gcg.dent.entity.Client;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Singleton
public class ClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Client makeClient(String fio, String phone) {
        Client client = new Client();
        client.setFio(fio);
        client.setPhone(phone);
        entityManager.persist(client);
        return client;
    }

    @Transactional
    @ReadOnly
    public Client findById(Long id) {
        return entityManager.find(Client.class, id);
    }

    @Transactional
    public Client find(String fio, String phone) {
        try {
            String normalizedPhone = phone.replaceAll("[^0-9]", "");
            return (Client)entityManager
                    .createNativeQuery("select * from client where lower(fio) ~* (:fio) and regexp_replace(phone, '[^0-9]', '', 'g') = (:phone)", Client.class)
                    .setParameter("fio", fio.toLowerCase())
                    .setParameter("phone", normalizedPhone)
                    .getSingleResult();
        } catch(Exception ex) {
            return makeClient(fio, phone);
        }
    }
}
