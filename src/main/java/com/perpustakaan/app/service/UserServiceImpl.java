package com.perpustakaan.app.service;

import static org.springframework.data.jpa.domain.Specification.where;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.perpustakaan.app.model.Group;
import com.perpustakaan.app.model.GroupApprove;
import com.perpustakaan.app.model.GroupApprove_;
import com.perpustakaan.app.model.Group_;
import com.perpustakaan.app.model.UserGroup_;
import com.perpustakaan.app.model.User_;
import com.perpustakaan.app.exception.CustomException;
import com.perpustakaan.app.exception.UserException;
import com.perpustakaan.app.model.User;
import com.perpustakaan.app.model.Konfirmasi;
import com.perpustakaan.app.model.UserGroup;
import com.perpustakaan.app.model.UserGroupKey;
import com.perpustakaan.app.repository.GroupApproveRepo;
import com.perpustakaan.app.repository.KonfirmasiRepo;
import com.perpustakaan.app.repository.UserRepo;
import com.perpustakaan.app.service.util.RegexValidator;
import com.perpustakaan.app.service.util.PasswordRule;
import com.perpustakaan.app.service.util.RandomFiveDigit;
import com.perpustakaan.app.service.util.FilterSpecification;
//import com.perpustakaan.app.service.util.MailBuilder;

import jakarta.persistence.criteria.Join;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncode;
    private final KonfirmasiRepo confirmRepo;
//    private final JavaMailSender mailSender;
    private final GroupApproveRepo groupApproveRepo;
    
    //tidak menggunakan lombok
    public UserServiceImpl(UserRepo userRepo,BCryptPasswordEncoder bCryptPasswordEncode,
//                           JavaMailSender mailSender,
            KonfirmasiRepo confirmRepo,GroupApproveRepo groupApproveRepo) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncode = bCryptPasswordEncode;
        this.confirmRepo = confirmRepo;
