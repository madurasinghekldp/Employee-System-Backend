package org.ems.demo.service;

import org.ems.demo.dto.Leave;
import org.ems.demo.dto.LeaveByUser;
import org.ems.demo.entity.LeaveEntity;

import java.util.List;
import java.util.Map;

public interface LeaveService {
    Leave createLeave(LeaveByUser leave,Long userId);

    List<Leave> getAllLeaves(Long employeeId,int limit,int offset);

    Leave updateLeave(Leave leave);

    void deleteLeave(Long id);

    List<Leave> getAllLeavesByUser(Long userId, int limit, int offset);
    Map<String, Double> getLeaveCounts(Long companyId);

    Integer getLeaveCountsByUser(Long userId);

    Map<String,Double> getLeaveCountsDatesByUser(Long userId);
    Map<String, Double> getLeaveCategoriesCountsByUser(Long userId);

    Map<String,Double> getEmployeeMonthlyLeaveCount(Long employeeId);
}
