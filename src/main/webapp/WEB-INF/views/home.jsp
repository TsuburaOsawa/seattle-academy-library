<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<!-- <script type="text/javascript" src="/resources/js/favoriteJavaScript.js"></script> -->
</head>
<body class="wrapper">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    //モーダル
        $(function() {
            $("#adultButton").click(function() {
                $("#ageConfirmationModal").show();
            });

            $("#disapear").click(function() {
            	$("#ageConfirmationModal").fadeOut();
            });
            
            $("#confirmYes").click(function() {
                window.location.href = "http://localhost:8080/SeattleLibrary/move";
            });

            $("#confirmNo").click(function() {
                $("#ageConfirmationModal").fadeOut();
            });
        });

    </script>
    <script type="text/javascript">
    //お気に入り登録
    function buttonClick(status,id) {    
        if(status==0){
            update(status,id);
            alert('登録解除！');
        } else {
        	update(status,id);
        	alert('登録完了！');
        }
     }

    function update(status,id){
        console.log(status);
    	var btn = new XMLHttpRequest();
        btn.open("GET", "http://localhost:8080/SeattleLibrary/favorite?value="+status+"&bookId="+id+"", false);
        btn.send();
        console.log("2");
        window.location.reload();
        }

	//My本棚に本移行
    function shelf(){
        console.log("10");
        const arr = [];
        let aaa=0;
        const chk1 = document.getElementsByName("bookShelf");
           console.log(chk1);
        for (let i = 0; i < chk1.length; i++) {
          if (chk1[i].checked) {
           console.log(chk1[i].value+" "+i);
               arr.push(chk1[i].value); 
          }
        }
             var status = new XMLHttpRequest();
                 status.open('POST',"http://localhost:8080/SeattleLibrary/addShelf?bookId="+arr+"");
                 status.send();     
    }
    </script>
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h1>Home</h1>
        <a href="<%=request.getContextPath()%>/addBook" class="btn_add_book">書籍の追加</a>
        <form action="<%=request.getContextPath()%>/search" method="get">
            <div class="flex">
                <input type="text" id="sbox" name="search" placeholder="キーワードを入力" size="20" /> <input type="image" id="sbtn" src="https://cdn.pixabay.com/photo/2014/11/30/14/11/cat-551554_1280.jpg" width="30" height="30" alt="検索" value="検索する" />
            </div>
        </form>
        <!-- ループ -->
        <section class="loop">
            <div class="loop__wrap">
                <c:forEach begin="1" end="20" step="2" var="bookInfo" items="${booklist}">
                    <form method="get" class="loop__list list--left" action="detailBook">
                        <a href="javascript:void(0)" class="loop__item" onclick="this.parentNode.submit();"> <img src="${bookInfo.thumbnail}" class="loop__image" alt=""><input type="hidden" name="bookId" value="${bookInfo.bookId}">
                        </a>
                    </form>
                </c:forEach>
                <c:forEach begin="1" end="20" step="2" var="bookInfo" items="${booklist}">
                    <form method="get" class="loop__list list--left" action="detailBook">
                        <a href="javascript:void(0)" class="loop__item" onclick="this.parentNode.submit();"> <img src="${bookInfo.thumbnail}" class="loop__image" alt=""><input type="hidden" name="bookId" value="${bookInfo.bookId}">
                        </a>
                    </form>
                </c:forEach>
            </div>
        </section>
        <a href="<%=request.getContextPath()%>/favoriteBooks" name="favo" class="btn_favo_book">お気に入り</a> <a href="<%=request.getContextPath()%>/loginShelf" method="get" class="shelf_book">My本棚へ</a> <input type="button" form="form1" class="moveshelf" value="本棚に追加" onclick="shelf()">
        <!-- モーダル(成人向けページへ) -->
        <div class="adultMove">
            <button id="adultButton" class="btn_adult_book">成人向けページへ</button>
            <div id="ageConfirmationModal" class="modal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">年齢確認</h5>
                            <button type="button" id="disapear" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p>あなたは18歳以上ですか？</p>
                        </div>
                        <div class="modal-footer">
                            <button id="confirmYes" class="modal-button">はい</button>
                            <button type="button" id="confirmNo" class="modal-button" data-dismiss="modal">いいえ</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- ジャンル -->
        <form action="${pageContext.request.contextPath}/genre" method="get">
            <div class="sebox">
                <label class="selectbox-001" id="selectbox"> <select name="select1">
                        <option value="">ジャンル選択</option>
                        <option value="小説">小説</option>
                        <option value="実用書">実用書</option>
                        <option value="ビジネス書">ビジネス書</option>
                        <option value="参考書">参考書</option>
                        <option value="コミック">コミック</option>
                        <option value="雑誌">雑誌</option>
                        <option value="成人向け">成人向け</option>
                </select>
                </label>
                <button type="submit" id="genre">検索</button>
            </div>
        </form>
        <div class="content_body">
            <c:if test="${!empty resultMessage}">
                <div class="error_msg">${resultMessage}</div>
            </c:if>
            <div>
                <div class="booklist">
                    <c:forEach var="bookInfo" items="${booklist}">
                        <div class="books">
                            <div>
                                <form method="post" id="form1" name="form1" class="shelfCheck" action="addShelf">
                                    <input type="checkbox" name="bookShelf" id="shelfBtn" value="${bookInfo.bookId}">📚
                                </form>
                            </div>
                            <form method="get" class="book_thumnail" action="editBook">
                                <a href="javascript:void(0)" onclick="this.parentNode.submit();"> <c:if test="${empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="resources/img/noImg.png">
                                    </c:if> <c:if test="${!empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="${bookInfo.thumbnail}">
                                    </c:if>
                                </a> <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                            </form>
                            <ul>
                                <li class="book_title">${bookInfo.title}</li>
                                <li class="book_author">(著)${bookInfo.author}</li>
                                <li class="book_publisher">出版社：${bookInfo.publisher}</li>
                                <li class="book_publish_date">出版日：${bookInfo.publishDate}</li>
                                <!-- お気に入り登録と解除 -->
                                <c:if test="${bookInfo.favorite == 0}">
                                    <button name="site${bookInfo.bookId}" id="nofbtn" value="1" onclick="buttonClick(1, ${bookInfo.bookId})">お気に入り登録</button>
                                </c:if>
                                <c:if test="${bookInfo.favorite == 1}">
                                    <button name="site${bookInfo.bookId}" id="fbtn" value="0" onclick="buttonClick(0, ${bookInfo.bookId})">お気に入り登録済</button>
                                </c:if>
                                <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
