package com.javaTask.repository;

import com.javaTask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Aleksandr Beryozkin
 */

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select distinct u from User u where u.username like :username")
    User findOneByUsername(@Param("username") String username);
}
