package com.perpustakaan.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="konfirmasi",schema = "public",
    uniqueConstraints = @UniqueConstraint(
    name = "uk_konfirmasi_key", columnNames = {"usrid"}))
public class Konfirmasi {
    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_konfirmasi")
    @SequenceGenerator(name = "seq_konfirmasi", allocationSize = 1)
    @Id private Long id;

    @Column(nullable=false) @NotEmpty private String code;
    
    @Column(columnDefinition = "timestamp with time zone",
            nullable=false) 
    //tidak bisa @NotEmpty, JPA akan error
    private LocalDateTime expired;
    
    @Column(nullable=false) private Boolean confirmed;
    
    @Column(columnDefinition = "smallint default 0",nullable=true)
    private Short attempt;
    
    @Column String usrid;
    
    @JsonIgnoreProperties("konfirmasi")
    @OneToOne (cascade = CascadeType.DETACH)
    @JoinColumn(name = "usrid", foreignKey = @ForeignKey(name = "fk_konfirmasi_user"), 
            insertable = false, updatable = false,nullable=false)
    private User user;
    
}
