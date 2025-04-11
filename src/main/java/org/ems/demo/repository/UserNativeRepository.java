package org.ems.demo.repository;


import org.ems.demo.dto.User;
import org.ems.demo.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserNativeRepository {
    UserEntity getUserByEmail(String email);

    List<UserEntity> getCompanyUsers(Long companyId);
}
