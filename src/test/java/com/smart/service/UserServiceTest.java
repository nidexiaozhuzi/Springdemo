package com.smart.service;

import com.smart.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import org.testng.Assert;

@ContextConfiguration("classpath*:/smart-context.xml")
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Test
    public void  hasMatchUser(){
        boolean b1 = userService.hasMatchUser("admin","123456");
       // boolean b2 = userService.hasMatchUser("admin","1111");
        Assert.assertTrue(b1);
      //  Assert.assertTrue(b2);

    }

    @Test
    public void findUserByUserName(){
        User user = userService.findUserByUserName("admin");
        Assert.assertEquals(user.getUserName(),"admin");
        //Assert.assertEquals(user.getPassword(),"123456");
    }

}
