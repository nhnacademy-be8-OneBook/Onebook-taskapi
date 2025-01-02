package com.nhnacademy.taskapi.keyManager.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class KeyResponseDto {
    private Header header;
    private Body body;

    @Getter
    @NoArgsConstructor
    public static class Body {
        private String secret;

        public Map<String, String> getSecretAsMap() throws JsonProcessingException {
            Map<String, String> map;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                map = objectMapper.readValue(secret, new TypeReference<Map<String, String>>() {
                });
                // 데이터 처리
            } catch (JsonProcessingException e) {
                System.err.println("JSON 파싱 실패: " + e.getMessage());
                throw new RuntimeException("Invalid JSON format", e);
            }
            return map;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Header {
        private Integer resultCode;
        private String resultMessage;
        private boolean isSuccessful;
    }
}
