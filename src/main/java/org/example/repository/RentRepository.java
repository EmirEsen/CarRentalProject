package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.entity.Rent;
import org.example.entity.Vehicle;

public class RentRepository extends RepositoryManager<Rent, Long>{

    public RentRepository() {
        super(Rent.class);
    }

    @Override
    public Rent save(Rent entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(entity.getVehicle());
            em.merge(entity.getCustomer());
            em.persist(entity);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
        return entity;
    }

    public Rent returnRent(Rent entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(entity.getVehicle());
            em.merge(entity.getCustomer());
            em.merge(entity);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
        return entity;
    }



}
