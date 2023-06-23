package jp.co.seattle.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.BooksService;

@Controller
public class AdultController {
	final static Logger logger = LoggerFactory.getLogger(AdultController.class);

	@Autowired
	private BooksService booksService;
	
	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 */

	
	@RequestMapping(value = "/moveAdult", method = RequestMethod.GET)
	public String transitionAdult(Model model) {
		List<BookInfo> adultPage = booksService.getAdultBookList();
		
		if (adultPage.size() > 0) {
			model.addAttribute("booklist", adultPage);
			return "adult";
		} else {
			model.addAttribute("resultMessage", "結果なし");
			return "adult";
		}
	}
}
