package jp.co.seattle.library.service;

import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 * booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 書籍名の昇順で書籍情報を取得するようにSQLを修正（タスク３）
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books where genre != '成人向け' and (shelf='delete' or shelf is null) ORDER BY title ASC;",
				new BookInfoRowMapper());

		return getedBookList;
	}
	
	public List<BookInfo> getAdultBookList() {

		// TODO 書籍名の昇順で書籍情報を取得するようにSQLを修正（タスク３）
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books where genre = '成人向け' ORDER BY title ASC;",
				new BookInfoRowMapper());

		return getedBookList;
	}
	

	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {
		String sql = "SELECT id, title, author, publisher, publish_date, isbn, description, thumbnail_url, thumbnail_name, favorite, genre FROM books WHERE books.id = ? ORDER BY title ASC;";

		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper(), bookId);

		return bookDetailsInfo;
	}
	
	public BookDetailsInfo getBookDetail(int bookId) {
		String sql = "SELECT id, title, author, publisher, publish_date, isbn, description, thumbnail_url, thumbnail_name, favorite, genre FROM books WHERE books.id = ?;";

		BookDetailsInfo bookDetails = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper(), bookId);

		return bookDetails;
	}

	/**
	 * 検索した書籍を取得する
	 */
	public List<BookInfo> searchBook(String word) {
		List<BookInfo> searchBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE title LIKE concat('%', ? , '%') and genre != '成人向け'",
				new BookInfoRowMapper(), word);
		//BookInfoRowMapper.mapRow());
		
		return searchBookList;
		//return searchBookInfo;
	}
	
	/*IDに基づいてお気に入り登録・解除
	 * お気に入りで保存したIDとツイートテーブルをリンクして一覧を取得する機能を付ける
	 */
	public void favoriteBook(String status ,int id) {
		String sql = "UPDATE books SET favorite = ? WHERE id = ? ";
		jdbcTemplate.update(sql, status, id);
	}
		
	
	//お気に入り書籍のみ表示
	public List<BookInfo> getFavoriteBook() {
		List<BookInfo> favoriteBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE favorite='1' and genre != '成人向け'",
				new BookInfoRowMapper());
		//BookInfoRowMapper.mapRow());
		return favoriteBookList;
	}
	
	//ジャンル別表示
	public List<BookInfo> selectBox(String genre) {
		List<BookInfo> fselectBoxList = jdbcTemplate.query(
				"SELECT * FROM books WHERE genre=? and genre != '成人向け'",
				new BookInfoRowMapper(), genre);
		//BookInfoRowMapper.mapRow());
		
		return fselectBoxList;
	}

	/**
	 * 書籍を登録する
	 *
	 * @param bookInfo 書籍情報
	 * @return bookId 書籍ID
	 */
	public int registBook(BookDetailsInfo bookInfo) {
		// TODO 取得した書籍情報を登録し、その書籍IDを返却するようにSQLを修正（タスク４）
		String sql = "INSERT INTO books(title, author, publisher, publish_date, thumbnail_name, thumbnail_url, isbn, description, reg_date, upd_date, favorite, genre) VALUES(?,?,?,?,?,?,?,?,NOW(), NOW(),?,?) RETURNING id";

		int bookId = jdbcTemplate.queryForObject(sql, int.class, bookInfo.getTitle(), bookInfo.getAuthor(),
				bookInfo.getPublisher(), bookInfo.getPublishDate(), bookInfo.getThumbnailName(),
				bookInfo.getThumbnailUrl(), bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getFavorite(), bookInfo.getGenre());
		return bookId;
	}

	/**
	 * 書籍を削除する
	 * 
	 * @param bookId 書籍ID
	 */
	public void deleteBook(int bookId) {
		// TODO 対象の書籍を削除するようにSQLを修正（タスク6）
		String sql = "DELETE FROM books WHERE id = ?";
		jdbcTemplate.update(sql, bookId);
	}
	
	
	//パスワード付き本棚
	public List<BookInfo> getShelfBook() {
	    List<BookInfo> getedBookList = jdbcTemplate.query(
	      "SELECT * FROM books WHERE shelf='register' ORDER BY title asc;",
	      new BookInfoRowMapper());
	    return getedBookList;
	    }

	public void registShelfBook(int bookId) {
	    String sql = "UPDATE books SET shelf='register' WHERE books.id = ?;";
	    jdbcTemplate.update(sql, bookId);
	 }
	 
	 public void getDeleteShelfBook(int bookId) {
	    String sql = "UPDATE books SET shelf='delete' WHERE books.id = ?;";
	    jdbcTemplate.update(sql, bookId);
	 }
	 
	/**
	 * 書籍情報を更新する
	 * 
	 * @param bookInfo
	 */
	public void updateBook(BookDetailsInfo bookInfo) {
		String sql;
		if (bookInfo.getThumbnailUrl() == null) {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）
			sql = "UPDATE books SET title = ?, author = ?, publisher = ?, publish_date = ?,  isbn = ?, description = ?, upd_date = NOW(), favorite = ?, genre = ? WHERE id = ? ";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getFavorite(), bookInfo.getGenre(), bookInfo.getBookId());
		} else {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）
			sql = "UPDATE books SET title = ?, author = ?, publisher = ?, publish_date = ?, thumbnail_name = ?, thumbnail_url = ?, isbn = ?, description = ?, upd_date = NOW(), favorite = ?, genre = ? WHERE id = ? ";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getThumbnailName(), bookInfo.getThumbnailUrl(),
					bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getFavorite(), bookInfo.getGenre(), bookInfo.getBookId());
		}
	}
	
	@Autowired
	 private RestTemplate restTemplate;
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * API呼び出し
	 * @param bookInfo 書籍情報
	 * @return メッセージ
	 */
	
	public String callAPI(BookDetailsInfo bookInfo) {
		
		//プロパティファイルからAPIのURLを取得
		ResourceBundle rb = ResourceBundle.getBundle("output");
		String url = rb.getString("url");
		
		try {
			//API呼び出し
			String responseMessage = restTemplate.postForObject(url, bookInfo, String.class);
			return responseMessage;
		} catch (Exception e) {
			//Todo 自動生成された　catch ブロック
			e.printStackTrace();
			return "API接続に失敗しました";
		}
	}
}
