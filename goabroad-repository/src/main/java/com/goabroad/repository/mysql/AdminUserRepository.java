package com.goabroad.repository.mysql;

import com.goabroad.model.entity.AdminUser;
import com.goabroad.model.enums.AdminRole;
import com.goabroad.model.enums.AdminStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 管理员Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    
    /**
     * 根据用户名查询管理员
     * 
     * @param username 用户名
     * @return 管理员对象
     */
    Optional<AdminUser> findByUsername(String username);
    
    /**
     * 根据邮箱查询管理员
     * 
     * @param email 邮箱
     * @return 管理员对象
     */
    Optional<AdminUser> findByEmail(String email);
    
    /**
     * 检查用户名是否存在
     * 
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);
    
    /**
     * 检查邮箱是否存在
     * 
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);
    
    /**
     * 根据用户名或邮箱查询管理员（用于登录）
     * 
     * @param username 用户名
     * @param email 邮箱
     * @return 管理员对象
     */
    @Query("SELECT a FROM AdminUser a WHERE a.username = :username OR a.email = :email")
    Optional<AdminUser> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);
    
    /**
     * 根据状态查询管理员列表
     * 
     * @param status 管理员状态
     * @param pageable 分页参数
     * @return 管理员列表
     */
    Page<AdminUser> findByStatus(AdminStatus status, Pageable pageable);
    
    /**
     * 根据角色查询管理员列表
     * 
     * @param role 角色
     * @param pageable 分页参数
     * @return 管理员列表
     */
    Page<AdminUser> findByRole(AdminRole role, Pageable pageable);
    
    /**
     * 根据关键词搜索管理员（用户名或昵称）
     * 
     * @param keyword 关键词
     * @param pageable 分页参数
     * @return 管理员列表
     */
    @Query("SELECT a FROM AdminUser a WHERE a.username LIKE %:keyword% OR a.nickname LIKE %:keyword%")
    Page<AdminUser> searchAdmins(@Param("keyword") String keyword, Pageable pageable);
    
    /**
     * 统计指定状态的管理员数量
     * 
     * @param status 管理员状态
     * @return 管理员数量
     */
    long countByStatus(AdminStatus status);
    
    /**
     * 统计指定角色的管理员数量
     * 
     * @param role 角色
     * @return 管理员数量
     */
    long countByRole(AdminRole role);
}

