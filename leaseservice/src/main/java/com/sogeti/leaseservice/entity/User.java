package com.sogeti.leaseservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * User entity.
 * This class is mapped to the users table in the database.
 *
 * @Author:revathi
 */
@Entity
@Table(name = "users")
@Data
public class User {

    /**
     * Id of the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the user
     */
    private String name;

    /**
     * Email of the user
     */
    private String email;

    /**
     *
     */
    private String password;

}
