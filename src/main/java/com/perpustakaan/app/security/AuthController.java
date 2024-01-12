package com.perpustakaan.app.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perpustakaan.app.model.User;
import com.perpustakaan.app.repository.KonfirmasiRepo;
import com.perpustakaan.app.security.AuthController;
import com.perpustakaan.app.service.UserService;
import com.perpustakaan.app.service.util.RandomFiveDigit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
      
    private final UserService userService;
    private final KonfirmasiRepo confirmRepo;
    
    @PostMapping("/register") @Transactional
    public synchronized ResponseEntity<?> register(@RequestBody User user){
        //confirmRepo.saveKonfirmasi(userResult.getId(),RandomFiveDigit.generate());
        return ResponseEntity.ok().body(userService.createAsUser(user));
    }
    
    @GetMapping("/konfirmasi")
    /* sync supaya thread safety dan menghemat resource jika kemampuan concurrent server
     * tdk banyak, kegiatan konfirmasi juga jarang/tidak terlalu aktif, 
     * jaga2 jika di isengin
     */
    public synchronized ResponseEntity<?> confirm(String userid, String kode){
        return ResponseEntity.ok().body(userService.confirm(userid, kode));
    }
    
    @GetMapping("/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(TokenKey.KEY.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.findUserById(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with name or id " + username));
                ;
                Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
                user.getUserGroup().forEach(userGroup -> {
                    grantedAuthorities.add(new SimpleGrantedAuthority(userGroup.getUserGroupKey().getGroupId()));
                });
                String access_token = JWT.create().withSubject(user.getId())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("akses", grantedAuthorities.stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                log.error("Error log in: {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> tokens = new HashMap<>();
                tokens.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                try {
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            throw new RuntimeException("refresh token is missing");
        }
    }
    
}
