package com.nhnacademy.taskapi.packaging.dto;

import com.nhnacademy.taskapi.order.dto.OrderResponseDTO;
import com.nhnacademy.taskapi.order.entity.Order;
import com.nhnacademy.taskapi.packaging.entity.Packaging;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PackagingResponseDTO {
    int id;
    String name;
    int price;

    public static PackagingResponseDTO fromPackaging(Packaging packaging) {
        return new PackagingResponseDTO(
                packaging.getId(),
                packaging.getName(),
                packaging.getPrice()
        );
    }
}
