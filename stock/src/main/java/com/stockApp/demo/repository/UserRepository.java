package com.stockApp.demo.repository;
import  com.stockApp.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User getById(long id);
    boolean existsUserByEmail(String email);
    User findOneByUsername(String usernmae);
    User findOneByEmail(String email);
}
