package org.example.flowerapp.Controllers;

import org.example.flowerapp.Models.Flower;
import org.example.flowerapp.Services.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flowers")
public class FlowerController {
    private final FlowerService flowerService;

    @Autowired
    public FlowerController(FlowerService flowerService){
        this.flowerService = flowerService;
    }

    @PostMapping
    public ResponseEntity<Flower> createFlower(@RequestBody Flower flower){
        Flower savedFlower = flowerService.addFlower(flower);
        return new ResponseEntity<>(savedFlower, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Flower>> getAllFlowers(){
        List<Flower> flowers = flowerService.getAllFlowers();
        return ResponseEntity.ok(flowers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flower> getFlowerById(@PathVariable long id){
        Flower flower = flowerService.getFlowerById(id);
        return ResponseEntity.ok{flower};
    }

}
