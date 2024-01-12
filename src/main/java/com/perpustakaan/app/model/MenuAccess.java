package com.perpustakaan.app.model;

import java.util.List;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.perpustakaan.app.model.MenuAccess;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
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
@Table(name = "menuacs", schema = "public")
public class MenuAccess {

    @EmbeddedId
    private MenuAccessKey menuAccessKey;
    @Column(columnDefinition = "boolean default true")
    private boolean enb;

    @ManyToOne
    @JoinColumn(name = "menuid", nullable = false, insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_menuacs_menu"))
    private Menu menu;

    @JsonIgnore
    @OneToMany(mappedBy = "menuAccess")
    @Nullable
    private List<GroupApprove> groupApproval;

}
