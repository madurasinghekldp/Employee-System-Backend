package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.SalaryEntity;
import org.ems.demo.repository.SalaryNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SalaryNativeRepositoryImpl implements SalaryNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<SalaryEntity> getAllSalaryByEmployee(Long employeeId, int limit, int offset) {
        String sql = """
                select s.id, s.payment, s.payment_date from salary s\s
                where s.employee_id = ?\s
                order by s.id desc limit ? offset ?
                """;
        return jdbcTemplate.query(
                sql,
                (rs,rowNum)->{
                    return new SalaryEntity(
                            rs.getLong("s.id"),
                            rs.getDouble("s.payment"),
                            rs.getDate("s.payment_date"),
                            null
                    );
                },
                employeeId,limit,offset
        );
    }
}
