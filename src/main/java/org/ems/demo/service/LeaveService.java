package org.ems.demo.service;

import org.ems.demo.dto.Leave;
import org.ems.demo.dto.LeaveByUser;
import org.ems.demo.entity.LeaveEntity;

import java.util.List;

public interface LeaveService {
    Leave createLeave(LeaveByUser leave);

    List<Leave> getAllLeaves(Long employeeId,int limit,int offset);

    Leave updateLeave(Leave leave);

    void deleteLeave(Long id);

    List<Leave> getAllLeavesByUser(Integer userId, int limit, int offset);
}
