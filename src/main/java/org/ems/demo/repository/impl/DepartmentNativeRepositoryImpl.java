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
    public List<DepartmentEntity> getSelected(Long companyId, String l, String o, String s) {
        String sql = """
                SELECT d.* FROM department d\s
                where d.company_id = ? and (d.id like ? or d.description like ? or d.name like ?)\s
                ORDER BY id DESC LIMIT ? OFFSET ?
                """;
        int limit = Integer.parseInt(l);
        int offset = Integer.parseInt(o);
        return jdbcTemplate.query(
                sql, // Pass limit and offset as query parameters
                (rs, rowNum) -> new DepartmentEntity(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        null,
                        null
                ),
                companyId,"%"+s+"%","%"+s+"%","%"+s+"%", limit, offset
        );
    }
}
