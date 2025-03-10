package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.LeaveEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.repository.LeaveNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class LeaveNativeRepositoryImpl implements LeaveNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<LeaveEntity> getAllLeavesByEmployee(Long employeeId,int l, int o){
        String sql = """
                select l.id, l.start_date, l.end_date, u.id, u.first_name, u.last_name from leaves l\s
                left join users u on u.id = l.approved_by\s
                where l.employee_id = ?\s
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
                            user,
                            null
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
                            user,
                            null
                    );
                },
                userId,limit,offset
        );
    }

    @Override
    public Map<String, Integer> getLeaveCounts(Long companyId) {
        String sql = """
                SELECT\s
                    DATE(l.end_date) AS leave_date,\s
                    SUM(l.day_count) AS total_leave_days\s
                FROM leaves l\s
                INNER JOIN employee e ON e.id = l.employee_id\s
                WHERE e.company_id = ? AND l.approved_by IS NOT null\s
                GROUP BY DATE(l.end_date)\s
                ORDER BY leave_date LIMIT 10
                """;

        return jdbcTemplate.query(sql,rs->{
            Map<String,Integer> leaveCountGroup = new HashMap<>();
            while(rs.next()){
                leaveCountGroup.put(rs.getString("leave_date"),rs.getInt("total_leave_days"));
            }
            return leaveCountGroup;
        },companyId);
    }

    @Override
    public Integer getLeaveCountsByUser(Integer userId) {
        String sql = """
                select count(l.id) from leaves l\s
                inner join employee e on e.id = l.employee_id\s
                where e.user_id = ? and l.approved_by is not null\s
                """;
        return jdbcTemplate.queryForObject(sql,Integer.class,userId);
    }

    @Override
    public Map<String, Integer> getLeaveCountsDatesByUser(Integer userId) {
        String sql = """
                SELECT\s
                DATE(l.end_date) AS leave_date,\s
                l.day_count AS leave_days\s
                FROM leaves l\s
                INNER JOIN employee e ON e.id = l.employee_id\s
                WHERE e.user_id = ? AND l.approved_by IS NOT null\s
                ORDER BY leave_date LIMIT 10
                """;

        return jdbcTemplate.query(sql,rs->{
            Map<String,Integer> leaveCounts = new HashMap<>();
            while(rs.next()){
                leaveCounts.put(rs.getString("leave_date"),rs.getInt("leave_days"));
            }
            return leaveCounts;
        },userId);
    }
}
