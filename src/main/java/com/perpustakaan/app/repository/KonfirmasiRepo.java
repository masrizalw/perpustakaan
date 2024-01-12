package com.perpustakaan.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.perpustakaan.app.model.Konfirmasi;

public interface KonfirmasiRepo extends  Extendable<Konfirmasi, Long> {

    
    @Modifying
    @Query(nativeQuery = true, value = "insert into konfirmasi "
            + "(confirmed,expired,usrid,code,id) values "
            + "(false,now()+interval '15 minutes',:userid,:kode,nextval('seq_konfirmasi'));")
    Integer saveKonfirmasi(@Param("userid")String userid,@Param("kode")String kode);

    @Modifying
    @Query(nativeQuery = true, value = "update konfirmasi set attempt=attempt+1 where usrid= :usrid")
    Integer addPercobaan(@Param("usrid")String usrid);
    
    //Optional<Konfirmasi> findByUserIdContaining(String userid);

    Optional<Konfirmasi> findByUserId(String userid);
    
}
