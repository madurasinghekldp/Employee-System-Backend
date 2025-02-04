package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.LeaveEntity;
import org.ems.demo.repository.LeaveNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LeaveNativeRepositoryImpl implements LeaveNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<LeaveEntity> getAllLeavesByEmployee(Long employeeId,int l, int o){
        String sql = """
                select l.id, l.start_date, l.end_date from leaves l\s
                inner join employee e on e.id = l.employee_id\s
                where e.id = ?\s
                order by l.id desc limit ? offset ?
                """;
        return jdbcTemplate.query(
                sql,
                (rs,rowNum)->{
                    return new LeaveEntity(
                            rs.getLong("l.id"),
                            null,
                            rs.getDate("l.start_date"),
                            rs.getDate("l.end_date")
                    );
                },
                employeeId,l,o
        );
    }
}
