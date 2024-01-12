package com.perpustakaan.app.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.perpustakaan.app.model.Menu;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Menu {
    
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String url;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "header", foreignKey = @ForeignKey(name = "fk_menu_itself"))
    private Menu header;

    @OneToMany(mappedBy = "header")
    private List<Menu> child;

    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    private List<MenuAccess> menuAccess;

    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    private List<GroupApprove> groupApproval;

}
