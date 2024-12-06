package com.example.CarDetails.Service;

import com.example.CarDetails.DTO.CarDTO;
import com.example.CarDetails.Modal.Cars;
import com.example.CarDetails.Repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarsService {

    @Autowired
    private CarsRepository carsRepository;

    public Cars addCar(Cars car) {
        validateImages(car.getImages());
        return carsRepository.save(car);
    }

    public List<CarDTO> getUserCars(Long userId) {
        return carsRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<Cars> getCarById(Long id) {
        return carsRepository.findById(id);
    }

    public Cars updateCar(Cars car) {
        validateImages(car.getImages()); // Validate image paths
        return carsRepository.save(car);
    }

    public void deleteCar(Cars car) {
        carsRepository.delete(car);
    }

    public List<CarDTO> searchCars(Long userId, String keyword) {
        return carsRepository.findByUserIdAndNameContainingIgnoreCaseOrUserIdAndDescriptionContainingIgnoreCaseOrUserIdAndTagsContainingIgnoreCase(
                        userId, keyword, userId, keyword, userId, keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convert entity to DTO
    public CarDTO convertToDTO(Cars car) {
        CarDTO dto = new CarDTO();
        dto.setProductId(car.getProductId());
        dto.setName(car.getName());
        dto.setBrand(car.getBrand());
        dto.setDescription(car.getDescription());
        dto.setImages(car.getImages()); // This now holds paths as strings
        dto.setTags(car.getTags());
        return dto;
    }

    private void validateImages(List<String> images) { // Ensure this is List<String>
        if (images.size() > 10) {
            throw new IllegalArgumentException("A car can have a maximum of 10 images.");
        }
    }
}