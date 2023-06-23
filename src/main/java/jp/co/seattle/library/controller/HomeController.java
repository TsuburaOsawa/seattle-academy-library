package jp.co.seattle.library.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class HomeController {
	final static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String transitionHome(Model model) {
		//書籍の一覧情報を取得（タスク３）
		List<BookInfo> homeBook = booksService.getBookList();

		if (homeBook.size() > 0) {
			model.addAttribute("booklist", homeBook);
			return "home";
		} else {
			model.addAttribute("resultMessage", "結果なし");
			return "home";
		}
	}

	//検索
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchBook(Locale locale, @RequestParam("search") String title, Model model) {

		//部分一致した書籍があるか
		List<BookInfo> selected = booksService.searchBook(title);
		if (selected.size() > 0) {
			model.addAttribute("booklist", selected);
			return "home";
		} else {
			model.addAttribute("resultMessage", "結果なし");
			return "home";
		}
	}

	//お気に入り登録・解除
	@RequestMapping(value = "/favorite", method = RequestMethod.GET)
	public String favoriteBook(Locale locale, RedirectAttributes redirectAttributes, @RequestParam("value") String status, @RequestParam("bookId") int id,
			Model model) {
		System.out.println(status + " " + id);
		booksService.favoriteBook(status, id);
		return "redirect:/home";
	}

	//お気に入りのみ表示
	@RequestMapping(value = "/favoriteBooks", method = RequestMethod.GET)
	public String favoriteBooks(Model model) {
		List<BookInfo> favoriteBookList = booksService.getFavoriteBook();
		model.addAttribute("booklist", favoriteBookList);
		return "home";
	}

	//ジャンル別表示
	@RequestMapping(value = "/genre", method = RequestMethod.GET)
	public String selectBox(Locale locale, @RequestParam("select1") String genre, Model model) {
		System.out.println(genre);
		List<BookInfo> selectBoxList = booksService.selectBox(genre);
		model.addAttribute("booklist", selectBoxList);
		System.out.println(selectBoxList);
		return "home";
	}

	//成人向けページへ
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public String moved(Model model) {
		return "redirect:/moveAdult";
	}

	//パスワード付き本棚ログイン画面へ
	@RequestMapping(value = "/loginShelf", method = RequestMethod.GET)
	public String first(Model model) {
		return "loginBookShelf";
	}

	//パスワード付き本棚へ本を移動
	@RequestMapping(value = "/addShelf", method = RequestMethod.POST)
	public String addShelfBook(@RequestParam("bookId") int[] arr, Model model) {
		for (int i : arr) {
			booksService.registShelfBook(i);
		}
		return "redirect:/bookShelf";
	}
	

}
