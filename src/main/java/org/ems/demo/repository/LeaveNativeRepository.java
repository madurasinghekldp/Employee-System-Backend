package org.ems.demo.repository;

import org.ems.demo.entity.LeaveEntity;

import java.util.List;
import java.util.Map;

public interface LeaveNativeRepository {
    List<LeaveEntity> getAllLeavesByEmployee(Long employeeId,int limit,int offset);

    List<LeaveEntity> getAllLeavesByUser(Long userId, int limit, int offset);

    Map<String, Double> getLeaveCounts(Long companyId);

    Integer getLeaveCountsByUser(Long userId);

    Map<String, Double> getLeaveCountsDatesByUser(Long userId);

    Map<String, Double> getLeaveCategoriesCountsByUser(Long userId);

    Double getLeaveCountByUserAndType(Long userId, String leaveType);

    Map<String, Double> getEmployeeMonthlyLeaveCount(Long employeeId);
}
