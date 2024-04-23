package com.perpustakaan.app.controller;

import java.util.List;

import com.perpustakaan.app.service.util.Specs;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perpustakaan.app.exception.CustomException;
import com.perpustakaan.app.model.Buku;
import com.perpustakaan.app.model.Buku_;
import com.perpustakaan.app.model.Pinjaman;
//import com.perpustakaan.app.model.Stok;
//import com.perpustakaan.app.model.Stok_;
import com.perpustakaan.app.repository.BukuRepo;
import com.perpustakaan.app.repository.PinjamanRepo;
import com.perpustakaan.app.repository.UserRepo;
import com.perpustakaan.app.service.PinjamanService;

import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;

@RestController @RequestMapping("/buku") @RequiredArgsConstructor
public class BukuController extends Specs {
    
    private final BukuRepo bukuRepo;
    private final UserRepo userRepo;
    private final PinjamanRepo pinjamRepo;
    private final PinjamanService pinjamService;
    
//    @Qualifier("CustomJavaMailSenderImpl")
//    private final JavaMailSender mailSender;
    
    @GetMapping("/hello")
    public ResponseEntity<String> getHello(){
        return ResponseEntity.ok().body("hello");
    }
    
    @GetMapping("/daftar")
    public ResponseEntity<Page<Buku>> getDaftar(String judul, String pengarang, String penerbit,
            String kategori, String isbn, Short tahun, Boolean available,
            @RequestParam(defaultValue="5") Integer size, 
            @RequestParam(defaultValue="1") Integer page){

        boolean isAnyFilter = false;
        Specification<Buku> byJudul = (r, q, cb) -> cb.like(cb.lower(r.get(Buku_.JUDUL)),
                ("%" + judul + "%").toLowerCase());
        Specification<Buku> byPengarang = (r, q, cb) -> cb.like(cb.lower(r.get(Buku_.PENGARANG)),
                ("%" + pengarang + "%").toLowerCase());
        Specification<Buku> byPenerbit = (r, q, cb) -> cb.like(cb.lower(r.get(Buku_.PENERBIT)),
                ("%" + penerbit + "%").toLowerCase());
        Specification<Buku> byKategori = (r, q, cb) -> cb.like(cb.lower(r.get(Buku_.KATEGORI)),
                ("%" + kategori + "%").toLowerCase());
        Specification<Buku> byIsbn = (r, q, cb) -> cb.like(cb.lower(r.get(Buku_.ISBN)),
                ("%" + isbn + "%").toLowerCase());
        Specification<Buku> byTahun = (r, q, cb) -> cb.equal(r.get(Buku_.TAHUN),tahun);
        Specification<Buku> isAvailable = ((r, q, cb) -> cb.greaterThan(r.get(Buku_.QTY), 0));
/*
        Specification<Buku> isAvailable = ((r, q, cb) -> {
            Join<Buku, Stok> bukuStok = r.join(Buku_.STOK);
            q.groupBy(r.get(Buku_.ID));
            return cb.greaterThan(bukuStok.get(Stok_.QTY), 0);
        });
*/

        Specification<Buku> specs = null;

        if (judul != null) {
            specs = isAnyFilterBefore(specs,byJudul,isAnyFilter);
            isAnyFilter = true;
        }
        if (pengarang != null) {
            specs = isAnyFilterBefore(specs,byPengarang,isAnyFilter);
            isAnyFilter = true;
        }
        if (penerbit != null) {
            specs = isAnyFilterBefore(specs,byPenerbit,isAnyFilter);
            isAnyFilter = true;
        }
        if (kategori != null) {
            specs = isAnyFilterBefore(specs,byKategori,isAnyFilter);
            isAnyFilter = true;
        }
        if (isbn != null) {
            specs = isAnyFilterBefore(specs,byIsbn,isAnyFilter);
            isAnyFilter = true;
        }
        if (tahun != null) {
            specs = isAnyFilterBefore(specs,byTahun,isAnyFilter);
            isAnyFilter = true;
        }
        if (available != null) {
            if(available) {
                specs = isAnyFilterBefore(specs,isAvailable,isAnyFilter);
                isAnyFilter = true;
            }
        }
        System.out.println("page"+page+"size"+size);
        //noinspection DataFlowIssue
        return ResponseEntity.ok().body(bukuRepo.findAll(specs,PageRequest.of(page-1, size,
                Sort.by("judul").ascending())));
    }
    
    /*
    @PostMapping("/send")
    public ResponseEntity<Mail> postSend(@RequestBody Mail mail){
        if(mail.subject().isBlank()||mail.to().isBlank()||mail.text().isBlank())
            throw new CustomException("Subyek, Email Penerima, dan Isi tidak boleh kosong");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Mail.FROM);
        message.setTo(mail.to());
        message.setText(mail.text());
        message.setSubject(mail.subject());
        mailSender.send(message);
        return ResponseEntity.ok().body(mail);
    }
    */
    
    @GetMapping("/pinjam")
    public ResponseEntity<Pinjaman> getPinjam(String userid, Long bukuid){
        if(!username().equalsIgnoreCase(userid))
            throw new CustomException("Anda tidak memiliki hak akses atas user lain");
        return ResponseEntity.ok().body(pinjamService.pinjam(userid, bukuid));
    }
    
    @GetMapping("/kembalikan")
    public ResponseEntity<Boolean> getKembalikan(String userid, Long bukuid){
        if(!username().equalsIgnoreCase(userid))
            throw new CustomException("Anda tidak memiliki hak akses atas user lain");
        return ResponseEntity.ok().body(pinjamService.kembalikan(userid, bukuid));
    }
    
    @GetMapping("/daftar-pinjaman")
    public ResponseEntity<List<Pinjaman>> getDaftarPinjaman(String userid){
        if(!username().equalsIgnoreCase(userid))
            throw new CustomException("Anda tidak memiliki hak akses atas user lain");
        return ResponseEntity.ok().body(userRepo.findById(userid).get().getPinjaman());
    }

}
