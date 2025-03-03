package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.LeaveEntity;
import org.ems.demo.entity.UserEntity;
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
                select l.id, l.start_date, l.end_date, u.id, u.first_name, u.last_name from leaves l\s
                inner join employee e on e.id = l.employee_id\s
                left join users u on u.id = l.approved_by\s
                where e.id = ?\s
                order by l.id desc limit ? offset ?
                """;
        return jdbcTemplate.query(
                sql,
                (rs,rowNum)->{
                    UserEntity user = new UserEntity(
                            rs.getInt("u.id"),
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
                            true
                    );
                    return new LeaveEntity(
                            rs.getLong("l.id"),
                            null,
                            rs.getDate("l.start_date"),
                            rs.getDate("l.end_date"),
                            user
                    );
                },
                employeeId,l,o
        );
    }

    @Override
    public List<LeaveEntity> getAllLeavesByEmployeeByUser(Integer userId, int limit, int offset) {
        String sql = """
                select l.id, l.start_date, l.end_date, u.id, u.first_name, u.last_name from leaves l\s
                inner join employee e on e.id = l.employee_id\s
                left join users u on u.id = l.approved_by\s
                inner join users v on v.id = e.user_id\s
                where v.id = ?\s
                order by l.id desc limit ? offset ?
                """;
        return jdbcTemplate.query(
                sql,
                (rs,rowNum)->{
                    UserEntity user = new UserEntity(
                            rs.getInt("u.id"),
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
                            true
                    );
                    return new LeaveEntity(
                            rs.getLong("l.id"),
                            null,
                            rs.getDate("l.start_date"),
                            rs.getDate("l.end_date"),
                            user
                    );
                },
                userId,limit,offset
        );
    }
}
