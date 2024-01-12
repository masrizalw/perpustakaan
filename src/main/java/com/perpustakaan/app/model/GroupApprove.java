package com.perpustakaan.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.perpustakaan.app.model.GroupApprove;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "grpaprv", schema = "public")
public class GroupApprove {
    
    @EmbeddedId
    private GroupApproveKey groupApproveKey;
    @Column
    private boolean enb;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "menuid", nullable = false, insertable = false, updatable = false, 
            foreignKey = @ForeignKey(name = "fk_grpaprv_menu"))
    private Menu menu;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "acsid", nullable = false, insertable = false, updatable = false)
    @JoinColumn(name = "menuid", nullable = false, insertable = false, updatable = false)
    private MenuAccess menuAccess;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "grpid", nullable = false, insertable = false, updatable = false, 
            foreignKey = @ForeignKey(name = "fk_grpaprv_grp"))
    private Group group;

}
