package org.example.flowerapp.Repository;

import org.example.flowerapp.Models.Enums.FlowerColor;
import org.example.flowerapp.Models.Flower;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class FlowerRepository {
    private final JdbcTemplate jdbc;

    public FlowerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    private RowMapper<Flower> flowerRowMapper() {
        return(rs, i) -> {
            Flower flower = new Flower();

            flower.setFlower_id(rs.getLong("flower_id"));
            flower.setFlowerName(rs.getString("flower_name"));
            flower.setSpecies(rs.getString("species"));
            String colorStr = rs.getString("color");
            flower.setColor(colorStr != null ? FlowerColor.valueOf(colorStr) : null);
            flower.setPlantingDate(rs.getDate("planting_date"));

            return flower;
        };
    }

    public Flower save(Flower flower) {
        if (flower.getFlower_id() == 0) {
            return add(flower);
        } else {
            update(flower);
            return flower;
        }
    }

    private Flower add(Flower flower) {
        String sql = "INSERT INTO flowerdetails (flower_name, species, color, planting_date) VALUES(?, ?, ?, ?)";
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, flower.getFlowerName());
            ps.setString(2, flower.getSpecies());
            ps.setString(3, flower.getColor() != null ? flower.getColor().getColorName() : null);
            ps.setDate(4, flower.getPlantingDate() != null ?
                    new java.sql.Date(flower.getPlantingDate().getTime()) : null);
            return ps;
        }, keyholder);

        return new Flower(
                Objects.requireNonNull(keyholder.getKey()).longValue(),
                flower.getFlowerName(),
                flower.getSpecies(),
                flower.getColor(),
                flower.getPlantingDate()
        );
    }

    private void update(Flower flower) {
        String sql = "UPDATE flowerdetails SET flower_name = ?, species = ?, color = ?, planting_date = ? WHERE flower_id = ?";
        jdbc.update(sql,
                flower.getFlowerName(),
                flower.getSpecies(),
                flower.getColor() != null ? flower.getColor().getColorName() : null,
                flower.getPlantingDate(),
                flower.getFlower_id());
    }


    public List<Flower> findAllFlower() {
        String sql = "SELECT * FROM flowerdetails";
        return jdbc.query(sql, flowerRowMapper());
    }

    public Flower findById(long flowerId) {
        String sql = "SELECT * FROM flowerdetails WHERE flower_id = ?";

        try {
            return jdbc.queryForObject(sql, new Object[]{flowerId}, flowerRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Flower> findBySpecies(String species) {
        String sql = "SELECT * FROM flowerdetails WHERE species = ?";
        return jdbc.query(sql, flowerRowMapper(), species);
    }

    public List <Flower> findByColor(FlowerColor color) {
        String sql = "SELECT * FROM flowerdetails WHERE color = ?";
        return jdbc.query(sql, flowerRowMapper(), color.getColorName());
    }

    public boolean deleteFlower(long id) {
        String sql = "DELETE FROM flowerdetails WHERE flower_id = ?";
        return jdbc.update(sql, id) != 0;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM flowerdetails";
        Long count = jdbc.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }
}
