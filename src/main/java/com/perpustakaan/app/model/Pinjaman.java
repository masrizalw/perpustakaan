package com.perpustakaan.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Pinjaman {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pinjaman")
    @SequenceGenerator(name = "seq_pinjaman", allocationSize = 1)
    @Id private Long id;
    @Column(nullable=false) private String userid;
    @Column(nullable=false) private Long bukuid;
    @Column(columnDefinition = "timestamp with time zone",
            nullable=false) private LocalDateTime tglKembali;
    @Column(columnDefinition = "timestamp with time zone",
            nullable=false) private LocalDateTime tglPinjam;
    @Column(nullable=false) private Boolean dikembalikan;
    @Column(nullable=false) private Integer qty;
    @Version private Integer version;
    
    @ManyToOne @JsonIgnoreProperties("pinjaman") @ToString.Exclude
    @JoinColumn(name = "userid", insertable = false, nullable = true, updatable = false, 
            foreignKey = @ForeignKey(name = "fk_pinjaman_user"))
    private User user;

    @ManyToOne @JsonIgnoreProperties("pinjaman") @ToString.Exclude
    @JoinColumn(name = "bukuid", insertable = false, nullable = true, updatable = false, 
    foreignKey = @ForeignKey(name = "fk_pinjaman_buku"))
    private Buku buku;
    
}
