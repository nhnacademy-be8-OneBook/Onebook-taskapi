package com.nhnacademy.taskapi.packaging.service.Impl;

import com.nhnacademy.taskapi.packaging.dto.PackagingCreateDTO;
import com.nhnacademy.taskapi.packaging.entity.Packaging;
import com.nhnacademy.taskapi.packaging.exception.PackagingAlreadyExistException;
import com.nhnacademy.taskapi.packaging.exception.PackagingNotFoundException;
import com.nhnacademy.taskapi.packaging.repository.PackagingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PackagingServiceImpl {

    private final PackagingRepository packagingRepository;

    // create
    public void createPackaging(PackagingCreateDTO packagingCreateDTO) {
        if (packagingRepository.existsByName((packagingCreateDTO.getName()))) {
            throw new PackagingAlreadyExistException(packagingCreateDTO.getName() + "is already exist name");
        }

        Packaging packaging = new Packaging(packagingCreateDTO.getName(), packagingCreateDTO.getPrice());

        packagingRepository.save(packaging);
    }

    // read
    public List<Packaging> getAllPackaging() {
        return packagingRepository.findAll();
    };

    public Packaging getPackagingById(int id) {
        return packagingRepository.findById(id).orElseThrow(() -> new PackagingNotFoundException(id + "is not found."));
    }

    // update

    // delete
    public void deletePackagingById(int id) {
        if (packagingRepository.existsById(id)) {
            throw new PackagingNotFoundException(id + "is not found");
        }
        packagingRepository.deleteById(id);
    }

}
