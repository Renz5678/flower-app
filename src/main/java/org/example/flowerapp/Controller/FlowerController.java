package org.example.flowerapp.Controller;

import lombok.RequiredArgsConstructor;
import org.example.flowerapp.DTO.FlowerRequestDTO;
import org.example.flowerapp.DTO.FlowerResponseDTO;
import org.example.flowerapp.Services.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flowers")
@RequiredArgsConstructor
public class FlowerController {

    private final FlowerService flowerService;

    @PostMapping
    public ResponseEntity<FlowerResponseDTO> createFlower(@RequestBody FlowerRequestDTO dto) {
        FlowerResponseDTO created = flowerService.addNewFlower(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<FlowerResponseDTO>> getAllFlowers() {
        List<FlowerResponseDTO> flowers = flowerService.getAllFlowers();
        return ResponseEntity.ok(flowers);
    }

    @GetMapping("/{flower_id}")
    public ResponseEntity<FlowerResponseDTO> getFlowerById(@PathVariable long id) {
        FlowerResponseDTO flower = flowerService.getFlowerById(id);
        return ResponseEntity.ok(flower);
    }

    @GetMapping("/species/{species}")
    public ResponseEntity<List<FlowerResponseDTO>> getFlowersBySpecies(@PathVariable String species) {
        List<FlowerResponseDTO> flowers = flowerService.getAllFlowersBySpecies(species);
        return ResponseEntity.ok(flowers);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<FlowerResponseDTO>> getFlowersByColor(@PathVariable String color) {
        List<FlowerResponseDTO> flowers = flowerService.getAllFlowerByColor(color);
        return ResponseEntity.ok(flowers);
    }

    @PutMapping("/{flower_id}")
    public ResponseEntity<FlowerResponseDTO> updateFlower(@RequestBody FlowerRequestDTO dto, long id) {
        FlowerResponseDTO updated = flowerService.updateFlower(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{flower_id}")
    public ResponseEntity<FlowerResponseDTO> deleteFlower(long id) {
        flowerService.deleteFlower(id);
        return ResponseEntity.noContent().build();
    }
}
