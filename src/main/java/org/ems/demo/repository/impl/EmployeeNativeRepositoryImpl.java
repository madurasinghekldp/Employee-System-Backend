package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.DepartmentEntity;
import org.ems.demo.entity.EmployeeEntity;
import org.ems.demo.entity.RoleEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.repository.EmployeeNativeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeNativeRepositoryImpl implements EmployeeNativeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ModelMapper mapper;

    @Override
    public List<EmployeeEntity> getSelected(Long companyId, String l, String o, String s) {
        String sql = """
                select e.id, u.id, u.first_name, u.last_name, u.email, d.id as department_id, d.name as department_name, d.description as department_description
                , r.id as role_id, r.name as role_name, r.description as role_description\s
                from employee e\s
                left join department d on e.department_id = d.id\s
                left join role r on e.role_id = r.id\s
                inner join users u on e.user_id = u.id\s
                where e.company_id = ? and\s
                u.is_active = true and\s
                (u.email like ? or e.id like ? or u.first_name like ? or u.last_name like ?)\s
                order by e.id desc limit ? offset ?
                """;
        int limit = Integer.parseInt(l);
        int offset = Integer.parseInt(o);
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    DepartmentEntity department = new DepartmentEntity(
                            rs.getLong("department_id"),
                            rs.getString("department_name"),
                            rs.getString("department_description"),
                            null,
                            null
                    );
                    RoleEntity role = new RoleEntity(
                            rs.getLong("role_id"),
                            rs.getString("role_name"),
                            rs.getString("role_description"),
                            null,null
                    );

                    UserEntity user = new UserEntity(
                            rs.getInt("u.id"),
                            rs.getString("u.first_name"),
                            rs.getString("u.last_name"),
                            rs.getString("u.email"),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            true
                    );

                    return new EmployeeEntity(
                            rs.getLong("e.id"),
                            user,
                            department,
                            role,
                            null,null,null,null

                    );
                },

                companyId,"%"+s+"%","%"+s+"%","%"+s+"%","%"+s+"%", limit, offset);
    }

    @Override
    public List<EmployeeEntity> getAll(Long companyId, Long departmentId) {
        String sql = """
                select e.id, u.first_name, u.last_name from employee e\s
                inner join users u on u.id = e.user_id\s
                where e.company_id = ? and e.department_id = ? and u.is_active = true
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    UserEntity user = new UserEntity()
                            .setFirstName(rs.getString("u.first_name"))
                            .setLastName(rs.getString("u.last_name"));

                    return new EmployeeEntity(
                            rs.getLong("e.id"),
                            user,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    );
                },
                companyId,departmentId
        );
    }

    @Override
    public Integer getCount(Long companyId) {
        String sql = """
                select count(id) from employee where company_id = ?
                """;
        return jdbcTemplate.queryForObject(sql,Integer.class,companyId);
    }
}
