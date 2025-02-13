package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.RoleEntity;
import org.ems.demo.repository.RoleNativeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleNativeRepositoryImpl implements RoleNativeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ModelMapper mapper;

    @Override
    public List<RoleEntity> getAllSelected(Long companyId, String l, String o, String s) {
        String sql = """
                SELECT * FROM role\s
                where company_id = ? and (id like ? or description like ? or name like ?)\s
                ORDER BY id DESC LIMIT ? OFFSET ?
                """;
        int limit = Integer.parseInt(l);
        int offset = Integer.parseInt(o);
        return jdbcTemplate.query(
                sql, // Pass limit and offset as query parameters
                (rs, rowNum) -> new RoleEntity(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        null,null
                ),
                companyId,"%"+s+"%","%"+s+"%","%"+s+"%", limit, offset
        );
    }
}
