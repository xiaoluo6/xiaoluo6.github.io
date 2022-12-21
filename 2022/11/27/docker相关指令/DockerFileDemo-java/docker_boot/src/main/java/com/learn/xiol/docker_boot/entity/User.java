package com.learn.xiol.docker_boot.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>用户实体类</p>
 * @author luowei
 * @date 2022-12-18 22:24
 * @desc 用户实体类
 */
@Data
@Table(name = "t_user")
public class User {

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别 0:女  1:男
     */
    private Byte sex;

    /**
     * 逻辑删除标志， 默认0不删除，1删除
     */
    private Byte deleted;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
