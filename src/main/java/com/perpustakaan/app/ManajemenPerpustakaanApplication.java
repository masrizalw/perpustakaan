package com.perpustakaan.app;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.perpustakaan.app.model.*;
import com.perpustakaan.app.service.PinjamanService;
import com.perpustakaan.app.service.util.CreateRandomTransaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.perpustakaan.app.repository.GroupApproveRepo;
import com.perpustakaan.app.repository.GroupRepo;
import com.perpustakaan.app.repository.UserGroupRepo;
import com.perpustakaan.app.repository.UserRepo;

@SpringBootApplication
public class ManajemenPerpustakaanApplication {

    ExecutorService executor = Executors.newFixedThreadPool(5);

	public static void main(String[] args) {
		SpringApplication.run(ManajemenPerpustakaanApplication.class, args);
	}
	
    @Bean BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    CommandLineRunner commandLineRunner(UserRepo userRepo, GroupRepo groupRepo, UserGroupRepo userGroupRepo,
                                        GroupApproveRepo groupApproveRepo, PinjamanService pinjamanService) {
        return args -> {
            
            User sa = User.builder().id("sa").name("Super Admin").disabled(false)
                    .email("sa@sa.id").password(bCryptPasswordEncoder().encode("asdf")).build();
            User user = User.builder().id("user").name("User A")
                    .email("user@sa.id").password(bCryptPasswordEncoder().encode("asdf")).build();
            User mrw = User.builder().id("mrw").name("Mister")
                    .email("mail@mrw.id").password(bCryptPasswordEncoder().encode("asdf")).build();
            User haeroh = User.builder().id("haeroh").name("haeroh")
                    .email("mail@haeroh.id").password(bCryptPasswordEncoder().encode("asdf")).build();
            User ica = User.builder().id("ica").name("ica")
                    .email("mail@ica.id").password(bCryptPasswordEncoder().encode("asdf")).build();
            List<User> userList = userRepo.saveAll(List.of(sa, user,mrw, haeroh, ica));

            Group adminGroup = Group.builder().id("admin").name("Super Admin").build();
            Group userGroup = Group.builder().id("user").name("User").build();

            UserGroupKey saAsAdminGroupkey = UserGroupKey.builder().groupId(adminGroup.getId())
                    .userId(sa.getId()).build();
            UserGroup saAsAdminGroup = UserGroup.builder().userGroupKey(saAsAdminGroupkey)
                    .user(sa).group(adminGroup).build();
            UserGroupKey saAsUserGroupKey = UserGroupKey.builder().groupId(userGroup.getId())
                    .userId(sa.getId()).build();
            UserGroup saAsUserGroup = UserGroup.builder().userGroupKey(saAsUserGroupKey).user(sa)
                    .group(userGroup).build();
            UserGroupKey userAsUserGroupkey = UserGroupKey.builder().groupId(userGroup.getId())
                    .userId(user.getId()).build();
            UserGroup userAsUserGroup = UserGroup.builder().userGroupKey(userAsUserGroupkey)
                    .user(user).group(userGroup).build();
            userGroupRepo.saveAll(List.of(saAsAdminGroup, saAsUserGroup, userAsUserGroup));

            List<Pinjaman> transaction = CreateRandomTransaction.start(userList);
            transaction.forEach(_pinjam -> {
                executor.submit(() -> {
                    try {
                        pinjamanService.pinjam(_pinjam.getUserid(),_pinjam.getBukuid());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            });
        };
    }

}
