package org.example.flowerapp.Repository;

import org.example.flowerapp.Models.Enums.GrowthStage;
import org.example.flowerapp.Models.Flower;
import org.example.flowerapp.Models.Growth;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class GrowthRepository {
    private final JdbcTemplate jdbc;
    private final FlowerRepository flowerRepository;

    public GrowthRepository(JdbcTemplate jdbc, FlowerRepository flowerRepository) {
        this.jdbc = jdbc;
        this.flowerRepository = flowerRepository;
    }

    private RowMapper<Growth> growthRowMapper() {
        return(rs, i) -> {
            Growth growth = new Growth();

            growth.setGrowth_id(rs.getLong("growth_id"));

            Flower flower = flowerRepository.findById(rs.getLong("flower_id"));
            growth.setFlower(flower);

            growth.setStage(GrowthStage.valueOf(rs.getString("growth_stage")));

            growth.setHeight(rs.getDouble("height"));

            growth.setColorChanges(rs.getBoolean("color_changes"));

            growth.setNotes(rs.getString("notes"));
            return growth;
        };
    }
}
