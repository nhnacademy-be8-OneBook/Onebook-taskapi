package com.nhnacademy.taskapi.nhnCloud.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NHNCloudService {

    @Value("${nhncloud.appKey}")
    private String appKey;

    @Value("${nhncloud.secretKey}")
    private String secretKey;

    private final RestTemplate restTemplate;

    public NHNCloudService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String BASE_URL = "https://api-image.nhncloudservice.com/image/v2.0/appkeys/";

    // 폴더 생성
    public String createFolder(String folderName) {
        String url = BASE_URL + appKey + "/folders";
        String requestBody = String.format("{\"path\": \"/%s\"}", folderName);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", secretKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    // 폴더 내 파일 목록 조회
    public String listFilesInFolder(String folderPath) {
        String url = BASE_URL + appKey + "/folders?basepath=" + folderPath;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", secretKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    // 파일 업로드
    public String uploadFile(String folderPath, byte[] fileData, String fileName) {
        String url = BASE_URL + appKey + "/images?path=" + folderPath + "/" + fileName + "&overwrite=true";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", secretKey);
        headers.set("Content-Type", "application/octet-stream");

        HttpEntity<byte[]> entity = new HttpEntity<>(fileData, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        return response.getBody();
    }

    // 파일 삭제
    public String deleteFile(String fileId) {
        String url = BASE_URL + appKey + "/images/sync?fileId=" + fileId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", secretKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        return response.getBody();
    }

    // 폴더 속성 조회
    public String getFolderProperties(String folderPath) {
        String url = BASE_URL + appKey + "/properties?path=" + folderPath;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", secretKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
