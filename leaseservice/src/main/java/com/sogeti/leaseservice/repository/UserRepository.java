package com.sogeti.leaseservice.repository;


import com.sogeti.leaseservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User Repository
 *
 * This interface is used to interact with the database
 * @Author:revathi
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * This method is used to find the user by email
     * @param email
     * @return
     */
    User findFirstByEmail(String email);
}
