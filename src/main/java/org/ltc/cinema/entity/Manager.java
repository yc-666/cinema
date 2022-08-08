package org.ltc.cinema.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mbz1113zzz
 * @version 1.0
 * @date 2022/8/7 18:01
 */
@Data
@ApiModel(value = "Manager",description = "管理员模块")
public class Manager implements Serializable {
    @ApiModelProperty(value = "管理员ID",required = true,example = "")
    private String managerId;

    @ApiModelProperty(value = "管理员密码",required = false,example = "")
    private String password;

    @ApiModelProperty(value = "姓名",required = false,example = "")
    private String name;

    @ApiModelProperty(value = "角色",required = false,example = "")
    private String role;

    @ApiModelProperty(value = "时间",required = false,example = "")
    private String time;

    private static final long serialVersionUID = 1L;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}