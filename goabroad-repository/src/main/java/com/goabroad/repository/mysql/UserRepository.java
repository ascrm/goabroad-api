package com.goabroad.repository.mysql;

import com.goabroad.model.entity.User;
import com.goabroad.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户对象
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 根据邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户对象
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 根据手机号查询用户
     * 
     * @param phone 手机号
     * @return 用户对象
     */
    Optional<User> findByPhone(String phone);
    
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
     * 检查手机号是否存在
     * 
     * @param phone 手机号
     * @return 是否存在
     */
    boolean existsByPhone(String phone);
    
    /**
     * 根据用户名或邮箱查询用户（用于登录）
     * 
     * @param username 用户名
     * @param email 邮箱
     * @return 用户对象
     */
    @Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :email")
    Optional<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);
    
    /**
     * 根据手机号或邮箱查询用户（用于登录）
     * 
     * @param phone 手机号
     * @param email 邮箱
     * @return 用户对象
     */
    @Query("SELECT u FROM User u WHERE u.phone = :phone OR u.email = :email")
    Optional<User> findByPhoneOrEmail(@Param("phone") String phone, @Param("email") String email);
    
    /**
     * 根据状态查询用户列表
     * 
     * @param status 用户状态
     * @param pageable 分页参数
     * @return 用户列表
     */
    Page<User> findByStatus(UserStatus status, Pageable pageable);
    
    /**
     * 根据关键词搜索用户（用户名或昵称）
     * 
     * @param keyword 关键词
     * @param pageable 分页参数
     * @return 用户列表
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% OR u.nickname LIKE %:keyword%")
    Page<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);
    
    /**
     * 统计指定状态的用户数量
     * 
     * @param status 用户状态
     * @return 用户数量
     */
    long countByStatus(UserStatus status);
}

