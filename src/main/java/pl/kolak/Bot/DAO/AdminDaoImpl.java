package pl.kolak.Bot.DAO;

import pl.kolak.Bot.model.Admin;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class AdminDaoImpl implements AdminDao{

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public AdminDaoImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void add(Admin admin) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(admin);
        transaction.commit();
    }

    @Override
    public Admin get(Long discord_id) {
        return entityManager.find(Admin.class, discord_id);
    }

    @Override
    public void update(Admin admin) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(admin);
        transaction.commit();
    }

    @Override
    public void delete(Long discord_id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Admin admin = entityManager.find(Admin.class, discord_id);
        entityManager.remove(admin);
        transaction.commit();
    }
}
