package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.DepartmentEntity;
import org.ems.demo.repository.DepartmentNativeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DepartmentNativeRepositoryImpl implements DepartmentNativeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ModelMapper mapper;

    @Override
    public List<DepartmentEntity> getSelected(String l, String o) {
        String sql = "SELECT * FROM department ORDER BY id DESC LIMIT ? OFFSET ?";
        int limit = Integer.parseInt(l);
        int offset = Integer.parseInt(o);
        return jdbcTemplate.query(
                sql, // Pass limit and offset as query parameters
                (rs, rowNum) -> new DepartmentEntity(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        null
                ),
                limit, offset
        );
    }
}
