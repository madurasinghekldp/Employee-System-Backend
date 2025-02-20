package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.Leave;
import org.ems.demo.entity.EmployeeEntity;
import org.ems.demo.entity.LeaveEntity;
import org.ems.demo.exception.LeaveException;
import org.ems.demo.repository.EmployeeRepository;
import org.ems.demo.repository.LeaveNativeRepository;
import org.ems.demo.repository.LeaveRepository;
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
    private final ObjectMapper mapper;

    @Override
    public Leave createLeave(Leave leave) {
        try{
            Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(leave.getEmployee().getId());
            if(employeeEntity.isEmpty()){
                throw new LeaveException("Employee not found!");
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
                    leave->{
                        leaveList.add(mapper.convertValue(leave, Leave.class));
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
            LeaveEntity saved = leaveRepository.save(leaveEntity);
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
}
