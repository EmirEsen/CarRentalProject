package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.entity.Customer;
import org.example.entity.Rent;
import org.example.entity.Vehicle;
import org.example.entity.enums.Status;

import java.util.List;
import java.util.Optional;

public class RentRepository extends RepositoryManager<Rent, Long> {

    public RentRepository() {
        super(Rent.class);
    }

    public Optional<Rent> getCustomersActiveRent(Customer customer) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
            Root<Rent> root = cq.from(Rent.class);
            cq.select(root).where(cb.and(cb.equal(root.get("customer"), customer), cb.equal(root.get("status"), Status.ACTIVE)));
            try {
                return Optional.ofNullable(em.createQuery(cq).getSingleResult());
            }catch (NoResultException e){
                return Optional.empty();
            }
        } finally {
            em.close();
        }
    }

    //todo can not rent same car twice altough it's been returned
    @Override
    public Rent save(Rent rent) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(rent.getVehicle());
            em.merge(rent.getCustomer());
            em.persist(rent);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
        return rent;
    }

    @Override
    public Iterable<Rent> saveAll(Iterable<Rent> entities) {
        return super.saveAll(entities);
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

    public List<Rent> getAllRentsOfCustomer(String tckn) {
        String queryString = "SELECT r " +
                "FROM Rent r " +
                "JOIN r.customer c " +
                "WHERE c.tckn = :tckn";

        TypedQuery<Rent> query = getEntityManager().createQuery(queryString, Rent.class);
        query.setParameter("tckn", tckn);
        return query.getResultList();
    }


}
