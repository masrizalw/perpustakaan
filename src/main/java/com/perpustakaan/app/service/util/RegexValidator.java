package com.perpustakaan.app.service.util;

import java.util.regex.Pattern;

/**
 * Masih membutuhkan email validator karena spring check @Email banyak lolosnya
 */
public final class RegexValidator {
    
    //^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$
/*
    private final static Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+$");
*/
    private final static Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]*)+$");

    private final static Pattern USERNAME_PATTERN = Pattern.compile(
            "^(?!.*\\s)(?!.*@.*\\.)(?!.*[^a-zA-Z0-9]).*$");
    
    //"^[a-zA-Z0-9!@#$%&*()\\-_+={\\[\\]}|\\\\\\/'\",<>.\\/?~`]+$");
    private final static Pattern ALPHANUMBERONLY_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
    
    public final static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    public final static boolean isValidUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }
    
    public final static String removeAnyCharacters(String string) throws Exception {
        if (string == null) return null;
        if (string.length() > 1000)
            throw new Exception("Max String 1000 characters");
        if (ALPHANUMBERONLY_PATTERN.matcher(string).matches())
            return string;
        StringBuilder result = new StringBuilder();
        String avoid = "<>`+[_]'#;:{|}\\\'/?)($#%@!~*=&^%\"";
        boolean escape = false;
        for (int i = 0; i < string.length(); i++) {
            escape = false;
            for (int j = 0; j < avoid.length(); j++) {
                if (string.charAt(i) == avoid.charAt(j))
                    escape = true;
                if (escape)
                    break;
            }
            if (!escape)
                result.append(string.charAt(i));
        }
        return result.toString();
    }
    
}
