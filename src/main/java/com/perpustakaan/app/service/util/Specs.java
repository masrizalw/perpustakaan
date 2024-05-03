package com.perpustakaan.app.service.util;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

public class Specs {

    /**
     * Private method
     * @param <T>
     * @param m kumpulan specification T lampau
     * @param t specification T yg akan di masukkan atau dijadikan pertama
     * @return specification T
     */
    protected <T> Specification<T> add(Specification<T> m, Specification<T> t){
        if(m==null)
            return t;
        else return m.and(t);
    }

    protected String username() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
