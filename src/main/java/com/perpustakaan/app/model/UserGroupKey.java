package com.perpustakaan.app.model;

import com.perpustakaan.app.model.UserGroupKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserGroupKey {

    @Column(name = "usrid", nullable = false)
    private String userId;

    @Column(name = "grpid", nullable = false)
    private String groupId;

}
