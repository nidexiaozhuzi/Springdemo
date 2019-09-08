package com.smart.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import com.smart.domain.User;

@Repository //通过Spring注解定义一个Data Access Object
public class UserDao {
    private JdbcTemplate  jdbcTemplate;

    @Autowired //自动注入JdbcTemplate的Bean
    public  void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getMatchCount(String userName, String password){
        String sqlStr = "SELECT COUNT(*) FROM t_user " + " WHERE user_name = ? AND password = ? ";

            return jdbcTemplate.queryForObject(sqlStr, new Object []{userName,password},Integer.class);

    }

    //根据用户名查询用户的SQL语句
    private final static String MATCH_COUNT_SQL = "SELECT COUNT(*) FROM " + "" +
            " t_user WHERE user_name = ? AND password = ?";
    //根据用户id更新用户登录日志
    private final static String UPDATE_LOGIN_INFO_SQL = "UPDATE t_user SET " +
            " last_visit = ?, last_ip = ?, credits = ? WHERE user_id = ? ";

    public User findUserByUserName(final String userName){

        final  User user = new User();
        jdbcTemplate.query(MATCH_COUNT_SQL, new Object[]{userName},
                //匿名类方式实现的回调函数

                new RowCallbackHandler() {
                @Override
                public void processRow(ResultSet rs) throws SQLException {
                  user.setUserId(rs.getInt("user_id"));
                  user.setUserName(userName);
                  user.setCredits(rs.getInt("credits"));
            }
        });

        return user;

    }


    public void updateLoginInfo(User user){
        jdbcTemplate.update(UPDATE_LOGIN_INFO_SQL,new Object[] {user.getLastVisit(),
                user.getLastIp(),user.getCredits(),user.getUserId()});
    }


}
