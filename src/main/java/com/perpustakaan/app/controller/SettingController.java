package com.perpustakaan.app.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perpustakaan.app.model.User;
import com.perpustakaan.app.model.Group;
import com.perpustakaan.app.model.GroupApprove;
import com.perpustakaan.app.model.GroupApproveKey;
import com.perpustakaan.app.model.Menu;
import com.perpustakaan.app.repository.GroupApproveRepo;
import com.perpustakaan.app.repository.GroupRepo;
import com.perpustakaan.app.repository.MenuAccessRepo;
import com.perpustakaan.app.repository.MenuRepo;
import com.perpustakaan.app.repository.UserRepo;
import com.perpustakaan.app.service.UserService;
import com.perpustakaan.app.service.util.RegexValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @RestController 
@RequestMapping("/setting")
public class SettingController {
    
    private final MenuRepo menuRepo;
    private final UserRepo userRepo;
    private final GroupRepo groupRepo;
    private final GroupApproveRepo groupApproveRepo;
    private final MenuAccessRepo menuAccessRepo;
    private final UserService userService;
    
    @GetMapping("/user")
    public ResponseEntity<User> findUser(String id) throws Exception {
        User user = userService.findUserById(
                RegexValidator.removeAnyCharacters(id)).get();
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser2(String id) 
            throws Exception {
        userRepo.deleteById(RegexValidator.removeAnyCharacters(id));
        return ResponseEntity.ok().body("User " + id + " berhasil di delete");
    }
    
    @GetMapping("/users")
    public ResponseEntity<Collection<User>> findUsers(String id, String name, 
            String[] groups) throws Exception {
        return ResponseEntity.ok().body(userService.findUser(Sort.by("id").ascending(), 
                RegexValidator.removeAnyCharacters(id), 
                RegexValidator.removeAnyCharacters(name), groups));
    }

    @GetMapping("/user/delete")
    public ResponseEntity<String> deleteUser(String id) throws Exception {
        userRepo.deleteById(RegexValidator.removeAnyCharacters(id));
        return ResponseEntity.ok().body("User " + id + " berhasil di delete");
    }
    
    @GetMapping("/group")
    public ResponseEntity<Group> findGroup(String id) throws Exception {
        return ResponseEntity.ok().body(groupRepo.findById(RegexValidator
                .removeAnyCharacters(id)).get());
    }

    @PostMapping("/group")
    public ResponseEntity<Group> findGroup(@RequestBody Group group) {
        try {
            groupRepo.findById(group.getId()).get();
        } catch (NoSuchElementException e) {
            List<GroupApprove> listGrpAprv = new ArrayList<GroupApprove>();
            menuAccessRepo.findAll().forEach(menuAccess -> {
                GroupApproveKey groupApproveKey = GroupApproveKey.builder().menuid(menuAccess.getMenu().getId())
                        .acsid(menuAccess.getMenuAccessKey().getAcsid()).grpid(group.getId()).build();
                GroupApprove groupApprove = GroupApprove.builder().groupApproveKey(groupApproveKey).group(group)
                        .menu(Menu.builder().id(menuAccess.getMenu().getId()).build()).menuAccess(menuAccess).build();
                listGrpAprv.add(groupApprove);
            });
            group.setGroupApprove(listGrpAprv);
        }
        return ResponseEntity.ok().body(groupRepo.save(group));
    }

    @GetMapping("/group/delete")
    public ResponseEntity<String> deleteGroup(String id) throws Exception {
        groupRepo.deleteById(RegexValidator.removeAnyCharacters(id));
        return ResponseEntity.ok().body("Group " + id + " berhasil dihapus");
    }

    @GetMapping("/groups")
    public ResponseEntity<List<Group>> findGroups() {
        return ResponseEntity.ok().body(groupRepo.findAll(Sort.by("id").ascending()));
    }

    @GetMapping("/menu")
    public ResponseEntity<Menu> menu() {
        return ResponseEntity.ok().body(menuRepo.findById("root").get());
    }

    @GetMapping("/grpaprv")
    public ResponseEntity<List<GroupApprove>> findGroupApproves(String id) throws Exception {
        return ResponseEntity.ok().body(userService.findGroupApprove(
                RegexValidator.removeAnyCharacters(id)));
    }

    @PostMapping("/grpaprv")
    public ResponseEntity<List<GroupApprove>> saveGroupApprove(
            @RequestBody List<GroupApprove> groupApproves) {
        return ResponseEntity.ok().body(groupApproveRepo.saveAll(groupApproves));
    }
    
}
