package com.learn.xiol.docker_boot.service;

import com.learn.xiol.docker_boot.entity.User;
import com.learn.xiol.docker_boot.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>用户服务类</p>
 * @author luowei
 * @date 2022-12-18 23:02
 * @desc 用户服务类
 */
@Service
@Slf4j
public class UserService {

    public static final String CACHE_KEY_USER = "user:";

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 添加用户
     * @param user 用户信息实体
     */
    public void addUser(User user){
        // 1. 先插入mysql并获取插入结果
        int i = userMapper.insertSelective(user);
        // 2. 插入成功
        if(i > 0){
            // 2.1 从数据库获取刚插入的对象
            user = userMapper.selectByPrimaryKey(user.getId());
            // 2.2 将获取到数据存储到redis一份，完成新增功能的数据一致性
            String key = CACHE_KEY_USER + user.getId();
            redisTemplate.opsForValue().set(key, user);
        }
    }

    /**
     * 通过用户id查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    public User findUserById(Integer id){
        // 1. 初始化用户信息
        User user = null;
        // 2. 获取缓存key
        String key = CACHE_KEY_USER + id;
        // 3. 先从redis中查询，如果有结果直接返回结果，如果没有再去查询mysql
        user = (User) redisTemplate.opsForValue().get(key);
        // 4. 如果没有，查询数据库
        if(user == null){
            // 4.1 从数据库获取用户信息
            user = userMapper.selectByPrimaryKey(id);
            // 如果没有，自己返回null
            if(user == null){
                return user;
            }else{
                // 如果数据库有，更新到redis中
                redisTemplate.opsForValue().set(key, user);
            }
        }
        return user;
    }

    /**
     * 通过id删除一个用户
     * @param id
     */
    public void deleteUser(Integer id) {
        User user = new User();
        user.setId(id);
        // 数据库删除用户信息
        int delete = userMapper.delete(user);
        // 如果数据库删除成功后，redis也对应删除
        if(delete > 0){
            redisTemplate.delete(CACHE_KEY_USER + id);
        }

    }

    public void updateUser(User user) {
         int i = userMapper.updateByPrimaryKeySelective(user);
         if(i > 0){
             // 2.1 从数据库获取刚插入的对象
             user = userMapper.selectByPrimaryKey(user.getId());
             // 2.2 将获取到数据存储到redis一份，完成新增功能的数据一致性
             String key = CACHE_KEY_USER + user.getId();
             redisTemplate.opsForValue().set(key, user);
         }
    }
}
