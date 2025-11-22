package org.example.flowerapp.Repository;

import org.example.flowerapp.Models.Enums.MaintenanceType;
import org.example.flowerapp.Models.Flower;
import org.example.flowerapp.Models.Maintenance;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class MaintenanceRepository {
    private final JdbcTemplate jdbc;
    private final FlowerRepository flowerRepository;

    public MaintenanceRepository(JdbcTemplate jdbc, FlowerRepository flowerRepository) {
        this.jdbc = jdbc;
        this.flowerRepository = flowerRepository;
    }

    private RowMapper<Maintenance> maintenanceRowMapper() {
        return(rs, i) -> {
            Maintenance maintenance = new Maintenance();

            maintenance.setTask_id(rs.getLong("maintenance_id"));

            Flower flower = flowerRepository.findById(rs.getLong("flower_id"));
            maintenance.setFlower(flower);
            maintenance.setTaskType(MaintenanceType.valueOf(rs.getString("maintenance_type")));

            java.sql.Timestamp ts = rs.getTimestamp("maintenance_date");
            maintenance.setScheduledDate(ts != null ? ts.toLocalDateTime() : null);

            maintenance.setNotes(rs.getString("notes"));
            maintenance.setPerformedBy(rs.getString("performed_by"));

            java.sql.Timestamp createdTs = rs.getTimestamp("created_at");
            maintenance.setCreatedAt(createdTs != null ? createdTs.toLocalDateTime() : null);
            return maintenance;
        };
    }

    public Maintenance save(Maintenance maintenance) {
        if (maintenance.getTask_id() == 0) {
            return add(maintenance);
        } else {
            update(maintenance);
            return maintenance;
        }
    }

    private Maintenance add(Maintenance maintenance) {
        String sql = "INSERT INTO maintenance (flower_id, maintenance_type, maintenance_date, notes, performed_by, created_at) VALUES(?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, maintenance.getFlower().getFlower_id());
            ps.setString(2, maintenance.getTaskType() != null ? maintenance.getTaskType().getMaintenanceType() : null);
            ps.setTimestamp(3, maintenance.getScheduledDate() != null ?
                    Timestamp.valueOf(maintenance.getScheduledDate()) : null);
            ps.setString(4, maintenance.getNotes());
            ps.setString(5, maintenance.getPerformedBy());
            ps.setTimestamp(6, maintenance.getCreatedAt() != null ?
                    Timestamp.valueOf(maintenance.getCreatedAt()) : null);
            return ps;
        }, keyHolder);

        maintenance.setTask_id(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return maintenance;
    }

    private void update(Maintenance maintenance) {
        String sql = "UPDATE maintenance SET maintenance_type = ?, maintenance_date = ?, notes = ?, performed_by = ?, created_at = ? WHERE maintenance_id = ?";
        jdbc.update(sql,
                maintenance.getTaskType() != null ? maintenance.getTaskType().getMaintenanceType() : null,
                maintenance.getScheduledDate() != null ? Timestamp.valueOf(maintenance.getScheduledDate()) : null,
                maintenance.getNotes(),
                maintenance.getPerformedBy(),
                maintenance.getCreatedAt() != null ? Timestamp.valueOf(maintenance.getCreatedAt()) : null,
                maintenance.getTask_id());
    }

    public Maintenance findByTaskId(long task_id) {
        String sql = "SELECT * FROM maintenance WHERE maintenance_id = ?";

        try {
            return jdbc.queryForObject(sql, new Object[]{task_id}, maintenanceRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Maintenance> findAllMaintenance() {
        String sql = "SELECT * FROM maintenance";
        return jdbc.query(sql, maintenanceRowMapper());
    }

    public List<Maintenance> findByFlowerId(Maintenance maintenance) {
        String sql = "SELECT * FROM maintenance WHERE flower_id = ?";
        return jdbc.query(sql, maintenanceRowMapper(), maintenance.getFlower().getFlower_id());
    }

    public List<Maintenance> findByMaintenanceType(Maintenance maintenance) {
        String sql = "SELECT * FROM maintenance WHERE maintenance_type = ?";
        return jdbc.query(sql, maintenanceRowMapper(), maintenance.getTaskType().getMaintenanceType());
    }

    public List<Maintenance> findByMaintenanceDate(Maintenance maintenance) {
        String sql = "SELECT * FROM maintenance WHERE maintenance_date = ?";
        return jdbc.query(sql, maintenanceRowMapper(), maintenance.getScheduledDate());
    }
}
