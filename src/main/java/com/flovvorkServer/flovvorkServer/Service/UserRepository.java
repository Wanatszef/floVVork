package com.flovvorkServer.flovvorkServer.Service;

import com.flovvorkServer.flovvorkServer.entity.User;
import com.flovvorkServer.flovvorkServer.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>
{
    User findByUsername(String username);

    User findByIdUser(Long id);
    @Query("SELECT m FROM User m")
    List<User> findAll();

    User findByUserDetails(UserDetails userDetails);

}
