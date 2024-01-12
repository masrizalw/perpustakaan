package com.perpustakaan.app.model;

import com.perpustakaan.app.model.MenuAccessKey;

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
public class MenuAccessKey {

    @Column
    private String menuid;
    @Column
    private String acsid;

}
