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
@ApiModel(value = "Member",description = "会员模型")
public class Member implements Serializable {
    /**
     * 与数据库的memberid映射
     */
    @ApiModelProperty(value = "会员ID",required = true,example = "")
    private String memberId;

    @ApiModelProperty(value = "密码",required = true,example = "")
    private String password;

    @ApiModelProperty(value = "姓名",required = true,example = "")
    private String name;

    @ApiModelProperty(value = "性别",required = true,example = "")
    private String sex;

    @ApiModelProperty(value = "生日",required = true,example = "")
    private String birthday;

    private static final long serialVersionUID = 1L;

    public String getmemberId() {
        return memberId;
    }

    public void setmemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}