//        this.mailSender = mailSender;
        this.groupApproveRepo = groupApproveRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UserException {
        User user = userRepo.findByIdOrEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email or username " + username));
        if (user == null)
            throw new UsernameNotFoundException("User not found with email or id " + username);
        if (user.isDisabled())
            throw new UsernameNotFoundException("Cek email terlebih dahulu untuk menyelesaikan "
                    + "proses registrasi", new UserException("User need to confirm registration"));
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getUserGroup().forEach(userGroup -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(userGroup.getUserGroupKey().getGroupId()));
        });
        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(),
                grantedAuthorities);
    }
    
    @Override
    public Optional<User> findUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> findUserByEmailOrId(String username) {
        return userRepo.findByIdOrEmail(username);
    }

    @Override
    public Optional<User> findUserByEmailOrId(String id, String email) {
        return userRepo.findByIdOrEmail(id, email);
    }
    
    @Override @Transactional
    public User update(User user) {
        if(userRepo.findById(user.getId()).isEmpty())
            error("User tidak ditemukan: "+user.getId());
        if(!PasswordRule.isAllowed(user.getPassword()))
            error("Password minimal 8 karakter, mengandung setidaknya 1 huruf kapital, "
                    + "tidak boleh menggunakan karakter spesial");
        user.setPassword(bCryptPasswordEncode.encode(user.getPassword()));
        return userRepo.save(user);
    }
    
    @Override @Transactional
    public User createAsUser(User anggota) {
        
        //user
        userValidator(anggota);
        anggota.getUserGroup().forEach(userGroup -> {
            if(userGroup.getUserGroupKey().getGroupId().equals("admin"))
                log.warn("User {} mencoba untuk membuat akses ke Admin ",anggota.getId());
        });
        UserGroupKey key = UserGroupKey.builder().userId(anggota.getId()).groupId("user").build();
        anggota.setUserGroup(List.of(UserGroup.builder().userGroupKey(key).build()));
        anggota.setPassword(bCryptPasswordEncode.encode(anggota.getPassword()));
        //true utk wajib konfirmasi pendaftaran, false sebaliknya
        anggota.setDisabled(false);
        User anggotaResult = userRepo.save(anggota);
        
        //konfirmasi
        Konfirmasi konfirm = Konfirmasi.builder().code(RandomFiveDigit.generate()).confirmed(false)
                .expired(LocalDateTime.now(ZoneId.of("Asia/Jakarta")).plusMinutes(5))
                .attempt((short) 0).user(anggotaResult).usrid(anggotaResult.getId()).build();
        Konfirmasi konfirmResult = confirmRepo.save(konfirm);
        
        //email
        //Note: parameter bisa di enkripsi untuk menghindari fraud
//        mailSender.send(new MailBuilder().to(anggotaResult.getEmail())
//                .subject("Kode Registrasi Perpustakaan Digital 418, Jakarta")
//                .text("Berikut adalah link konfirmasi anda, klik link berikut "
//                        + "http://localhost:8080/auth/konfirmasi?userid="+anggotaResult.getId()
//                        +"&kode="+konfirmResult.getCode())
//                .build());
        
        return anggotaResult;
    }
    
    @Override @Transactional
    public User createAsAdmin(User admin) {
        userValidator(admin);
        if(admin.getUserGroup().isEmpty())
            error("Grup User harus di isi");
        UserGroupKey key = UserGroupKey.builder().userId(admin.getId()).groupId("admin").build();
        admin.getUserGroup().add(UserGroup.builder().userGroupKey(key).build());
        admin.setPassword(bCryptPasswordEncode.encode(admin.getPassword()));
        return userRepo.save(admin);
    }
    
    @Override @Transactional
    public Map<String,Object> confirm(String userid, String kode) {
        
        //inisialisasi
        Map<String,Object> pesan = new HashMap<>();
        
        User user = userRepo.findById(userid).get();
        Konfirmasi konfirmasi = confirmRepo.findByUserId(userid).get();
        if (konfirmasi.getExpired().isBefore(LocalDateTime.now()))
            error("Batas waktu konfirmasi habis");
        if (konfirmasi.getAttempt()>5)
            error("Batas Maksimum Percobaan Konfirmasi Tercapai");
        if (!konfirmasi.getUsrid().equals(user.getId()))
            error(""); //kosongan
        if (!kode.equals(konfirmasi.getCode())) {
            confirmRepo.addPercobaan(user.getId());
            pesan.put("error_message","Kode tidak sama, percobaan "+konfirmasi.getAttempt()+" dari 5");
            return pesan;
        }
        if (konfirmasi.getConfirmed())
            error("Akun telah terkonfirmasi, silakan login");
        confirmRepo.addPercobaan(user.getId());
        konfirmasi.setConfirmed(true);
        user.setDisabled(false);
        pesan.put("user", userRepo.save(user));
        pesan.put("konfirmasi", confirmRepo.save(konfirmasi));
        pesan.put("status", "Konfirmasi Berhasil");
        return pesan;
    }
    
    @Override
    public List<GroupApprove> findGroupApprove(String id) {
        if (id == null)
            return groupApproveRepo.findAll();
        Specification<GroupApprove> grpaprv = (r, q, c) -> {
            Join<GroupApprove, Group> grpJoin = r.join(GroupApprove_.GROUP);
            return c.equal(grpJoin.get(Group_.ID), id);
        };
        return groupApproveRepo.findAll(where(grpaprv));
    }
    
    @Override
    /**
     * Spesifikasinya manual dahulu, FilterSpecification bisa di sederhanakan lagi 
     * dan butuh sedikit parameter utk filter yg lebih fleksibel dan readable
     */
    public Collection<User> findUser(Sort sort, String id, String name, String[] groups) {

        if (id == null && name == null && groups == null)
            return userRepo.findAll();

        List<Specification<User>> specs = new ArrayList<Specification<User>>();
        Specification<User> byId = (r, q, cb) -> cb.like(cb.lower(r.get(User_.ID)),
                new StringBuilder("%").append(id).append("%").toString().toLowerCase());
        Specification<User> byName = (r, q, cb) -> cb.like(cb.lower(r.get(User_.NAME)),
                new StringBuilder("%").append(name).append("%").toString().toLowerCase());
        Specification<User> byGroups = ((r, q, cb) -> {
            Join<User, UserGroup> userGroup = r.join(User_.USER_GROUP);
            Join<UserGroup, Group> group = userGroup.join(UserGroup_.GROUP);
            q.groupBy(r.get(User_.ID));
            return cb.in(group.get(Group_.ID)).value(Arrays.asList(groups));
        });

        if (id != null)
            specs.add(byId);
        if (name != null)
            specs.add(byName);
        if (groups != null)
            specs.add(byGroups);
        return userRepo.findAll(FilterSpecification.query(specs), sort);
    }
    
    private final void userValidator(User user) {
        if(user.getEmail().isEmpty()||user.getId().isEmpty()||user.getName().isEmpty()
                ||user.getPassword().isEmpty())
            error("Email, Username, Nama dan Password tidak boleh kosong");
        if(!PasswordRule.isAllowed(user.getPassword()))
            error("Password minimal 8 karakter, mengandung setidaknya 1 huruf kapital, "
                    + "tidak boleh menggunakan karakter spesial");
        if(findUserByEmailOrId(user.getId(),user.getEmail()).isPresent())
            error("Username/email "+user.getId()+" sudah terdaftar");
        if(!RegexValidator.isValidEmail(user.getEmail()))
            error("Format Email tidak sesuai");
        if(!RegexValidator.isValidUsername(user.getId()))
            error("Format Username tidak sesuai");
    }
    
    private final void error(String msg) throws CustomException {
        throw new CustomException(msg);
    }
    
}
