package com.perpustakaan.app.model;

import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "usr", schema = "public",
    uniqueConstraints = @UniqueConstraint(name = "uk_user_key", columnNames = {
        "email" }))
public class User {
    
    @Id @NotEmpty
    private String id; //username
    @Email(message = "Email harus benar")
    @Column(nullable=false) @NotEmpty
    private String email;
    @Column(nullable=false) @NotEmpty
    private String name;
    @Column(nullable=false) @NotEmpty
    private String password;
    @Column(columnDefinition = "boolean default false ",nullable=false)
    private boolean disabled;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, 
            orphanRemoval = true)
    //@Nullable
    private List<UserGroup> userGroup;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL,
    		orphanRemoval = true)
    @JsonIgnoreProperties("user")
    @Nullable
    private List<Pinjaman> pinjaman;

    @OneToOne(mappedBy = "user") @JsonIgnoreProperties("user")
    private Konfirmasi konfirmasi;

}
