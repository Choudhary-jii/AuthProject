//package com.example.CarDetails.Modal;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.*;
//import jakarta.persistence.Table;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@Getter
//@Setter
//@Entity
//@Table(name = "cars")
//public class Cars {
//
//    @Id
//    @Column(name = "product_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long productId;
//
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @Column(name = "brand", nullable = false)
//    private String brand;
//
//    @Column(name = "description", nullable = false)
//    private String description;
//
//    // Store image paths as strings
//    @ElementCollection
//    @CollectionTable(name = "car_images", joinColumns = @JoinColumn(name = "car_id"))
//    private List<String> images = new ArrayList<>(); // Store image paths
//
//    @ElementCollection
//    private List<String> tags = new ArrayList<>();
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Users user;
//}
