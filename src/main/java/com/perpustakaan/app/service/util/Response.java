package com.perpustakaan.app.service.util;

import org.springframework.http.ResponseEntity;

public final class Response {
    
    public final static <T> ResponseEntity<T> get(T t){
        return ResponseEntity.ok().body(t);
    }
    
}
