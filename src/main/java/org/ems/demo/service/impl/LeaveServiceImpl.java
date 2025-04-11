package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.Leave;
import org.ems.demo.dto.LeaveByUser;
import org.ems.demo.dto.User;
import org.ems.demo.entity.EmployeeEntity;
import org.ems.demo.entity.LeaveEntity;
import org.ems.demo.entity.NotificationEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.exception.LeaveException;
import org.ems.demo.repository.*;
import org.ems.demo.service.EmailService;
import org.ems.demo.service.LeaveService;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveNativeRepository leaveNativeRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ObjectMapper mapper;
    private final NotificationRepository notificationRepository;

    @Override
    public Leave createLeave(LeaveByUser leave,Long userId) {
        try{
            Optional<UserEntity> byId = userRepository.findById(leave.getUser().getId());
            if(byId.isEmpty()){
                throw new LeaveException("User is not found!");
            }
            Optional<EmployeeEntity> employeeEntity = employeeRepository.findByUser(byId.get());
            if(employeeEntity.isEmpty()){
                throw new LeaveException("Employee is not found!");
            }

            if(String.valueOf(leave.getLeaveType()).equals("ANNUAL")){
                Double annuals = Double.valueOf(employeeEntity.get().getCompany().getAnnualLeaves());
                Double annualTotal = leaveNativeRepository.getLeaveCountByUserAndType(byId.get().getId(), "ANNUAL");
                if(annualTotal==null) annualTotal = 0.0;
                if((annualTotal+leave.getDayCount())>annuals) throw new LeaveException("You are going to exceed available annual leave count.");
            }
            if(String.valueOf(leave.getLeaveType()).equals("CASUAL")){
                Double casuals = Double.valueOf(employeeEntity.get().getCompany().getCasualLeaves());
                Double casualTotal = leaveNativeRepository.getLeaveCountByUserAndType(byId.get().getId(), "CASUAL");
                if(casualTotal==null) casualTotal = 0.0;
                if((casualTotal+leave.getDayCount())>casuals) throw new LeaveException("You are going to exceed available casual leave count.");
            }
            LeaveEntity leaveEntity = mapper.convertValue(leave,LeaveEntity.class);
            leaveEntity.setDayCount(leave.getDayCount());
            leaveEntity.setEmployee(employeeEntity.get());

            notificationRepository.save(
                    new NotificationEntity(
                            null,
                            byId.get(),
                            userRepository.findById(userId).get(),
                            String.format("%s %s applied for a leave",byId.get().getFirstName(),byId.get().getLastName()),
                            false
                    )
            );
            return mapper.convertValue(leaveRepository.save(leaveEntity), Leave.class);
        }
        catch(LeaveException e){
            throw e;
        }
        catch(Exception e){
            log.info(e.toString());
            throw new LeaveException("Leave is not added!");
        }
    }

    @Override
    public List<Leave> getAllLeaves(Long employeeId,int limit,int offset) {
        try{
            List<LeaveEntity> allLeavesByEmployee = leaveNativeRepository.getAllLeavesByEmployee(employeeId,limit,offset);
            if(allLeavesByEmployee.isEmpty()) throw new LeaveException("Leaves not found");
            List<Leave> leaveList = new ArrayList<>();
            allLeavesByEmployee.forEach(
                    leaveEntity->{
                        User user = mapper.convertValue(leaveEntity.getApprovedBy(), User.class);
                        Leave leave = mapper.convertValue(leaveEntity, Leave.class);
                        leave.setApprovedBy(user);
                        leaveList.add(leave);
                    }
            );
            return leaveList;
        }
        catch(LeaveException e){
            throw e;
        }
        catch(Exception e){
            throw new LeaveException("Unknown error occurred");
        }
    }

    @Override
    public Leave updateLeave(Leave leave) {
        try{
            Optional<LeaveEntity> byId = leaveRepository.findById(leave.getId());
            if(byId.isEmpty()) throw new LeaveException("Leave is not found");
            LeaveEntity leaveEntity = byId.get();
            leaveEntity.setDate(leave.getDate());
            leaveEntity.setReason(leave.getReason());
            leaveEntity.setDayCount(leave.getDayCount());
            leaveEntity.setLeaveType(leave.getLeaveType());
            if(leave.getApprovedBy()!=null){
                Optional<UserEntity> optionalUser = userRepository.findById(leave.getApprovedBy().getId());
                if(optionalUser.isEmpty()) throw new LeaveException("User is not found");
                leaveEntity.setApprovedBy(optionalUser.get());
            }
            LeaveEntity saved = leaveRepository.save(leaveEntity);
            if(saved.getApprovedBy()!=null){
                emailService.sendSimpleMessage(saved.getEmployee().getUser().getEmail(),
                        "Your leave has been approved.",
                        String.format("""
                                The leave you applied for has been approved.
                                Date: %s
                                Day count: %s
                                
                                Thank you,
                                %s
                                """,saved.getDate(),saved.getDayCount()==1?"1":"1/2",saved.getEmployee().getCompany().getName())
                        );
            }
            return mapper.convertValue(saved, Leave.class);
        }
        catch(LeaveException e){
            throw e;
        }
        catch(Exception e){
            throw new LeaveException("Leave is not updated!");
        }
    }

    @Override
    public void deleteLeave(Long id) {
        try{
            Optional<LeaveEntity> byId = leaveRepository.findById(id);
            if(byId.isEmpty()) throw new LeaveException("Leave is not found");
            leaveRepository.deleteById(id);
        }
        catch(LeaveException e){
            throw e;
        }
        catch(Exception e){
            throw new LeaveException("Leave is not deleted!");
        }
    }

    @Override
    public List<Leave> getAllLeavesByUser(Long userId, int limit, int offset) {
        try{
            List<LeaveEntity> allLeavesByEmployee = leaveNativeRepository.getAllLeavesByUser(userId,limit,offset);
            if(allLeavesByEmployee.isEmpty()) throw new LeaveException("Leaves not found");
            List<Leave> leaveList = new ArrayList<>();
            allLeavesByEmployee.forEach(
                    leaveEntity->{
                        User user = mapper.convertValue(leaveEntity.getApprovedBy(), User.class);
                        Leave leave = mapper.convertValue(leaveEntity, Leave.class);
                        leave.setApprovedBy(user);
                        leaveList.add(leave);
                    }
            );
            return leaveList;
        }
        catch(LeaveException e){
            throw e;
        }
        catch(Exception e){
            log.info(e.toString());
            throw new LeaveException("Unknown error occurred");
        }
    }

    @Override
    public Map<String, Double> getLeaveCounts(Long companyId) {
        return leaveNativeRepository.getLeaveCounts(companyId);
    }

    @Override
    public Integer getLeaveCountsByUser(Long userId) {
        return leaveNativeRepository.getLeaveCountsByUser(userId);
    }

    @Override
    public Map<String, Double> getLeaveCountsDatesByUser(Long userId) {
        return leaveNativeRepository.getLeaveCountsDatesByUser(userId);
    }

    public Map<String, Double> getLeaveCategoriesCountsByUser(Long userId){
        return leaveNativeRepository.getLeaveCategoriesCountsByUser(userId);
    }

    @Override
    public Map<String, Double> getEmployeeMonthlyLeaveCount(Long employeeId) {
        return leaveNativeRepository.getEmployeeMonthlyLeaveCount(employeeId);
    }
}
