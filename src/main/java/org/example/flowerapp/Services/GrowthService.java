package org.example.flowerapp.Services;

import org.example.flowerapp.DTO.GrowthRequestDTO;
import org.example.flowerapp.DTO.GrowthResponseDTO;
import org.example.flowerapp.Exceptions.EntityNotFoundExceptions.FlowerNotFoundException;
import org.example.flowerapp.Exceptions.EntityNotFoundExceptions.GrowthNotFoundException;
import org.example.flowerapp.Exceptions.ValidationExceptions.InvalidGrowthDataException;
import org.example.flowerapp.Models.Enums.GrowthStage;
import org.example.flowerapp.Models.Flower;
import org.example.flowerapp.Models.Growth;
import org.example.flowerapp.Repository.FlowerRepository;
import org.example.flowerapp.Repository.GrowthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrowthService {

    @Autowired
    private GrowthRepository growthRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    @Transactional
    public GrowthResponseDTO addNewGrowth(GrowthRequestDTO dto) {
        validateGrowthData(dto);
        Flower flower = findFlowerByIdOrThrow(dto.getFlower_id());

        Growth growth = new Growth();
        growth.setFlower(flower);
        growth.setStage(dto.getStage());
        growth.setHeight(dto.getHeight());
        growth.setColorChanges(dto.isColorChanges());
        growth.setNotes(dto.getNotes());

        Growth saved = growthRepository.save(growth);

        return mapToResponseDTO(saved);
    }

    @Transactional
    public GrowthResponseDTO updateGrowth(GrowthRequestDTO dto, long id) {
        Growth growth = findGrowthByIdOrThrow(id);
        validateGrowthData(dto);
        Flower flower = findFlowerByIdOrThrow(dto.getFlower_id());

        growth.setFlower(flower);
        growth.setStage(dto.getStage());
        growth.setHeight(dto.getHeight());
        growth.setColorChanges(dto.isColorChanges());
        growth.setNotes(dto.getNotes());

        Growth saved = growthRepository.save(growth);

        return mapToResponseDTO(saved);
    }

    public GrowthResponseDTO getGrowthById(long id) {
        Growth growth = findGrowthByIdOrThrow(id);
        return mapToResponseDTO(growth);
    }

    public List<GrowthResponseDTO> getAllGrowthDetails() {
        return growthRepository.findAllGrowth()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public List<GrowthResponseDTO> getGrowthByStage(GrowthStage stage) {
        return growthRepository.findByStage(stage)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public List<GrowthResponseDTO> getGrowthByColorChanges(boolean colorChanges) {
        return growthRepository.findByColorChanges(colorChanges)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Transactional
    public void deleteGrowth(long id) {
        findGrowthByIdOrThrow(id);
        growthRepository.deleteGrowth(id);
    }

    private void validateGrowthData(GrowthRequestDTO dto) {
        if (dto.getFlower_id() <= 0) {
            throw new InvalidGrowthDataException("Flower ID is required");
        }

        if (dto.getHeight() < 0) {
            throw new InvalidGrowthDataException("Height cannot be negative");
        }

        if (dto.getStage() == null) {
            throw new InvalidGrowthDataException("Growth stage is required");
        }
    }

    private Flower findFlowerByIdOrThrow(long flowerId) {
        if (!flowerRepository.existsById(flowerId)) {
            throw new FlowerNotFoundException("Flower with id " + flowerId + " not found");
        }
        return flowerRepository.findByFlowerId(flowerId);
    }

    private Growth findGrowthByIdOrThrow(long id) {
        Growth growth = growthRepository.findByGrowthId(id);
        if (growth == null) {
            throw new GrowthNotFoundException("Growth Details with id " + id + " not found");
        }
        return growth;
    }

    private GrowthResponseDTO mapToResponseDTO(Growth growth) {
        GrowthResponseDTO growthDto = new GrowthResponseDTO();

        growthDto.setGrowth_id(growth.getGrowth_id());
        growthDto.setFlower_id(growth.getFlower().getFlower_id());
        growthDto.setStage(growth.getStage().getGrowthStage());
        growthDto.setHeight(growth.getHeight());
        growthDto.setColorChanges(growth.isColorChanges());
        growthDto.setNotes(growth.getNotes());

        return growthDto;
    }
}