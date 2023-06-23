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
<link href="<c:url value="/resources/css/bookShelfHome.css" />" rel="stylesheet" type="text/css">
<!-- <script type="text/javascript" src="/resources/js/favoriteJavaScript.js"></script> -->
</head>
<body class="wrapper">
    
    <script type="text/javascript">
    function buttonClick(num,id) {    
        if(num==0){ 
            alert('登録解除！');
           //document.getElementById("fbtn").textContent="お気に入り登録";
            /* fbtn.onclick = () => {
            	  fbtn.style.backgroundColor = "#d3d3d3";
            	}; */
            a(num,id);
        } else {
        	alert('登録完了！');
        	//document.getElementById("nofbtn").textContent="お気に入り登録済";
        	/* nofbtn.onclick = () => {
          	  nofbtn.style.backgroundColor = "#ffc0cb";
        	}; */
        	 a(num,id);
            }
        }

    function a(num,id){
        console.log(num);
        console.log("1");
    	 var btn = new XMLHttpRequest();
         btn.open("POST", "http://localhost:8080/SeattleLibrary/favorite?value="+num+"&bookId="+id+"");
       // fbtn.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        btn.send();
        console.log("2");
        }

	</script>
    <script>
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
                 status.open('POST',"http://localhost:8080/SeattleLibrary/deleteShelf?bookId="+arr+"");
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
        <h1>My Shelf</h1>
        <form action="<%=request.getContextPath()%>/search" method="get">
            <div class="flex">
                <input type="text" id="sbox" name="search" placeholder="キーワードを入力" size="20" /> <input type="image" id="sbtn" src="https://cdn.pixabay.com/photo/2014/11/30/14/11/cat-551554_1280.jpg" width="30" height="30" alt="検索" value="検索する" />
            </div>
        </form>
            <a href="<%=request.getContextPath()%>/addBook" class="btn_add_book">書籍の追加</a>  
            <a href="<%=request.getContextPath()%>/moveToHome" class="btn_add_book">ホーム画面へ</a> <input type="button" form="form1" class="return_shelf" value="本棚に戻す" onclick="shelf()">
            <div class="content_body">
            <c:if test="${!empty resultMessage}">
                <div class="error_msg">${resultMessage}</div>
            </c:if>
            <div>
                <div class="booklist">
                    <c:forEach var="bookInfo" items="${booklist}">
                        <div class="books">
                            <div>
                                <form method="post" id="form1" name="form1" class="shelfCheck" action="deleteShelf">
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
