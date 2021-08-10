package com.jcsa.eqmd.config;

import com.jcsa.eqmd.bean.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户密码集合
 */
@Configuration
@ConfigurationProperties(prefix = "data")
@PropertySource("classpath:user.properties")
public class UserConfig {

    List<User> userList = new ArrayList<>();

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Map<String, String> getMap(){
        return userList.stream().collect(Collectors.toMap(User::getUserName, User::getPassword));
    }
}
