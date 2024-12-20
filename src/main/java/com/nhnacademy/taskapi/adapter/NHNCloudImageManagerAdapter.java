package com.nhnacademy.taskapi.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class NHNCloudImageManagerAdapter {

    private final RestTemplate restTemplate;

    public NHNCloudImageManagerAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String API_URL = "https://api-image.nhncloudservice.com";

    public String uploadImageToImageManager(MultipartFile multipartFile) {
        String url = UriComponentsBuilder.fromHttpUrl(API_URL).path("/upload").toUriString();

        // HttpHeaders 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 파일을 Multipart로 보내기 위한 HttpEntity 설정
        HttpEntity<MultipartFile> entity = new HttpEntity<>(multipartFile, headers);

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // 응답에서 URL을 추출하여 반환 (예: 응답 본문에 URL이 포함되어 있다고 가정)
        return response.getBody();  // 반환된 URL 또는 응답을 처리
    }
}