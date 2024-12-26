package com.nhnacademy.taskapi.adapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

@Service
@Slf4j
public class NhnImageManager {
    private static final String URL = "https://api-image.nhncloudservice.com/image/v2.0/appkeys/{appkey}/images";
    private static final String APP_KEY = "bgi6iWmF4yYnwQoo";
    private static final String SECRET_KEY = "5eV2wK0nslCUu42AwIFNiEhKLThoDK4H";


    public String uploadImage(byte[] imageBytes, String fileName) throws IOException{

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", SECRET_KEY);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);


        HttpEntity<byte[]> entity = new HttpEntity<>(imageBytes, headers);

        String url = URL.replace("{appkey}", APP_KEY) + "?path=/onebook/" + fileName + "&overwrite=true";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);


        if(response.getStatusCode() == HttpStatus.OK){
            log.info("Image upload: {}", "Image upload success");
            log.info("Response Body: {}", response.getBody());

            String imageUrl = extractUrl(response.getBody());
            log.info("Image Url: {}", imageUrl);
            return imageUrl;
        }else{
            log.info("Image upload: {}", "Image upload Fail");
            log.info("Response Body: {}", response.getBody());
            return null;
        }
    }

    private String extractUrl(String responseBody) {
        if (responseBody.contains("url")) {
            String prefix = "\"url\":\"";
            int startIdx = responseBody.indexOf(prefix) + prefix.length();
            int endIdx = responseBody.indexOf("\"", startIdx);
            return responseBody.substring(startIdx, endIdx);
        }
        return null;
    }


    //폴더 내 파일 목록 조회
    public String listFilesInFolder(String fileName) {
          // 조회하고자 하는 폴더 경로
        String url = "https://api-image.nhncloudservice.com/image/v2.0/appkeys/" + APP_KEY + "/folders?basepath=" + "/onebook";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", SECRET_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Folder contents: {}", response.getBody());
                // 여기서 파일 목록을 파싱하여 fileId를 추출
                String fileId = extractFileName(response.getBody(), fileName);
                log.info("File ID: {}", fileId);
                return fileId;
            } else {
                log.error("Failed to retrieve folder contents.");
            }
        } catch (Exception e) {
            log.error("Error while listing files in folder", e);
        }
        return null;
    }


    //폴더 내 파일ID 추출
    private String extractFileName(String responseBody, String fileName) {


        try{
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray files = jsonResponse.getAsJsonArray("files");

            for(int i = 0; i < files.size(); i++){
                JsonObject file = files.get(i).getAsJsonObject();
                String name = file.get("name").getAsString();

                if(name.equals(fileName)){
                    return file.get("id").getAsString();
                }
            }

        }catch(Exception e){
            log.error("Error while getting file name", e);
        }
        return null;


    }


    public void deleteImage(String fileName) {
        String fileId = listFilesInFolder(fileName);
        String url = "https://api-image.nhncloudservice.com/image/v2.0/appkeys/" + APP_KEY + "/images/sync?fileId=" + fileId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", SECRET_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("File deleted successfully.");
            } else {
                log.error("Failed to delete file.");
            }
        } catch (Exception e) {
            log.error("Error while deleting file", e);
        }
    }





}
