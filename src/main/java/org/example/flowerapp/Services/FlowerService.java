package org.example.flowerapp.Services; 

import org.example.flowerapp.Models.Enums.FlowerColor;
import org.example.flowerapp.Exceptions.DuplicateEntryException;
import org.example.flowerapp.Exceptions.ResourceNotFoundException;
import org.example.flowerapp.Models.Flower;
import org.example.flowerapp.Repository.FlowerRepository;
import org.example.flowerapp.Utils.Validators.FlowerValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowerService {

    private final FlowerRepository flowerRepository;

    public FlowerService(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    public Flower addFlower(Flower flower) {
        FlowerValidator.validateFlower(flower);
        checkDuplicateDB(flower, false); 
        return flowerRepository.save(flower);
    }

    public Flower getFlowerById(long id) {
        Flower f = flowerRepository.findById(id);
        if (f == null) {
            throw new ResourceNotFoundException("Flower", id);
        }
        return f;
    }

    public List<Flower> getAllFlowers() {
        return flowerRepository.findAllFlower();
    }

    public Flower updateFlower(Flower flower) {
        Flower existingFlower = getFlowerById(flower.getFlower_id()); 

        if (flower.getFlowerName() != null) {
            existingFlower.setFlowerName(flower.getFlowerName());
        }
        if (flower.getSpecies() != null) {
            existingFlower.setSpecies(flower.getSpecies());
        }
        if (flower.getColor() != null) {
            existingFlower.setColor(flower.getColor());
        }
        if (flower.getPlantingDate() != null) {
            existingFlower.setPlantingDate(flower.getPlantingDate());
        }
        
        FlowerValidator.validateFlower(existingFlower); 
        checkDuplicateDB(existingFlower, true); 
        return flowerRepository.save(existingFlower);
    }

    public boolean deleteFlower(long id) {
        getFlowerById(id); 
        return flowerRepository.deleteFlower(id);
    }

    public List<Flower> getBySpecies(String species) {
        return flowerRepository.findBySpecies(species);
    }

    public List<Flower> getByColor(FlowerColor color) {
        return flowerRepository.findByColor(color);
    }

    private void checkDuplicateDB(Flower flower, boolean isUpdate) {
        List<Flower> duplicates = flowerRepository.findDuplicates(
            flower.getFlowerName(), 
            flower.getSpecies(), 
            flower.getPlantingDate()
        );

        long count = duplicates.stream()
            .filter(f -> !isUpdate || f.getFlower_id() != flower.getFlower_id())
            .count();

        if (count > 0) {
            throw new DuplicateEntryException(flower.getFlowerName(), flower.getSpecies(), flower.getPlantingDate());
        }
    }
}