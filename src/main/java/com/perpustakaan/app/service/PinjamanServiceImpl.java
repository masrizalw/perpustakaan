package com.perpustakaan.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.perpustakaan.app.exception.CustomException;
import com.perpustakaan.app.model.Buku;
import com.perpustakaan.app.model.Pinjaman;
//import com.perpustakaan.app.model.Stok;
import com.perpustakaan.app.model.User;
import com.perpustakaan.app.repository.BukuRepo;
import com.perpustakaan.app.repository.PinjamanRepo;
//import com.perpustakaan.app.repository.StokRepo;
import com.perpustakaan.app.repository.UserRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor @Service @Slf4j
public class PinjamanServiceImpl implements PinjamanService {
    
    private final PinjamanRepo pinjamRepo;
    private final UserRepo userRepo;
    private final BukuRepo bukuRepo;

    @Override @Transactional
    public Pinjaman pinjam(String userid, Long bukuid) {
        User user = userRepo.findById(userid).orElseThrow();
        assert user.getPinjaman() != null;
        user.getPinjaman().forEach(pinjam -> {
            if(!pinjam.getDikembalikan())
                throw new CustomException("Buku "+pinjam.getBuku().getJudul()+" belum dikembalikan,"
                        + "harap kembalikan dahulu sebelum meminjam kembali");
        });
        Buku buku = bukuRepo.findById(bukuid).orElseThrow();
        Pinjaman pinjam = Pinjaman.builder().userid(userid).bukuid(bukuid).dikembalikan(false)
                .tglPinjam(LocalDateTime.now()).tglKembali(LocalDateTime.now().plusWeeks(2))
                .user(user).buku(buku).qty(1).build();
        buku.setQty(buku.getQty()-1);
        if(buku.getQty()<0) {
            throw new CustomException("Stok buku "+buku.getJudul()+" tidak mencukupi");
        }
        bukuRepo.save(buku);
        return pinjamRepo.save(pinjam);
    }
    
    @Override @Transactional
    public Boolean kembalikan(String userid, Long bukuid) {

        //update pinjaman
        List<Pinjaman> listPinjam = pinjamRepo
                .findAllByUseridAndBukuidAndDikembalikan(userid,bukuid,false);
        if(listPinjam.isEmpty())
            throw new CustomException("pinjaman tidak ditemukan");
        if(listPinjam.size()>1)
            throw new CustomException("data tidak valid, ditemukan 2 pinjaman buku yang sama oleh satu "
                    + "user "+userid);
        for(Pinjaman pinjaman : listPinjam)
            pinjaman.setDikembalikan(true);
        pinjamRepo.save(listPinjam.getFirst());

        //update buku
        Buku buku = bukuRepo.findById(bukuid).orElseThrow();
        buku.setQty(buku.getQty()+1);
        bukuRepo.save(buku);

        return true;
    }

}
