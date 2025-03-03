package org.ems.demo.repository;

import org.ems.demo.entity.LeaveEntity;

import java.util.List;

public interface LeaveNativeRepository {
    List<LeaveEntity> getAllLeavesByEmployee(Long employeeId,int limit,int offset);

    List<LeaveEntity> getAllLeavesByEmployeeByUser(Integer userId, int limit, int offset);
}
