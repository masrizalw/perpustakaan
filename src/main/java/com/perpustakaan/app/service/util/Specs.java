package com.perpustakaan.app.service.util;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

public class Specs {

    /**
     * Private method
     * @param <T>
     * @param m kumpulan specification T lampau
     * @param t specification T yg akan di masukkan atau dijadikan pertama
     * @param isAnyFilter kondisi apakah pernah ada filter sebelumnya
     * @return specification T
     */
    protected <T> Specification<T> isAnyFilterBefore(Specification<T> m, Specification<T> t, boolean isAnyFilter){
        if(!isAnyFilter)
            return t;
        else return m.and(t);
    }

    protected String username() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
