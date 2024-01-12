package com.perpustakaan.app;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class PageDeserializer extends JsonDeserializer<Page<Object>> {
    
    @Override
    public Page<Object> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);
        // Deserialize content
        List<Object> content = mapper.convertValue(node.get("content"), new TypeReference<List<Object>>() {});
        // Deserialize pageable
        Pageable pageable = mapper.convertValue(node.get("pageable"), Pageable.class);
        // Deserialize other fields
        int totalPages = node.get("totalPages").asInt();
        long totalElements = node.get("totalElements").asLong();
        return new PageImpl<>(content, pageable, totalElements);
    }

}