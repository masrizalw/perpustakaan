package com.perpustakaan.app.service.util;

import java.util.Random;

public final class RandomFiveDigit {

    private final static Random random = new Random();

    public final static String generate() {
        return String.valueOf(random.nextInt(90000) + 10000);
    }

}
