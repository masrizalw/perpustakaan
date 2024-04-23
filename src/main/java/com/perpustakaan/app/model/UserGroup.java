package com.perpustakaan.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.perpustakaan.app.model.UserGroup;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usrgrp", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "unique_usrgrp", columnNames = { "usrid", "grpid" }) })
public class UserGroup {

    @EmbeddedId
    UserGroupKey userGroupKey;

    @JsonIgnore
    @ManyToOne @ToString.Exclude
    @JoinColumn(name = "usrid", nullable = false, insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_usrgrp_usr"))
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "grpid", nullable = false, insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_usrgrp_grp"))
    private Group group;


}
