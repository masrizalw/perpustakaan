package com.perpustakaan.app.controller;

public record Mail(String subject, String text, String to) {
    
    public final static String FROM = "perpustakaandigital418@gmail.com";
    
}
