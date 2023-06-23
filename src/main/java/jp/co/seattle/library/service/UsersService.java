package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.rowMapper.UserCountRowMapper;
import jp.co.seattle.library.rowMapper.UserCountRowMapper2;

/**
 * Handles requests for the application home page.
 */
@Controller
//APIの入り口 APIとは、他のソフトウェアが外部から自分のソフトウェアへアクセスし利用できるようにしたもの
//ソフトウェアコンポーネントが互いにやりとりするのに使用するインタフェースの仕様
public class UsersService {
	final static Logger logger = LoggerFactory.getLogger(UsersService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * ユーザー情報を登録する
	 * 
	 * @param userInfo ユーザー情報
	 */
	public void registUser(UserInfo userInfo) {

		// SQL生成
		String sql = "INSERT INTO users (email, password,shelfPassword, reg_date,upd_date) VALUES ('" + userInfo.getEmail() + "','"
				+ userInfo.getPassword() + "','" + userInfo.getShelfPassword() + "',now(),now()" + ")";
		//String sql = "INSERT INTO users (email, password,reg_date,upd_date) VALUES ('abc@co.jp', 'aiueo12345',now(),now())";
		//char型と区別して’シングルクォーテーション

		jdbcTemplate.update(sql);
		//JavaとDatabaseを繋ぐ
	}

	public void resetUser(UserInfo userInfo) {
		String sql = "UPDATE users SET password = ?, upd_date = NOW() WHERE email = '" + userInfo.getEmail() + "'";
		jdbcTemplate.update(sql, userInfo.getPassword());
	}

	/**
	 * ユーザー情報取得
	 * 
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @return ユーザー情報
	 */
	public UserInfo selectUserInfo(String email, String password) {
		try {
			String sql = "SELECT email, password FROM users WHERE email = '" + email + "' AND password = '" + password
					+ "'";
			UserInfo selectedUserInfo = jdbcTemplate.queryForObject(sql, new UserCountRowMapper());
			return selectedUserInfo;
		} catch (Exception e) {
			return null;
		}
	}
	
	public UserInfo selectUserShelf(String email, String shelfPassword) {
		   try {
		    String sql = "SELECT email, shelfpassword FROM users WHERE email = '" + email + "' AND shelfpassword = '" + shelfPassword + "'";
		    UserInfo selectedUserInfo = jdbcTemplate.queryForObject(sql, new UserCountRowMapper2());
		    return selectedUserInfo;
		   } catch (Exception e) {
		    return null;
		   }
		  }
}
