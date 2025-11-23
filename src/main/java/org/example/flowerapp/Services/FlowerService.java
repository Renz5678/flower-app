package org.example.flowerapp.Services;

import org.example.flowerapp.DTO.FlowerRequestDTO;
import org.example.flowerapp.DTO.FlowerResponseDTO;
import org.example.flowerapp.Exceptions.BusinessLogicExceptions.DuplicateFlowerException;
import org.example.flowerapp.Exceptions.EntityNotFoundExceptions.FlowerNotFoundException;
import org.example.flowerapp.Exceptions.ValidationExceptions.InvalidFlowerDataException;
import org.example.flowerapp.Models.Flower;
import org.example.flowerapp.Repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlowerService {

    @Autowired
    private FlowerRepository flowerRepository;

    @Transactional
    public FlowerResponseDTO addNewFlower(FlowerRequestDTO dto) {
        validateFlowerName(dto.getFlowerName());
        checkDuplicateFlowerName(dto.getFlowerName());

        Flower flower = new Flower();
        flower.setFlowerName(dto.getFlowerName());
        flower.setSpecies(dto.getSpecies());
        flower.setColor(dto.getColor());
        flower.setPlantingDate(dto.getPlantingDate());

        Flower saved = flowerRepository.save(flower);

        return mapToResponseDTO(saved);
    }

    @Transactional
    public FlowerResponseDTO updateFlower(FlowerRequestDTO dto, long id) {
        Flower flower = findFlowerByIdOrThrow(id);

        validateFlowerName(dto.getFlowerName());

        // Check for duplicate name only if the name is being changed
        if (!flower.getFlowerName().equals(dto.getFlowerName())) {
            checkDuplicateFlowerName(dto.getFlowerName());
        }

        flower.setFlowerName(dto.getFlowerName());
        flower.setSpecies(dto.getSpecies());
        flower.setColor(dto.getColor());
        flower.setPlantingDate(dto.getPlantingDate());

        Flower updated = flowerRepository.save(flower);

        return mapToResponseDTO(updated);
    }

    public FlowerResponseDTO getFlowerById(long id) {
        Flower flower = findFlowerByIdOrThrow(id);
        return mapToResponseDTO(flower);
    }

    public List<FlowerResponseDTO> getAllFlowers() {
        return flowerRepository.findAllFlower()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public List<FlowerResponseDTO> getAllFlowersBySpecies(String species) {
        return flowerRepository.findBySpecies(species)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public List<FlowerResponseDTO> getAllFlowerByColor(String color) {
        return flowerRepository.findByColor(color)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Transactional
    public void deleteFlower(long id) {
        findFlowerByIdOrThrow(id);
        flowerRepository.deleteFlower(id);
    }

    private void validateFlowerName(String flowerName) {
        if (flowerName == null || flowerName.isBlank()) {
            throw new InvalidFlowerDataException("Flower name is required!");
        }
    }

    private void checkDuplicateFlowerName(String flowerName) {
        if (flowerRepository.existsByName(flowerName)) {
            throw new DuplicateFlowerException("Flower name already exists!");
        }
    }

    private Flower findFlowerByIdOrThrow(long id) {
        Flower flower = flowerRepository.findByFlowerId(id);
        if (flower == null) {
            throw new FlowerNotFoundException("Flower with id " + id + " not found");
        }
        return flower;
    }

    private FlowerResponseDTO mapToResponseDTO(Flower flower) {
        FlowerResponseDTO flowerResponseDTO = new FlowerResponseDTO();

        flowerResponseDTO.setFlower_id(flower.getFlower_id());
        flowerResponseDTO.setFlowerName(flower.getFlowerName());
        flowerResponseDTO.setSpecies(flower.getSpecies());
        flowerResponseDTO.setColor(flower.getColor());
        flowerResponseDTO.setPlantingDate(flower.getPlantingDate());

        return flowerResponseDTO;
    }
}