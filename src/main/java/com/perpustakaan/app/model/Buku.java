package com.perpustakaan.app.model;

import java.util.List;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Buku {
    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_buku")
    @SequenceGenerator(name = "seq_buku", allocationSize = 1)
    @Id private Long id;
    @Column private String isbn;
    @Column private String judul;
    @Column private String pengarang;
    @Column private String penerbit;
    @Column private String kategori;
    @Column private Short tahun;
    
    //stok dibedakan jika kelak ada manajemen stok antar gudang
    @OneToOne(mappedBy="buku") @JsonIgnoreProperties("buku")
    private Stok stok;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "buku", cascade = CascadeType.ALL, 
            orphanRemoval = false)
    @Nullable @JsonIgnoreProperties("buku")
    private List<Pinjaman> pinjaman;
    
}
