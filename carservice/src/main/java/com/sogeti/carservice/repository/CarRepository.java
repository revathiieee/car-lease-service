package com.sogeti.carservice.repository;

import com.sogeti.carservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Car repository
 *
 * This interface is used to perform CRUD operations on Car entity
 * @Author: revathi
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
