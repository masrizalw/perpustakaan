package com.perpustakaan.app.model;

import java.util.Collection;

import org.springframework.lang.Nullable;

import com.perpustakaan.app.model.Group;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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
@Table(name = "grp", schema = "public")
public class Group {

    @Id
    private String id;
    @Column
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "group")
    @Nullable
    private Collection<UserGroup> userGroup;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("groupApproveKey.menuid ASC,groupApproveKey.acsid ASC")
    private Collection<GroupApprove> groupApprove;

}
