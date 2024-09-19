package com.sboard.repository;

import com.sboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    //@Query("select u from User u where u.uid = ? and u.pass = ?")
    //List<User> finduser(String uid, String pass);
}
