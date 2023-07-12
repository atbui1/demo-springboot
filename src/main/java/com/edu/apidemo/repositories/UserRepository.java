package com.edu.apidemo.repositories;

import com.edu.apidemo.models.Role;
import com.edu.apidemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
    List<User> findByFullname(String fullname);
    List<User> findUserByRolesId(long roleId);
    List<User> findUserByStatus(int status);
}
