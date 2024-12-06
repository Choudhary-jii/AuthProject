package com.example.CarDetails.DTO;

import lombok.Data;
import java.util.List;

@Data
public class CarDTO {
    private Long productId;
    private String name;
    private String brand;
    private String description;
    private List<String> images; // Change to List<String> for image paths
    private List<String> tags;
}