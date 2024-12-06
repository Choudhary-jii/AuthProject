package com.example.CarDetails.Repository;

import com.example.CarDetails.Modal.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarsRepository extends JpaRepository<Cars, Long> {

    List<Cars> findByUserId(Long userId);

    List<Cars> findByUserIdAndNameContainingIgnoreCaseOrUserIdAndDescriptionContainingIgnoreCaseOrUserIdAndTagsContainingIgnoreCase(
            Long userId, String name, Long userId2, String description, Long userId3, String tags);

}

