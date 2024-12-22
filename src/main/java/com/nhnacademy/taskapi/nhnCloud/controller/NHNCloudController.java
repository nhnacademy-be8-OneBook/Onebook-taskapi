package com.nhnacademy.taskapi.nhnCloud.controller;

import com.nhnacademy.taskapi.nhnCloud.service.NHNCloudService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/nhncloud")
public class NHNCloudController {

    private final NHNCloudService nhnCloudService;

    public NHNCloudController(NHNCloudService nhnCloudService) {
        this.nhnCloudService = nhnCloudService;
    }

    // 폴더 생성
    @PostMapping("/createFolder")
    public ResponseEntity<String> createFolder(@RequestParam String folderName) {
        String result = nhnCloudService.createFolder(folderName);
        return ResponseEntity.ok(result);
    }

    // 폴더 내 파일 목록 조회
    @GetMapping("/listFiles")
    public ResponseEntity<String> listFiles(@RequestParam String folderPath) {
        String result = nhnCloudService.listFilesInFolder(folderPath);
        return ResponseEntity.ok(result);
    }

    // 파일 업로드
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam String folderPath, @RequestParam String fileName, @RequestBody byte[] fileData) {
        String result = nhnCloudService.uploadFile(folderPath, fileData, fileName);
        return ResponseEntity.ok(result);
    }

    // 파일 삭제
    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam String fileId) {
        String result = nhnCloudService.deleteFile(fileId);
        return ResponseEntity.ok(result);
    }

    // 폴더 속성 조회
    @GetMapping("/getFolderProperties")
    public ResponseEntity<String> getFolderProperties(@RequestParam String folderPath) {
        String result = nhnCloudService.getFolderProperties(folderPath);
        return ResponseEntity.ok(result);
    }
}