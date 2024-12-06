package com.example.CarDetails.Controller;

import org.springframework.web.multipart.MultipartFile;
import com.example.CarDetails.DTO.CarDTO;
import com.example.CarDetails.Helper.HelpingFunction;
import com.example.CarDetails.Modal.Cars;
import com.example.CarDetails.Modal.Users;
import com.example.CarDetails.Service.CarsService;
import com.example.CarDetails.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

    private final CarsService carsService;
    private final UserRepository usersRepository;
    private final HelpingFunction helpingFunction;

    @Autowired
    public CarsController(CarsService carsService, UserRepository usersRepository, HelpingFunction helpingFunction) {
        this.carsService = carsService;
        this.usersRepository = usersRepository;
        this.helpingFunction = helpingFunction;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCar(@RequestHeader("Authorization") String token,
                                         @RequestParam("name") String name,
                                         @RequestParam("brand") String brand,
                                         @RequestParam("description") String description,
                                         @RequestParam("tags") List<String> tags,
                                         @RequestParam("images") MultipartFile[] images) { // Accept multiple files

        Long userId = helpingFunction.extractUserIdFromToken(token);
        Optional<Users> userOptional = usersRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found.", HttpStatus.UNAUTHORIZED);
        }

        Cars car = new Cars();
        car.setName(name);
        car.setBrand(brand);
        car.setDescription(description);

        // Save images and get paths
        List<String> imagePaths = new ArrayList<>();

        try {
            for (MultipartFile image : images) {
                // Save each image and get the path
                String imagePath = saveImage(image); // Implement this method to save the image and return the path
                imagePaths.add(imagePath);
            }
            car.setImages(imagePaths); // Set the list of image paths
            car.setTags(tags);
            car.setUser(userOptional.get());

            carsService.addCar(car);
            return new ResponseEntity<>("Car added successfully.", HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload images.", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @GetMapping("/my-cars")
    public ResponseEntity<List<CarDTO>> getUserCars(@RequestHeader("Authorization") String token) {
        Long userId = helpingFunction.extractUserIdFromToken(token);
        List<CarDTO> cars = carsService.getUserCars(userId);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@RequestHeader("Authorization") String token,
                                             @PathVariable Long id) {
        Long userId = helpingFunction.extractUserIdFromToken(token);
        Optional<Cars> carOptional = carsService.getCarById(id);

        if (carOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Cars car = carOptional.get();
        if (!car.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Access denied.");
        }

        CarDTO carDto = carsService.convertToDTO(car); // Convert to DTO here
        return new ResponseEntity<>(carDto, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCar(@RequestHeader("Authorization") String token,
                                            @PathVariable Long id,
                                            @RequestParam("name") String name,
                                            @RequestParam("brand") String brand,
                                            @RequestParam("description") String description,
                                            @RequestParam("tags") List<String> tags,
                                            @RequestParam(value = "images", required = false) MultipartFile[] images) { // Accept multiple files

        Long userId = helpingFunction.extractUserIdFromToken(token);
        Optional<Cars> carOptional = carsService.getCarById(id);

        if (carOptional.isEmpty()) {
            return new ResponseEntity<>("Car not found.", HttpStatus.NOT_FOUND);
        }

        Cars car = carOptional.get();
        if (!car.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Access denied.");
        }

        // Update fields from request parameters
        car.setName(name);
        car.setBrand(brand);
        car.setDescription(description);
        car.setTags(tags);

        // Handle image updates
        List<String> imagePaths = new ArrayList<>();

        try {
            // If new images are uploaded, save them and update the paths
            if (images != null) {
                for (MultipartFile image : images) {
                    String imagePath = saveImage(image); // Save the new image and get its path
                    imagePaths.add(imagePath);
                }
            }

            // Get existing images
            List<String> existingImages = car.getImages();

            // Delete old images that are not part of the new upload
            for (String existingImage : existingImages) {
                if (!imagePaths.contains(existingImage)) {
                    File fileToDelete = new File(existingImage);
                    if (fileToDelete.exists()) {
                        boolean deleted = fileToDelete.delete(); // Delete the file from filesystem
                        if (!deleted) {
                            System.err.println("Failed to delete file: " + existingImage);
                        }
                    }
                }
            }

            // Update the car's images with new paths
            existingImages.clear(); // Clear old paths
            existingImages.addAll(imagePaths); // Add new paths
            car.setImages(existingImages); // Update the car's images

            carsService.updateCar(car); // Save updated car entity
            return new ResponseEntity<>("Car updated successfully.", HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload images.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<CarDTO>> searchCars(@RequestHeader("Authorization") String token,
                                                   @RequestParam String keyword) {
        Long userId = helpingFunction.extractUserIdFromToken(token);
        List<CarDTO> cars = carsService.searchCars(userId, keyword);

        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@RequestHeader("Authorization") String token,
                                            @PathVariable Long id) {
        Long userId = helpingFunction.extractUserIdFromToken(token);
        Optional<Cars> carOptional = carsService.getCarById(id);

        if (carOptional.isEmpty()) {
            return new ResponseEntity<>("Car not found.", HttpStatus.NOT_FOUND);
        }

        Cars car = carOptional.get();
        if (!car.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Access denied.");
        }

        // Optionally delete associated images from filesystem
        for (String imagePath : car.getImages()) {
            File file = new File(imagePath);
            if (file.exists()) {
                file.delete(); // Delete the file from the filesystem
            }
        }

        carsService.deleteCar(car); // Call the delete method from service
        return new ResponseEntity<>("Car deleted successfully.", HttpStatus.OK);
    }

    // Implement a method to save images in a specific folder
    private String saveImage(MultipartFile image) throws IOException {
        // Specify the directory where you want to save images, e.g., "uploads/"
        String uploadDir = "uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory if it doesn't exist
        }

        // Generate a unique filename based on current time or UUID
        String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filepath = Paths.get(uploadDir + filename);

        // Save the file to that path
        Files.copy(image.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        return filepath.toString(); // Return the path as a string for database storage
    }

}
