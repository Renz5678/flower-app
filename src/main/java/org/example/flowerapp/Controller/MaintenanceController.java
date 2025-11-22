package org.example.flowerapp.Controller;

import org.example.flowerapp.Models.Maintenance;
import org.example.flowerapp.Models.Enums.MaintenanceType;
import org.example.flowerapp.Services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    @Autowired
    public MaintenanceController(MaintenanceService maintenanceService){
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<Maintenance> createMaintenanceTask(@RequestBody Maintenance maintenance){
        Maintenance savedTask = maintenanceService.addMaintenanceTask(maintenance);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Maintenance>> getAllTasks(){
        List<Maintenance> tasks = maintenanceService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getTaskById(@PathVariable long id){
        Maintenance task = maintenanceService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> updateMaintenanceTask(@PathVariable long id, @RequestBody Maintenance maintenance){
        maintenance.setTask_id(id);
        Maintenance updatedtasks = maintenanceService.updateMaintenanceTask(maintenance);
        return ResponseEntity.ok(updatedtasks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable long id){
        maintenanceService.deleteMaintenance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/flower")
    public ResponseEntity<List<Maintenance>> getTasksByFlower(@RequestParam("id") long flowerId) {
        List<Maintenance> tasks = maintenanceService.getTasksByFlower(flowerId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/type")
    public ResponseEntity<List<Maintenance>> getTaskByType(@RequestParam("name") MaintenanceType type){
        List<Maintenance> tasks = maintenanceService.getTasksByType(type); 
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Maintenance>> getTaskByDate(@RequestParam("time") LocalDateTime scheduledDate){
        List<Maintenance> tasks = maintenanceService.getTasksByDate(scheduledDate);
        return ResponseEntity.ok(tasks);
    }
}
