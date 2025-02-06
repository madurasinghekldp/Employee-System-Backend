package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.Task;
import org.ems.demo.entity.EmployeeEntity;
import org.ems.demo.entity.TaskEntity;
import org.ems.demo.exception.TaskException;
import org.ems.demo.repository.EmployeeRepository;
import org.ems.demo.repository.TaskNativeRepository;
import org.ems.demo.repository.TaskRepository;
import org.ems.demo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskNativeRepository taskNativeRepository;
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper mapper;

    @Override
    public Task createTask(Task task) {

        try{
            Optional<EmployeeEntity> employee = employeeRepository.findById(task.getEmployee().getId());
            if(employee.isEmpty()) throw new TaskException("Employee is not found!");
            TaskEntity taskEntity = mapper.convertValue(task, TaskEntity.class);
            taskEntity.setEmployee(employee.get());
            return mapper.convertValue(taskRepository.save(taskEntity), Task.class);
        }
        catch(Exception e){
            throw new TaskException("Task is not created");
        }
    }

    @Override
    public List<Task> getAllTask(Long employeeId, int limit, int offset) {
        try{
            List<TaskEntity> allTaskByEmployee = taskNativeRepository.getAllTaskByEmployee(employeeId, limit, offset);
            if(allTaskByEmployee.isEmpty()) throw new TaskException("Tasks not found!");
            List<Task> tasks = new ArrayList<>();
            allTaskByEmployee.forEach(task->{
                tasks.add(mapper.convertValue(task,Task.class));
            });
            return tasks;
        }
        catch(Exception e){
            log.info(e.toString());
            throw new TaskException("Unknown error occurred!");
        }
    }

    @Override
    public Task updateTask(Task task) {
        try{
            Optional<TaskEntity> taskById = taskRepository.findById(task.getId());
            if(taskById.isEmpty()) throw new TaskException("Task is not found!");
            TaskEntity taskEntity = taskById.get();
            taskEntity.setTaskName(task.getTaskName());
            taskEntity.setStartDate(task.getStartDate());
            taskEntity.setDueDate(task.getDueDate());
            taskEntity.setCompletedDate(task.getCompletedDate());
            if(!task.getCompletedDate().toString().isEmpty()){
                taskEntity.setOverDues(calculateOverDues(task.getCompletedDate(),task.getDueDate()));
            }
            return mapper.convertValue(taskRepository.save(taskEntity),Task.class);

        }
        catch(Exception e){
            throw new TaskException("Task is not updated!");
        }
    }

    @Override
    public void deleteTask(Long id) {
        try{
            Optional<TaskEntity> byId = taskRepository.findById(id);
            if(byId.isEmpty()) throw new TaskException("Task is not found!");
            taskRepository.deleteById(id);
        }
        catch(Exception e){
            throw new TaskException("Task is not deleted!");
        }
    }

    private int calculateOverDues(Date completedDate, Date dueDate) {
        int overDues = 0;
        if (completedDate != null && dueDate != null) {
            LocalDate due = convertToLocalDate(dueDate);
            LocalDate completed = convertToLocalDate(completedDate);
            return completed.isAfter(due) ? (int) ChronoUnit.DAYS.between(due, completed) : 0;
        } else {
            return overDues;
        }
    }

    private LocalDate convertToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
