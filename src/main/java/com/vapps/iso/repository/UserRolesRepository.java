package com.vapps.iso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vapps.iso.entity.UserRole;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRole, String> {
	@Query("select userrole.role from UserRole userrole, User user where user.userName=?1 and userrole.userId=user.id")
	List<String> findRoleByUserName(String username);
}
