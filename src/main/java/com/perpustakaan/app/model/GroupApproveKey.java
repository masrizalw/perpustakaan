package com.perpustakaan.app.model;

import com.perpustakaan.app.model.GroupApproveKey;

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
public class GroupApproveKey {
    
    @Column private String menuid;
    @Column private String grpid;
    @Column private String acsid;
    
}
