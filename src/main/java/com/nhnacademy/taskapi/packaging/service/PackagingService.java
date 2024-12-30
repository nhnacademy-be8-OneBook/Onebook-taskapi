package com.nhnacademy.taskapi.packaging.service;

import com.nhnacademy.taskapi.packaging.dto.PackagingCreateDTO;
import com.nhnacademy.taskapi.packaging.dto.PackagingResponseDTO;
import com.nhnacademy.taskapi.packaging.entity.Packaging;
import com.nhnacademy.taskapi.packaging.exception.PackagingAlreadyExistException;
import com.nhnacademy.taskapi.packaging.exception.PackagingNotFoundException;

import java.util.List;

public interface PackagingService {
    // create
    public void createPackaging(PackagingCreateDTO packagingCreateDTO);

    // read
    public List<PackagingResponseDTO> getAllPackaging();
    public Packaging getPackagingById(int id);

    // update

    // delete
    public void deletePackagingById(int id);
}
