package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.CompanyEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.repository.UserNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserNativeRepositoryImpl implements UserNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserEntity getUserByEmail(String email) {
        String sql = """
                select u.id, u.email, u.first_name, u.last_name, u.profile_image, c.id, c.name, c.address,\s
                c.register_number, c.annual_leaves, c.casual_leaves, c.logo\s
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
                            null,null,null,null,rs.getInt("c.annual_leaves"),
                            rs.getInt("c.casual_leaves"),
                            rs.getString("c.logo")
                    );
                    return new UserEntity(
                            rs.getLong("u.id"),
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
                            null,
                            null,
                            true,
                            rs.getString("u.profile_image")
                    );
                },
                email
        );
    }

    @Override
    public List<UserEntity> getCompanyUsers(Long companyId) {
        String sql = """
                select u.id,u.first_name,u.last_name from users u\s
                inner join user_roles_at ur on u.id = ur.user_id\s
                inner join user_roles r on ur.role_id = r.id\s
                where u.company_id = ? and r.name != 'ROLE_EMP'
                """;
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    return new UserEntity(
                            rs.getLong("u.id"),
                            rs.getString("u.first_name"),
                            rs.getString("u.last_name"),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,null,
                            true,
                            null
                    );
                },companyId
        );
    }
}
