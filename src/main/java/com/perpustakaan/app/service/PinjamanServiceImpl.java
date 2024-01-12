package com.perpustakaan.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perpustakaan.app.exception.CustomException;
import com.perpustakaan.app.model.Buku;
import com.perpustakaan.app.model.Pinjaman;
import com.perpustakaan.app.model.Stok;
import com.perpustakaan.app.model.User;
import com.perpustakaan.app.repository.BukuRepo;
import com.perpustakaan.app.repository.PinjamanRepo;
import com.perpustakaan.app.repository.StokRepo;
import com.perpustakaan.app.repository.UserRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor @Service @Slf4j
public class PinjamanServiceImpl implements PinjamanService {
    
    private final PinjamanRepo pinjamRepo;
    private final UserRepo userRepo;
    private final BukuRepo bukuRepo;
    private final StokRepo stokRepo;
    
    @Override @Transactional
    public Pinjaman pinjam(String userid, Long bukuid) {
        log.debug("start");
        User user = userRepo.findById(userid).get();
        user.getPinjaman().forEach(pinjam -> {
            if(!pinjam.getDikembalikan())
                throw new CustomException("Buku "+pinjam.getBuku().getJudul()+" belum dikembalikan,"
                        + "harap kembalikan dahulu sebelum meminjam kembali");
        });
        log.debug("checking user has pinjaman done");
        Buku buku = bukuRepo.findById(bukuid).get();
        Pinjaman pinjam = Pinjaman.builder().userid(userid).bukuid(bukuid).dikembalikan(false)
                .tglPinjam(LocalDateTime.now()).tglKembali(LocalDateTime.now().plusWeeks(2))
                .user(user).buku(buku).qty(1).build();
        stokRepo.decreaseStok(bukuid);
        log.debug("decrease stok done");
        if(stokRepo.getQty(bukuid)<0)
            throw new CustomException("Stok buku "+buku.getJudul()+" tidak mencukupi");
        log.debug("check final qty done");
        return pinjamRepo.save(pinjam);
    }
    
    @Override @Transactional
    public Pinjaman kembalikan(String userid, Long bukuid) {
        List<Pinjaman> listPinjam = pinjamRepo
                .findAllByUseridAndBukuidAndDikembalikan(userid,bukuid);
        int count = 0;
        Pinjaman pinjamResult = null;
        if(listPinjam.size()<=0)
            throw new CustomException("pinjaman tidak ditemukan");
        for(Pinjaman pinjam : listPinjam) {
            if(!pinjam.getDikembalikan()) {
                if(count>0)
                    throw new CustomException("data tidak valid, ditemukan 2 pinjaman oleh satu "
                            + "user "+userid);
                pinjamRepo.kembalikan(pinjam.getId());
                pinjamResult = pinjam;
                count++;
            }
            stokRepo.increaseStok(bukuid);
        }
        return pinjamResult;
    }

}
