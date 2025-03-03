package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.CompanyEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.repository.UserNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserNativeRepositoryImpl implements UserNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserEntity getUserByEmail(String email) {
        String sql = """
                select u.id, u.email, u.first_name, u.last_name, c.id, c.name, c.address, c.register_number\s
                from users u\s
                inner join company c on u.company_id = c.id\s
                where u.email = ?
                """;
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) ->{
                    CompanyEntity company = new CompanyEntity(
                            rs.getLong("c.id"),
                            rs.getString("c.name"),
                            rs.getString("c.address"),
                            rs.getString("c.register_number"),
                            null,null,null,null
                    );
                    return new UserEntity(
                            rs.getInt("u.id"),
                            rs.getString("u.first_name"),
                            rs.getString("u.last_name"),
                            rs.getString("u.email"),
                            null,
                            null,
                            company,
                            null,
                            null,
                            null,
                            null,
                            true
                    );
                },
                email
        );
    }
}
