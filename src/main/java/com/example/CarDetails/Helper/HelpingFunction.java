package com.example.CarDetails.Helper;
//import com.example.CarDetails.DTO.CarDTO;
//import com.example.CarDetails.Modal.Cars;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class HelpingFunction {

    private final String SECRET_KEY = "Z1v6Nk5t2Mhx7G3KXxH5C3R7pBw9Vq6kA8d4TnS2kP1D2g3J9YwG0N5sX9Hb7q4";

    public String getSECRET_KEY() {
        return SECRET_KEY;

    }

    public Long extractUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        return Long.parseLong(claims.get("userId").toString());
    }

}

    // Convert entity to DTO
//    private CarDTO convertToDTO(Cars car) {
//        CarDTO dto = new CarDTO();
//        dto.setProductId(car.getProductId());
//        dto.setName(car.getName());
//        dto.setBrand(car.getBrand());
//        dto.setDescription(car.getDescription());
//        dto.setImages(car.getImages());
//        dto.setTags(car.getTags());
//        return dto;
//    }
//}
