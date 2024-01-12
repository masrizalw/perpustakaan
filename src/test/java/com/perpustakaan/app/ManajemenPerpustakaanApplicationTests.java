package com.perpustakaan.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class ManajemenPerpustakaanApplicationTests {

    enum Type {JSON,PDF}
    @Autowired private TestRestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();
    @SuppressWarnings("unused")
    private String token, refreshToken;
    
    @Order(1) @Test void getDaftarAdmin() {
        ResponseEntity<Object> response = restTemplate.exchange("/admin/daftar-admin",HttpMethod.GET,
                getAuthenticateHttpEntity(),Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.info("getDaftarAdmin pass");
    }
	
    @Order(2) @Test void getDaftarAnggota() {
        ResponseEntity<Object> response = restTemplate.exchange("/admin/daftar-anggota",HttpMethod.GET,
                getAuthenticateHttpEntity(),Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.info("getDaftarAnggota pass");
    }
    
    @Order(3) @Test void postAnggota() {
        String anggotaJson = "{"
                + "\"id\":\"masrizalw\","
                + "\"name\":\"Mas Rizal Wahidi\","
                + "\"password\":\"dfdsfsdfsdS\","
                + "\"email\":\"masrizalw@gmail.com\","
                + "\"disabled\":false,"
                + "\"userGroup\":[{"
                + "    \"userGroupKey\":{"
                + "        \"userId\":\"sa\","
                + "        \"groupId\":\"admin\""
                + "    }"
                + "}]"
                + "}";
        ResponseEntity<Object> response = restTemplate.exchange("/auth/register",HttpMethod.POST,
                getAuthenticateHttpEntity(anggotaJson,Type.JSON),Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.info("postAnggota pass");
    }
    
    /* dst... krn waktu terbatas, test di batasi.....
    @Order(4) @Test void postAdmin() {
        ResponseEntity<Object> response = restTemplate.exchange("/admin/daftar-anggota",HttpMethod.GET,
                getAuthenticateHttpEntity(),Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.info("getDaftarAnggota pass");
    }
    */
    
    private <T> HttpEntity<T> getAuthenticateHttpEntity(T t,Type type){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+token);
        switch(type) {
        case JSON:
            headers.setContentType(MediaType.APPLICATION_JSON);
            break;
        case PDF:
            headers.setContentType(MediaType.APPLICATION_PDF);
            break;
        default:break;
        }
        return new HttpEntity<>(t,headers);
    }
    
   @SuppressWarnings("unused")
   private <T> HttpEntity<T> getAuthenticateHttpEntity(T t){
       return new HttpEntity<>(t,headers);
   }
   
    private HttpEntity<?> getAuthenticateHttpEntity(){
        return new HttpEntity<>(headers);
    }
    
    private void refreshHeaders() {
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+token);
        log.info("setup headers...");
    }
    
    /**
     * Manual Login
     * @param username
     * @param password
     * @param unit
     * @return
     */
    @SuppressWarnings("unused")
    private HttpHeaders login(String username, String password, String unit) { 
        ResponseEntity<String> response = restTemplate.exchange("/auth/login?username="+username+""
                + "&password="+password,HttpMethod.GET,
                new HttpEntity<>(headers), String.class);
        //parse response JWT
        JsonNode jsonNode;
        try {
            jsonNode = new ObjectMapper().readTree(response.getBody());
            this.token = jsonNode.get("access_token").asText();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+token);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return headers;
    }
    
    /**
     * Automate Login For the First Time test run
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @BeforeAll
    public void authenticate() throws JsonMappingException, JsonProcessingException { 
        log.info("authenticate run");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> response = restTemplate.exchange("/auth/login?username=sa&password=asdf",HttpMethod.GET,
                new HttpEntity<>(headers), String.class);
        //parse response JWT
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        //parse any key in json to text
        this.token = jsonNode.get("access_token").asText();
        this.refreshToken = jsonNode.get("refresh_token").asText();
        refreshHeaders();
    }
    
}
