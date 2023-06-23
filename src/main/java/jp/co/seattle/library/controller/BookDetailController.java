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

import jp.co.seattle.library.service.BooksService;

@Controller
public class BookDetailController {
	final static Logger logger = LoggerFactory.getLogger(EditBookController.class);

	@Autowired
	private BooksService booksService;
	
	@RequestMapping(value = "/detailBook", method = RequestMethod.GET)
	public String transition(Locale locale, @RequestParam("bookId")int bookId, Model model) {
		logger.info("Welcome detail.java! The client locale is {}.", locale);
		
		model.addAttribute("bookInfo", booksService.getBookDetail(bookId));
		System.out.println("12");
		return "bookDetail";
	}
}