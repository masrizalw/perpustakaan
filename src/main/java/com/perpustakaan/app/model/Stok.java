/*
package com.perpustakaan.app.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="stok",schema = "public",
    uniqueConstraints = @UniqueConstraint(
    name = "uk_stok_key", columnNames = {"bukuid"}))
public class Stok {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_stok")
    @SequenceGenerator(name = "seq_stok", allocationSize = 1)
    @Id private Long id;

    @Column(nullable=false) private Long bukuid;
    
    @Column(nullable=false) private Integer qty;

    @OneToOne @JsonIgnoreProperties("stok")
    @JoinColumn(name = "bukuid", insertable = false, nullable = false, updatable = false, 
    foreignKey = @ForeignKey(name = "fk_stok_buku"))
    private Buku buku;

}
*/
