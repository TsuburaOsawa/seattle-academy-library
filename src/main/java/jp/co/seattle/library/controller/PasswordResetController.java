package jp.co.seattle.library.controller;

import java.util.Locale;

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

@Controller /** APIの入り口 */
public class PasswordResetController {
	final static Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/passwordReset", method = RequestMethod.GET)
	public String passwordReset(Model model) {
		return "passwordReset";
	}

	@RequestMapping(value = "/resetAccount", method = RequestMethod.POST)
	public String createAccount(Locale locale, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("passwordForCheck") String passwordForCheck,
			Model model) {
		// デバッグ用ログ
		logger.info("Welcome resetAccount! The client locale is {}.", locale);
		//logger=ログを出力する（作ってる）左のコンソールに出力される

		// バリデーションチェック、パスワード一致チェック（タスク１）

		if (password.length() >= 8 && password.matches("[0-9a-zA-Z]+")) {
			if (password.equals(passwordForCheck)) {
				// パラメータで受け取ったアカウント情報をDtoに格納する。
				UserInfo userInfo = new UserInfo();
				userInfo.setEmail(email);
				userInfo.setPassword(password);
				usersService.registUser(userInfo);
				return "redirect:/login";
			} else {
				model.addAttribute("errorMessage", "パスワードと確認パスワードが一致していません。");
				return "resetAccount";
			}
		} else {
			model.addAttribute("errorMessage", "パスワードは８桁以上の半角英数字で設定してください。");
			return "resetAccount";
		}

		usersService.updateAcount(usersInfo);

		return "login";
	}
}
