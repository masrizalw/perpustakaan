package com.perpustakaan.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.perpustakaan.app.service.util.Specs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perpustakaan.app.model.UserGroup;
import com.perpustakaan.app.model.UserGroup_;
import com.perpustakaan.app.model.User_;
import com.perpustakaan.app.exception.CustomException;
import com.perpustakaan.app.model.Buku;
import com.perpustakaan.app.model.Buku_;
import com.perpustakaan.app.model.Pinjaman;
import com.perpustakaan.app.model.Pinjaman_;
//import com.perpustakaan.app.model.Stok;
//import com.perpustakaan.app.model.Stok_;
import com.perpustakaan.app.model.User;
import com.perpustakaan.app.model.UserGroupKey;
import com.perpustakaan.app.model.UserGroupKey_;
import com.perpustakaan.app.repository.PinjamanRepo;
import com.perpustakaan.app.repository.UserRepo;
import com.perpustakaan.app.service.UserService;
import com.perpustakaan.app.service.util.Response;
import com.perpustakaan.app.service.util.RegexValidator;

import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @RestController 
@RequestMapping("/admin")
public class AdministratorController extends Specs {

    private final UserService userService;
    private final UserRepo userRepo;
    private final PinjamanRepo pinjamRepo;
    
    @GetMapping
    public String getHello() {
        return "hello admin";
    }
    
    @GetMapping("/users")
    public ResponseEntity<Collection<User>> findUsers(String id, String name, String[] groups) 
            throws Exception {
        return ResponseEntity.ok()
                .body(userService.findUser(Sort.by("id").ascending(), 
                        RegexValidator.removeAnyCharacters(id), 
                        RegexValidator.removeAnyCharacters(name), groups));
    }
    
