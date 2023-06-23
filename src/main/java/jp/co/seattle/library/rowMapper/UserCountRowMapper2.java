package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.UserInfo;
 
 @Configuration
 public class UserCountRowMapper2 implements RowMapper<UserInfo> {

  @Override
  public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
   // Query結果（ResultSet rs）を、オブジェクトに格納する実装
   UserInfo userInfo = new UserInfo();
   userInfo.setEmail(rs.getString("email"));
   userInfo.setShelfPassword(rs.getString("shelfPassword"));
   return userInfo;
  }

 }
