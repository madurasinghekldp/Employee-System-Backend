package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.Leave;
import org.ems.demo.dto.LeaveByUser;
import org.ems.demo.dto.User;
import org.ems.demo.entity.EmployeeEntity;
import org.ems.demo.entity.LeaveEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.exception.LeaveException;
import org.ems.demo.repository.EmployeeRepository;
import org.ems.demo.repository.LeaveNativeRepository;
import org.ems.demo.repository.LeaveRepository;
import org.ems.demo.repository.UserRepository;
import org.ems.demo.service.EmailService;
import org.ems.demo.service.LeaveService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public Leave createLeave(LeaveByUser leave) {
        try{
            Optional<UserEntity> byId = userRepository.findById(leave.getUser().getId());
            if(byId.isEmpty()){
                throw new LeaveException("User is not found!");
            }
            Optional<EmployeeEntity> employeeEntity = employeeRepository.findByUser(byId.get());
            if(employeeEntity.isEmpty()){
                throw new LeaveException("Employee is not found!");
            }
            LeaveEntity leaveEntity = mapper.convertValue(leave,LeaveEntity.class);
            leaveEntity.setEmployee(employeeEntity.get());
            return mapper.convertValue(leaveRepository.save(leaveEntity), Leave.class);
        }
        catch(LeaveException e){
            throw e;
        }
        catch(Exception e){
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
            leaveEntity.setEndDate(leave.getEndDate());
            leaveEntity.setStartDate(leave.getStartDate());
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
                                Start Date: %s
                                End Date: %s
                                
                                Thank you,
                                %s
                                """,saved.getStartDate(),saved.getEndDate(),saved.getEmployee().getCompany().getName())
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
    public List<Leave> getAllLeavesByUser(Integer userId, int limit, int offset) {
        try{
            List<LeaveEntity> allLeavesByEmployee = leaveNativeRepository.getAllLeavesByEmployeeByUser(userId,limit,offset);
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
}
