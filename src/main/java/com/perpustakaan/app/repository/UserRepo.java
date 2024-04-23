package com.perpustakaan.app.repository;

import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.perpustakaan.app.model.User;

public interface UserRepo extends Extendable<User, String>{
    
    @Query(nativeQuery = true, value = "select * from usr where (id=:id or email=:id) and disabled=false;")
    Optional<User> findByIdOrEmail(@Param("id") String id);

    @Query(nativeQuery = true, value = "select * from usr where (id=:id or email = :id or id = :mail or email = :mail);")
    Optional<User> findByIdOrEmail(@Param("id") String id,@Param("mail") String mail);
    
    Optional<User> findByEmailOrId(String email,String id);

    Optional<User> findById(String id);
    
}
