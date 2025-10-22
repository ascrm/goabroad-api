package com.goabroad.service.admin;

import com.goabroad.model.dto.AdminDTO;
import com.goabroad.model.dto.response.AdminUserResponse;
import com.goabroad.model.enums.AdminRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 管理员服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface AdminService {
    
    /**
     * 创建管理员
     * 
     * @param dto 管理员DTO
     * @return 管理员信息
     */
    AdminUserResponse createAdmin(AdminDTO dto);
    
    /**
     * 更新管理员信息
     * 
     * @param adminId 管理员ID
     * @param dto 管理员DTO
     * @return 更新后的管理员信息
     */
    AdminUserResponse updateAdmin(Long adminId, AdminDTO dto);
    
    /**
     * 删除管理员
     * 
     * @param adminId 管理员ID
     */
    void deleteAdmin(Long adminId);
    
    /**
     * 根据ID获取管理员
     * 
     * @param adminId 管理员ID
     * @return 管理员信息
     */
    AdminUserResponse getAdminById(Long adminId);
    
    /**
     * 根据用户名获取管理员
     * 
     * @param username 用户名
     * @return 管理员信息
     */
    AdminUserResponse getAdminByUsername(String username);
    
    /**
     * 获取所有管理员
     * 
     * @param pageable 分页参数
     * @return 管理员列表
     */
    Page<AdminUserResponse> getAllAdmins(Pageable pageable);
    
    /**
     * 根据角色查询管理员
     * 
     * @param role 角色
     * @param pageable 分页参数
     * @return 管理员列表
     */
    Page<AdminUserResponse> getAdminsByRole(AdminRole role, Pageable pageable);
    
    /**
     * 搜索管理员
     * 
     * @param keyword 关键词
     * @param pageable 分页参数
     * @return 管理员列表
     */
    Page<AdminUserResponse> searchAdmins(String keyword, Pageable pageable);
    
    /**
     * 修改管理员密码
     * 
     * @param adminId 管理员ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Long adminId, String oldPassword, String newPassword);
    
    /**
     * 禁用管理员
     * 
     * @param adminId 管理员ID
     */
    void disableAdmin(Long adminId);
    
    /**
     * 启用管理员
     * 
     * @param adminId 管理员ID
     */
    void enableAdmin(Long adminId);
}

