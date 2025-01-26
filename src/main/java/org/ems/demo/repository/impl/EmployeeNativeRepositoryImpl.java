package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.DepartmentEntity;
import org.ems.demo.entity.EmployeeEntity;
import org.ems.demo.entity.RoleEntity;
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
    public List<EmployeeEntity> getSelected(String l, String o, String s) {
        String sql = """
                select e.*, d.id as department_id, d.name as department_name, d.description as department_description
                , r.id as role_id, r.name as role_name, r.description as role_description\s
                from employee e\s
                left join department d on e.department_id = d.id\s
                left join role r on e.role_id = r.id\s
                where e.email like ? or e.id like ? or e.first_name like ? or e.last_name like ?\s
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
                            null
                    );
                    RoleEntity role = new RoleEntity(
                            rs.getLong("role_id"),
                            rs.getString("role_name"),
                            rs.getString("role_description"),
                            null,null
                    );
                    return new EmployeeEntity(
                            rs.getLong("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            department,
                            role,
                            null,null,null,null

                    );
                },

                "%"+s+"%","%"+s+"%","%"+s+"%","%"+s+"%", limit, offset);
    }
}
