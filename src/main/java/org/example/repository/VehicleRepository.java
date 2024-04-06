package org.example.repository;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.entity.Rent;
import org.example.entity.Vehicle;
import org.example.entity.enums.Segment;
import org.example.entity.enums.Status;

import java.lang.reflect.Field;
import java.util.List;

public class VehicleRepository extends RepositoryManager<Vehicle, Long> {

    public VehicleRepository() {
        super(Vehicle.class);
    }


    public List<Vehicle> getVehiclesBySegment(Segment segment) {
        return findByColumnAndValue("segment", segment);
    }

    public void detachAllVehicles(List<Vehicle> vehicles){
        detachAll(vehicles);
    }

    public Field[] getFieldNames(Class<Vehicle> entityClass) {
        return entityClass.getDeclaredFields();
    }

    public List<Vehicle> getVehiclesWithFilter(String filtername, String filterValue) {
        return findByColumnAndValue(filtername, filterValue);
    }


    public List<Vehicle> getAvailableVehicles() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Vehicle> cr = criteriaBuilder.createQuery(Vehicle.class);
        Root<Vehicle> root = cr.from(Vehicle.class);
        cr.select(root).where(criteriaBuilder.equal(root.get("inRent"), false));
        return getEntityManager().createQuery(cr).getResultList();
    }

    public List<Vehicle> getVehiclesInRent() {
        String queryString = "SELECT v " +
                "FROM Vehicle v " +
                "JOIN Rent r ON v = r.vehicle " +
                "WHERE r IS NOT NULL AND r.status = :status";
        TypedQuery<Vehicle> query = getEntityManager().createQuery(queryString, Vehicle.class);
        query.setParameter("status", Status.ACTIVE);
        return query.getResultList();
    }

    public List<Vehicle> getVehiclesRentedByCustomer(Long customerId) {
        String queryString = "SELECT v " +
                "FROM Vehicle v " +
                "JOIN v.rent r " +
                "WHERE r.customer.id = :customerId";

        TypedQuery<Vehicle> query = getEntityManager().createQuery(queryString, Vehicle.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }






}
