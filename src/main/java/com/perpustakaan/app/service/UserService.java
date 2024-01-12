package com.perpustakaan.app.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.perpustakaan.app.model.GroupApprove;
import com.perpustakaan.app.model.User;

public interface UserService {

    Optional<User> findUserById(String id);

    Optional<User> findUserByEmailOrId(String id, String email);

    Optional<User> findUserByEmailOrId(String username);
    
    User update(User user);

    User createAsUser(User user);

    User createAsAdmin(User admin);
    
    Map<String,Object> confirm(String userid, String code);
    
    List<GroupApprove> findGroupApprove(String id);
    
    Collection<User> findUser(Sort sort, String id, String name, String[] groups);
    
}
