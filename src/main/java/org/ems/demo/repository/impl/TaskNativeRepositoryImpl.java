package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.TaskEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.repository.TaskNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TaskNativeRepositoryImpl implements TaskNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TaskEntity> getAllTaskByEmployee(Long employeeId, int limit, int offset) {
        String sql = """
                select t.id, t.task_name, t.start_date, t.due_date, t.completed_date, t.over_dues, t.status,\s
                u.id, u.first_name, u.last_name
                from tasks t\s
                left join users u on u.id = t.approved_by_id\s
                where t.employee_id = ?\s
                order by t.id desc limit ? offset ?
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

                    return new TaskEntity(
                            rs.getLong("t.id"),
                            rs.getString("t.task_name"),
                            rs.getDate("t.start_date"),
                            rs.getDate("t.due_date"),
                            rs.getDate("t.completed_date"),
                            rs.getInt("t.over_dues"),
                            null,
                            rs.getString("t.status"),
                            user
                    );
                },
                employeeId,limit,offset
        );
    }

    @Override
    public List<TaskEntity> getAllTaskByUser(Integer userId, int limit, int offset) {
        String sql = """
                select t.id, t.task_name, t.start_date, t.due_date, t.completed_date, t.over_dues, t.status,\s
                u.id, u.first_name, u.last_name\s
                from tasks t\s
                inner join employee e on e.id = t.employee_id\s
                left join users u on u.id = t.approved_by_id\s
                inner join users v on v.id = e.user_id\s
                where v.id = ?\s
                order by t.id desc limit ? offset ?
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

                    return new TaskEntity(
                            rs.getLong("t.id"),
                            rs.getString("t.task_name"),
                            rs.getDate("t.start_date"),
                            rs.getDate("t.due_date"),
                            rs.getDate("t.completed_date"),
                            rs.getInt("t.over_dues"),
                            null,
                            rs.getString("t.status"),
                            user
                    );
                },
                userId,limit,offset
        );
    }

    @Override
    public Map<String, Integer> getTasksByStatus(Long companyId) {
        String sql = """
                select t.status as status, count(t.id) as task_count from tasks t\s
                inner join employee e on e.id = t.employee_id\s
                where e.company_id = ?\s
                group by t.status
                """;

        return jdbcTemplate.query(sql,rs->{
            Map<String,Integer> taskCountGroup = new HashMap<>();
            while(rs.next()){
                taskCountGroup.put(rs.getString("status"),rs.getInt("task_count"));
            }
            return taskCountGroup;
        },companyId);
    }

    @Override
    public Integer getTasksCountByUser(Integer userId) {
        String sql = """
                select count(t.id) from tasks t\s
                inner join employee e on e.id = t.employee_id\s
                where e.user_id = ?
                """;
        return jdbcTemplate.queryForObject(sql,Integer.class,userId);
    }

    @Override
    public Map<String, Integer> getTasksByStatusByUser(Integer userId) {
        String sql = """
                select t.status as status, count(t.id) as task_count from tasks t\s
                inner join employee e on e.id = t.employee_id\s
                where e.user_id = ?\s
                group by t.status
                """;

        return jdbcTemplate.query(sql,rs->{
            Map<String,Integer> taskCountGroup = new HashMap<>();
            while(rs.next()){
                taskCountGroup.put(rs.getString("status"),rs.getInt("task_count"));
            }
            return taskCountGroup;
        },userId);
    }
}
