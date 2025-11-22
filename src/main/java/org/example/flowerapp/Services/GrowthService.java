package org.example.flowerapp.Services;


import org.example.flowerapp.Models.Enums.GrowthStage;
import org.example.flowerapp.Models.Growth;
import org.example.flowerapp.Repository.GrowthRepository;
import org.example.flowerapp.Utils.Validators.GrowthValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrowthService {
    
    private final GrowthRepository growthRepository;
    private final FlowerService flowerService;

    public GrowthService(GrowthRepository growthRepository, FlowerService flowerService){
        this.growthRepository = growthRepository;
        this.flowerService = flowerService;
    }

public Growth addGrowthRecord(Growth growth) {
        GrowthValidator.validateGrowth(growth);
        
        flowerService.getFlowerById(growth.getFlower().getFlower_id());

        return growthRepository.save(growth);
    }

    public Growth getGrowthById(long id) {
        return growthRepository.findByGrowthId(id);
    }

    public List<Growth> getAllGrowthRecords() {
        return growthRepository.findAllGrowth();
    }

    public Growth updateGrowthRecord(Growth growth) {
        Growth existingRecord = getGrowthById(growth.getGrowth_id());
        
        if (growth.getStage() != null) {
            existingRecord.setStage(growth.getStage());
        }

        if (growth.getHeight() > 0 || (growth.getHeight() == 0 && existingRecord.getHeight() != 0)) {
            existingRecord.setHeight(growth.getHeight());
        }
        
        existingRecord.setColorChanges(growth.isColorChanges()); 

        if (growth.getNotes() != null) {
            existingRecord.setNotes(growth.getNotes());
        }
        
        GrowthValidator.validateGrowth(existingRecord);

        return growthRepository.save(existingRecord);
    }

    public boolean deleteGrowthRecord(long id) {
        return growthRepository.deleteGrowth(id);
    }

    public List<Growth> getRecordsByFlowerId(long flowerId) {
        flowerService.getFlowerById(flowerId); 
        return growthRepository.findByFlowerId(flowerId);
    }

    public List<Growth> getRecordsByStage(GrowthStage stage) {
        return growthRepository.findByStage(stage);
    }

    public List<Growth> getRecordsByColorChanges(boolean colorChanges) {
        return growthRepository.findByColorChanges(colorChanges);
    }
}
