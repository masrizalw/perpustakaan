package com.perpustakaan.app.service;

import com.perpustakaan.app.model.Pinjaman;

public interface PinjamanService {
    
    Pinjaman pinjam(String userid, Long bukuid);

    Pinjaman kembalikan(String userid, Long bukuid);
    
}
