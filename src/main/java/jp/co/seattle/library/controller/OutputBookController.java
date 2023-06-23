package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

@Controller
public class OutputBookController {
	final static Logger logger = LoggerFactory.getLogger(OutputBookController.class);
	
	@Autowired
	private BooksService booksService;
	
	/**
	 * 対象書籍をファイル出力する
	 * 
	 * @param locale 
	 * @param bookID
	 * @param model
	 * @param redirectAttributes 
	 * @return 遷移先画面名
	 */
	@Transactional
	@RequestMapping(value = "/outputBook", method = RequestMethod.POST)
	public String outputBook(Locale locale, @RequestParam("bookId") int bookId, RedirectAttributes redirectAttributes, Model model) {
		logger.info("Welcome output! Thw client locate is {}.", locale);
		
		//書籍情報
		BookDetailsInfo bookInfo = booksService.getBookInfo(bookId);
		
		//API呼び出し
		String responseMessage = booksService.callAPI(bookInfo);
		
		System.out.println("12");
		
		if (responseMessage.equals("書籍出力に成功しました")) {
			redirectAttributes.addFlashAttribute("successMessage", responseMessage);
		} else {
			redirectAttributes.addFlashAttribute("errorMessage",responseMessage);
		}
		return "redirect:/editBook?bookId=" + bookId;
	}
	
}
