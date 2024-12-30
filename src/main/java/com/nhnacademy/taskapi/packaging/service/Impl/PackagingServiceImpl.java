package com.nhnacademy.taskapi.packaging.service.Impl;

import com.nhnacademy.taskapi.packaging.dto.PackagingCreateDTO;
import com.nhnacademy.taskapi.packaging.dto.PackagingResponseDTO;
import com.nhnacademy.taskapi.packaging.entity.Packaging;
import com.nhnacademy.taskapi.packaging.exception.PackagingAlreadyExistException;
import com.nhnacademy.taskapi.packaging.exception.PackagingNotFoundException;
import com.nhnacademy.taskapi.packaging.repository.PackagingRepository;
import com.nhnacademy.taskapi.packaging.service.PackagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PackagingServiceImpl implements PackagingService {

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
    public List<PackagingResponseDTO> getAllPackaging() {
        List<PackagingResponseDTO> list = packagingRepository.findAll().stream().map(PackagingResponseDTO::fromPackaging).toList();
        return list;
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
