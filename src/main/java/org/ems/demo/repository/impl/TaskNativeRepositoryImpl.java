package org.ems.demo.repository.impl;

import lombok.RequiredArgsConstructor;
import org.ems.demo.entity.TaskEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.repository.TaskNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
                inner join employee e on e.id = t.employee_id\s
                left join users u on u.id = t.approved_by_id\s
                where e.id = ?\s
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
                u.id, u.first_name, u.last_name
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
}
