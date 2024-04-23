/*
package com.perpustakaan.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import com.perpustakaan.app.model.Stok;

public interface StokRepo extends Extendable<Stok,Long>{
    
    @Modifying
    @Query(nativeQuery = true, value = "update stok set qty=qty-1 where bukuid= :bukuid")
    Integer decreaseStok(@Param("bukuid")Long bukuid);

    @Modifying
    @Query(nativeQuery = true, value = "update stok set qty=qty+1 where bukuid= :bukuid")
    Integer increaseStok(@Param("bukuid")Long bukuid);

    @Query(nativeQuery = true, value = "select qty from stok where bukuid= :bukuid")
    Integer getQty(@Param("bukuid")Long bukuid);
    
    Optional<Stok> findByBukuid(Long bukuid);

}
*/
