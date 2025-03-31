package org.ems.demo.repository;

import org.ems.demo.entity.LeaveEntity;

import java.util.List;
import java.util.Map;

public interface LeaveNativeRepository {
    List<LeaveEntity> getAllLeavesByEmployee(Long employeeId,int limit,int offset);

    List<LeaveEntity> getAllLeavesByUser(Integer userId, int limit, int offset);

    Map<String, Double> getLeaveCounts(Long companyId);

    Integer getLeaveCountsByUser(Integer userId);

    Map<String, Double> getLeaveCountsDatesByUser(Integer userId);
}
