package org.example.flowerapp.Controller;

import org.example.flowerapp.Models.Enums.GrowthStage;
import org.example.flowerapp.Models.Growth;
import org.example.flowerapp.Services.GrowthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/growth")
public class GrowthController {

    private final GrowthService growthService;

    @Autowired
    public GrowthController(GrowthService growthService) {
        this.growthService = growthService;
    }

    @PostMapping
    public ResponseEntity<Growth> createGrowthRecord(@RequestBody Growth growth) {
        Growth savedRecord = growthService.addGrowthRecord(growth);
        return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Growth>> getAllGrowthRecords() {
        List<Growth> records = growthService.getAllGrowthRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Growth> getGrowthRecordById(@PathVariable long id) {
        Growth record = growthService.getGrowthById(id);
        return ResponseEntity.ok(record);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Growth> updateGrowthRecord(@PathVariable long id, @RequestBody Growth growth) {
        growth.setGrowth_id(id); 
        Growth updatedRecord = growthService.updateGrowthRecord(growth);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowthRecord(@PathVariable long id) {
        growthService.deleteGrowthRecord(id); 
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/flower")
    public ResponseEntity<List<Growth>> getRecordsByFlower(@RequestParam("id") long flowerId) {
        List<Growth> records = growthService.getRecordsByFlowerId(flowerId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/stage")
    public ResponseEntity<List<Growth>> getRecordsByStage(@RequestParam("name") GrowthStage stage) {
        List<Growth> records = growthService.getRecordsByStage(stage);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/color-changes")
    public ResponseEntity<List<Growth>> getRecordsByColorChanges(@RequestParam("status") boolean colorChanges) {
        List<Growth> records = growthService.getRecordsByColorChanges(colorChanges);
        return ResponseEntity.ok(records);
    }
}