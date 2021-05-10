package gcg.dent.repository;

import gcg.dent.entity.Document;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class DocumentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Document addDocument(Document document) {
        document.setId(null);
        entityManager.persist(document);
        return document;
    }

    @Transactional
    @ReadOnly
    public Document findById(Long id) {
        return entityManager.find(Document.class, id);
    }

    @Transactional
    @ReadOnly
    public List<Document> getAll() {
        return entityManager
                .createQuery("select D from Document D order by D.name", Document.class)
                .getResultList();
    }

    @Transactional
    public void removeById(Long id) {
        entityManager.createQuery("delete from Document D where D.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Transactional
    public Document update(Document document) {
        entityManager.merge(document);
        return document;
    }
}