    /**
     * Membuat admin baru untuk akses halaman admin
     * 
     * @param user
     * @return user
     */
    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User admin){
        userService.createAsAdmin(admin);
        return Response.get(admin);
    }
        
    /**
     * Daftar Admin
     * 
     * @param size
     * @param page
     * @return user
     */
    @GetMapping("/daftar-admin")
    public ResponseEntity<Page<User>> getList(
            @RequestParam(defaultValue="5") Integer size, 
            @RequestParam(defaultValue="1") Integer page){
        if(size > 150)
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
        Specification<User> specs = (root, query, criteriaBuilder) -> {
            Join<User, UserGroup> userGroupJoin = root.join(User_.USER_GROUP);
            Join<UserGroup,UserGroupKey> userGroupKeyJoin = userGroupJoin.join(UserGroup_.USER_GROUP_KEY);
            return criteriaBuilder.in(userGroupKeyJoin.get(UserGroupKey_.GROUP_ID)).value(Set.of("admin"));
        };
        return Response.get(userRepo.findAll(specs, PageRequest.of(
                page-1,size,Sort.by("id").descending())));
    }
    
    @GetMapping("/daftar-pinjaman")
    public ResponseEntity<Page<Pinjaman>> getDaftarPinjaman(String userid,Long bukuid,String judulbuku,
            @RequestParam(defaultValue="1990-01-30")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate tglkembaliStart,
            @RequestParam(defaultValue="1990-01-30")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate tglkembaliEnd,
            @RequestParam(defaultValue="1990-01-30")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate tglpinjamStart,
            @RequestParam(defaultValue="1990-01-30")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate tglpinjamEnd,
            Boolean dikembalikan,@RequestParam(defaultValue="5") Integer size, 
            @RequestParam(defaultValue="1") Integer page ){
        
        LocalDate oldDate = LocalDate.of(2000, 1, 30);

        Boolean isAnyFilter = false;
        Specification<Pinjaman> byUserid = (r, q, cb) -> cb.like(cb.lower(r.get(Pinjaman_.USERID)),
                new StringBuilder("%").append(userid).append("%").toString().toLowerCase());
        Specification<Pinjaman> byJudulbuku = (r, q, cb) -> {
            Join<Pinjaman, Buku> buku = r.join(Pinjaman_.BUKU);
            q.groupBy(r.get(Pinjaman_.ID));
            return cb.like(cb.lower(buku.get(Buku_.JUDUL)),
                    new StringBuilder("%").append(judulbuku).append("%").toString().toLowerCase());
        };
        Specification<Pinjaman> byBukuid = (r, q, cb) -> cb.equal(r.get(Pinjaman_.BUKUID),bukuid);
        Specification<Pinjaman> statusDikembalikan = (r, q, cb) -> cb.equal(
                r.get(Pinjaman_.DIKEMBALIKAN),dikembalikan);
        Specification<Pinjaman> byTglkembaliStart =(root, query, criteriaBuilder) -> {
            ZonedDateTime after = ZonedDateTime.of(tglkembaliStart, LocalTime.MAX ,ZoneId.of("Asia/Jakarta"));
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Pinjaman_.TGL_KEMBALI), after);
        };
        Specification<Pinjaman> byTglkembaliEnd =(root, query, criteriaBuilder) -> {
            ZonedDateTime before = ZonedDateTime.of(tglkembaliEnd, LocalTime.MAX ,ZoneId.of("Asia/Jakarta"));
            return criteriaBuilder.lessThanOrEqualTo(root.get(Pinjaman_.TGL_KEMBALI), before);
        };
        Specification<Pinjaman> byTglpinjamStart =(root, query, criteriaBuilder) -> {
            ZonedDateTime after = ZonedDateTime.of(tglpinjamStart, LocalTime.MAX ,ZoneId.of("Asia/Jakarta"));
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Pinjaman_.TGL_PINJAM), after);
        };
        Specification<Pinjaman> byTglpinjamEnd =(root, query, criteriaBuilder) -> {
            ZonedDateTime before = ZonedDateTime.of(tglpinjamEnd, LocalTime.MAX ,ZoneId.of("Asia/Jakarta"));
            return criteriaBuilder.lessThanOrEqualTo(root.get(Pinjaman_.TGL_PINJAM), before);
        };

        Specification<Pinjaman> specs = null;

        if (userid != null) {
            specs = isAnyFilterBefore(specs,byUserid,isAnyFilter);
            isAnyFilter = true;
        }
        if (bukuid != null) {
            specs = isAnyFilterBefore(specs,byBukuid,isAnyFilter);
            isAnyFilter = true;
        }
        if (judulbuku != null) {
            specs = isAnyFilterBefore(specs,byJudulbuku,isAnyFilter);
            isAnyFilter = true;
        }
        if (dikembalikan != null) {
            if(!dikembalikan) {
                specs = isAnyFilterBefore(specs,statusDikembalikan,isAnyFilter);
                isAnyFilter = true;
            }
        }
        if (tglkembaliStart != null & oldDate.isBefore(tglkembaliStart)) {
            specs = isAnyFilterBefore(specs,byTglkembaliStart,isAnyFilter);
            isAnyFilter = true;
        }
        if (tglkembaliEnd != null & oldDate.isBefore(tglkembaliEnd)) {
            specs = isAnyFilterBefore(specs,byTglkembaliEnd,isAnyFilter);
            isAnyFilter = true;
        }
        if (tglpinjamStart != null & oldDate.isBefore(tglpinjamStart)) {
            specs = isAnyFilterBefore(specs,byTglpinjamStart,isAnyFilter);
            isAnyFilter = true;
        }
        if (tglpinjamEnd != null & oldDate.isBefore(tglpinjamEnd)) {
            specs = isAnyFilterBefore(specs,byTglpinjamEnd,isAnyFilter);
            isAnyFilter = true;
        }
        return ResponseEntity.ok().body(pinjamRepo.findAll(specs,PageRequest.of(page-1, size,
                Sort.by("tglKembali").ascending())));
    }

    /**
     * Daftar Anggota Perpustakaan
     * 
     * @param size
     * @param page
     * @return
     */
    @GetMapping("/daftar-anggota")
    public ResponseEntity<Page<User>> getAnggota(
            @RequestParam(defaultValue="5") Integer size, 
            @RequestParam(defaultValue="1") Integer page){ 
        if(size > 150)
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
        Specification<User> specs = (root, query, criteriaBuilder) -> {
            Join<User, UserGroup> userGroupJoin = root.join(User_.USER_GROUP);
            Join<UserGroup,UserGroupKey> userGroupKeyJoin = userGroupJoin.join(UserGroup_.USER_GROUP_KEY);
            return criteriaBuilder.in(userGroupKeyJoin.get(UserGroupKey_.GROUP_ID)).value(Set.of("user"));
        };
        return Response.get(userRepo.findAll(specs, PageRequest.of(
                page-1,size,Sort.by("id").descending())));
    }

}
