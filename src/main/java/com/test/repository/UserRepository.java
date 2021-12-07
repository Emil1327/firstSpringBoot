package com.test.repository;

import com.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u from User u where u.email=:email and  u.password=:password")
    User login(String email,String password);

    @Query(value = "SELECT u from User u where u.email=:email")
    User getByEmail(String email);

    List<User> getAllByName(String name);

    @Query(value = "SELECT u from User u where u.id=:id")
    User findGetId(int id);

    @Query(value = "SELECT u from User u where u.token=:token")
    User getUserByToken(long token);
}
