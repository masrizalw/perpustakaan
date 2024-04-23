package com.perpustakaan.app.service.util;

import com.perpustakaan.app.model.Buku;
import com.perpustakaan.app.model.Pinjaman;
import com.perpustakaan.app.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CreateRandomTransaction {

    private static final Random RANDOM = new Random();
    private static final List<String> userList = Arrays.asList("sa", "user", "haeroh", "ica","mrw");
//    private static final List<Integer> bookList = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,17,18,19,20,21,22,23,24,25);
    private static final List<Integer> bookList = Arrays.asList(1,2);

    public static List<Pinjaman> start(List<User> userList) {
        List<Pinjaman> pinjaman = new ArrayList<>();
        for (User user : userList) {
            pinjaman.add(createRandomPinjaman(user));
        }
        return pinjaman;
    }

    private static Pinjaman createRandomPinjaman(User user) {
        Long bukuid = Long.valueOf(bookList.get(RANDOM.nextInt(bookList.size())));
        return Pinjaman.builder().userid(user.getId()).bukuid(bukuid).dikembalikan(false)
                .tglPinjam(LocalDateTime.now()).tglKembali(LocalDateTime.now().plusWeeks(2))
                .user(user).buku(Buku.builder().id(bukuid).build()).qty(1).build();
    }

}
