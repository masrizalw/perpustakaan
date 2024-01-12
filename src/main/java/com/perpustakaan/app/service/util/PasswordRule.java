package com.perpustakaan.app.service.util;

public final class PasswordRule {
    
    public final static boolean isAllowed(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasCapital = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            }
        }
        return hasCapital;
    }
    
}
