package org.example.flowerapp.Controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.example.flowerapp.DTO.GrowthRequestDTO;
import org.example.flowerapp.DTO.GrowthResponseDTO;
import org.example.flowerapp.Models.Enums.GrowthStage;
import org.example.flowerapp.Services.GrowthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/growth")
@RequiredArgsConstructor
public class GrowthController {
    private final GrowthService growthService;

    @PostMapping
    public ResponseEntity<GrowthResponseDTO> createNewGrowthDetail(@RequestBody GrowthRequestDTO dto) {
        GrowthResponseDTO created = growthService.addNewGrowth(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<GrowthResponseDTO>> getAllGrowthDetails(@RequestBody GrowthRequestDTO dto) {
        List<GrowthResponseDTO> growthList = growthService.getAllGrowthDetails();
        return ResponseEntity.ok(growthList);
    }

    @GetMapping("/{growth_id}")
    public ResponseEntity<GrowthResponseDTO> getGrowthById(@RequestBody long id) {
        GrowthResponseDTO growth = growthService.getGrowthById(id);
        return ResponseEntity.ok(growth);
    }

    @GetMapping("{/growth_stage}")
    public ResponseEntity <List<GrowthResponseDTO>> getGrowthByStage(@RequestBody GrowthStage stage) {
        List<GrowthResponseDTO> growth = growthService.getGrowthByStage(stage);
        return ResponseEntity.ok(growth);
    }

    @GetMapping("/color_changes")
    public ResponseEntity<List<GrowthResponseDTO>> getGrowthByColorChanges(@RequestBody boolean colorChange) {
        List<GrowthResponseDTO> growth = growthService.getGrowthByColorChanges(colorChange);
        return ResponseEntity.ok(growth);
    }

    @PutMapping("/{growth_id}")
    public ResponseEntity<GrowthResponseDTO> updateGrowthDetails(@RequestBody GrowthRequestDTO dto, long id) {
        GrowthResponseDTO updated = growthService.updateGrowth(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{growth_id}")
    public ResponseEntity<GrowthRequestDTO> deleteGrowth(long id) {
        growthService.deleteGrowth(id);
        return ResponseEntity.noContent().build();
    }
}
