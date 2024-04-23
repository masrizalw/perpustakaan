package com.perpustakaan.app.repository;

import com.perpustakaan.app.model.Buku;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface BukuRepo extends Extendable<Buku,Long>{

    @SuppressWarnings("NullableProblems")
    @Lock(LockModeType.OPTIMISTIC)
    Optional<Buku> findById(Long id);
    
}
