package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.dto.LeaveType;
import org.ems.demo.entity.LeaveEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.repository.LeaveNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class LeaveNativeRepositoryImpl implements LeaveNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<LeaveEntity> getAllLeavesByEmployee(Long employeeId,int l, int o){
        String sql = """
                select l.id, l.date, l.reason, l.leave_type, l.day_count, u.id, u.first_name, u.last_name from leaves l\s
                left join users u on u.id = l.approved_by\s
                where l.employee_id = ?\s
                order by l.id desc limit ? offset ?
                """;
        return jdbcTemplate.query(
                sql,
                (rs,rowNum)->{
                    UserEntity user = new UserEntity(
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
                            null,
                            null,
                            true,
                            null
                    );
                    LeaveType type = LeaveType.valueOf(rs.getString("l.leave_type"));
                    return new LeaveEntity(
                            rs.getLong("l.id"),
                            null,
                            rs.getDate("l.date"),
                            rs.getString("l.reason"),
                            user,
                            rs.getDouble("l.day_count"),
                            type
                    );
                },
                employeeId,l,o
        );
    }

    @Override
    public List<LeaveEntity> getAllLeavesByUser(Long userId, int limit, int offset) {
        String sql = """
                select l.id, l.date, l.reason, l.leave_type, l.day_count, u.id, u.first_name, u.last_name from leaves l\s
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
                    LeaveType type = LeaveType.valueOf(rs.getString("l.leave_type"));
                    return new LeaveEntity(
                            rs.getLong("l.id"),
                            null,
                            rs.getDate("l.date"),
                            rs.getString("l.reason"),
                            user,
                            rs.getDouble("l.day_count"),
                            type
                    );
                },
                userId,limit,offset
        );
    }

    @Override
    public Map<String, Double> getLeaveCounts(Long companyId) {
        String sql = """
                SELECT\s
                    DATE(l.date) AS leave_date,\s
                    SUM(l.day_count) AS total_leave_days\s
                FROM leaves l\s
                INNER JOIN employee e ON e.id = l.employee_id\s
                WHERE e.company_id = ? AND l.approved_by IS NOT null\s
                AND MONTH(l.date) = MONTH(CURRENT_DATE)\s
                AND YEAR(l.date) = YEAR(CURRENT_DATE)\s
                GROUP BY DATE(l.date)\s
                ORDER BY leave_date
                """;

        return jdbcTemplate.query(sql,rs->{
            Map<String,Double> leaveCountGroup = new LinkedHashMap<>();
            while(rs.next()){
                leaveCountGroup.put(rs.getString("leave_date"),rs.getDouble("total_leave_days"));
            }
            return leaveCountGroup;
        },companyId);
    }

    @Override
    public Integer getLeaveCountsByUser(Long userId) {
        String sql = """
                select count(l.id) from leaves l\s
                inner join employee e on e.id = l.employee_id\s
                where e.user_id = ? and l.approved_by is not null\s
                AND MONTH(l.date) = MONTH(CURRENT_DATE)\s
                AND YEAR(l.date) = YEAR(CURRENT_DATE)\s
                """;
        return jdbcTemplate.queryForObject(sql,Integer.class,userId);
    }

    @Override
    public Map<String, Double> getLeaveCountsDatesByUser(Long userId) {
        String sql = """
                SELECT\s
                DATE(l.date) AS leave_date,\s
                l.day_count AS leave_days\s
                FROM leaves l\s
                INNER JOIN employee e ON e.id = l.employee_id\s
                WHERE e.user_id = ? AND l.approved_by IS NOT null\s
                AND MONTH(l.date) = MONTH(CURRENT_DATE)\s
                AND YEAR(l.date) = YEAR(CURRENT_DATE)\s
                ORDER BY leave_date
                """;

        return jdbcTemplate.query(sql,rs->{
            Map<String,Double> leaveCounts = new LinkedHashMap<>();
            while(rs.next()){
                leaveCounts.put(rs.getString("leave_date"),rs.getDouble("leave_days"));
            }
            return leaveCounts;
        },userId);
    }

    @Override
    public Map<String, Double> getLeaveCategoriesCountsByUser(Long userId) {
        String sql = """
                SELECT\s
                l.leave_type as leave_type,\s
                SUM(l.day_count) AS leave_count\s
                FROM leaves l\s
                INNER JOIN employee e ON e.id = l.employee_id\s
                WHERE e.user_id = ? AND l.approved_by IS NOT null\s
                AND YEAR(l.date) = YEAR(CURRENT_DATE)\s
                GROUP BY l.leave_type
                """;

        return jdbcTemplate.query(sql,rs->{
            Map<String,Double> leaveCounts = new LinkedHashMap<>();
            while(rs.next()){
                leaveCounts.put(rs.getString("leave_type"),rs.getDouble("leave_count"));
            }
            return leaveCounts;
        },userId);
    }

    @Override
    public Double getLeaveCountByUserAndType(Long userId, String leaveType) {
        String sql = """
                select sum(l.day_count) from leaves l\s
                inner join employee e on e.id = l.employee_id\s
                where e.user_id = ? and l.approved_by is not null and l.leave_type = ?\s
                AND YEAR(l.date) = YEAR(CURRENT_DATE)\s
                """;
        return jdbcTemplate.queryForObject(sql,Double.class,userId,leaveType);
    }

    @Override
    public Map<String, Double> getEmployeeMonthlyLeaveCount(Long employeeId) {
        String sql = """
                select sum(day_count) from leaves\s
                where approved_by is not null and employee_id = ?\s
                AND MONTH(date) = MONTH(CURRENT_DATE)\s
                AND YEAR(date) = YEAR(CURRENT_DATE)\s
                """;
        Double count = jdbcTemplate.queryForObject(sql, Double.class, employeeId);
        Map<String,Double> leaveCounts = new HashMap<>();
        leaveCounts.put("leave_count",count);
        return leaveCounts;
    }
}
