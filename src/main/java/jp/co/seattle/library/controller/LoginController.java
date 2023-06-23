package jp.co.seattle.library.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.service.UsersService;

/**
 * ログインコントローラー
 */
@Controller /** APIの入り口 */
public class LoginController {
	final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String first(Model model) {
		return "login"; // jspファイル名
	}

	/**
	 * ログイン処理
	 *
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @param model
	 * @return ホーム画面に遷移
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST) //jspのform内の情報がここに飛ぶ
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
		//("email"),("password")はimputタグのnameと関連づけている。それを引数として使う。
		
		// メアドとパスワードに一致するユーザー取得
		UserInfo selectedUserInfo = usersService.selectUserInfo(email, password);

		// ユーザーが存在すればログイン、存在しなければエラー(タスク２)
		if (Objects.isNull(selectedUserInfo)) {//←staticメソッドだからインスタンス化が必要ない。引数がObjectだからObjectUtil。isEmptyでnull判断も可能。
			model.addAttribute("errorMessage", "メールアドレスとパスワードが一致しません");
			return "login";
		} else {
			return "redirect:/home";//redorect:homecontrollerのhomeのメソッドの処理を行ってから画面遷移
		}
	}
}