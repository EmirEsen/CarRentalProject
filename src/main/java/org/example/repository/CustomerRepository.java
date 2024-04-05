package org.example.repository;

import jakarta.persistence.EntityManager;
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

public class CustomerRepository extends RepositoryManager<Customer, Long>{
    public CustomerRepository() {
        super(Customer.class);
    }


    public void addNewCustomer(Customer customer){
        save(customer);
    }

    public Customer findByTckn(String tckn){
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            Root<Customer> root = cq.from(Customer.class);
            cq.select(root).where(cb.equal(root.get("tckn"),
                    tckn));
            return em.createQuery(cq).getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Rent> getActiveRentOfCustomer(Customer customer){
       return customer.getRents().stream()
                .filter(rent -> rent.getStatus().equals(Status.ACTIVE))
                .toList();
    }

    @Override
    public List<Customer> findByColumnAndValue(String columnName, Object value) {
        return super.findByColumnAndValue(columnName, value);
    }




}
