package com.perpustakaan.app.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    @Column(nullable=false) private Integer qty;
    @Version private Integer version;
    
/*
    @OneToOne(mappedBy="buku") @JsonIgnoreProperties("buku")
    private Stok stok;
*/

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "buku", cascade = CascadeType.ALL, 
            orphanRemoval = false)
    @Nullable @JsonIgnoreProperties("buku") @ToString.Exclude
    private List<Pinjaman> pinjaman;
    
}
