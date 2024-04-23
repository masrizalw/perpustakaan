package com.perpustakaan.app.repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.perpustakaan.app.model.Pinjaman;

public interface PinjamanRepo extends  Extendable<Pinjaman,Long>{

    @Modifying
    @Query(nativeQuery = true, value = "update pinjaman set dikembalikan=true where id= :id")
    Integer kembalikan(@Param("id")Long id);
    
    //List<Pinjaman> findAllByUseridAndBukuid(String userid, Long bukuid);

/*
    @Query(nativeQuery = true, value = "select * from pinjaman where userid= :userid and bukuid= :bukuid and dikembalikan=false for update")
    List<Pinjaman> findAllByUseridAndBukuidAndDikembalikan(@Param("userid")String userid,
            @Param("bukuid")Long bukuid);
*/

    @Lock(LockModeType.OPTIMISTIC)
    List<Pinjaman> findAllByUseridAndBukuidAndDikembalikan(String userid, Long bukuid, Boolean dikembalikan);

    @Query(nativeQuery = true, value = "select * from pinjaman where userid= :userid and bukuid= :bukuid and dikembalikan=true")
    List<Pinjaman> findAllByUseridAndBukuid(@Param("userid")String userid,
            @Param("bukuid")Long bukuid);

}
