<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>書籍の詳細｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/bookDetail.css" />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/thumbnail.js"></script>
</head>
<body class="wrapper">
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
        <div>
            <div class="booklist">
                <div class="books">
                    <c:if test="${empty bookInfo.thumbnailUrl}">
                        <img class="book_noimg" src="resources/img/noImg.png">
                    </c:if>
                    <c:if test="${!empty bookInfo.thumbnailUrl}">
                        <img class="book_noimg" src="${bookInfo.thumbnailUrl}">
                    </c:if>
                    <ul>
                        <li class="book_title">${bookInfo.title}</li>
                        <li class="book_author">(著)${bookInfo.author}</li>
                        <li class="book_publisher">出版社：${bookInfo.publisher}</li>
                        <li class="book_publish_date">出版日：${bookInfo.publishDate}</li>
                        <li class="book_description">説明文：${bookInfo.description}</li>
                        <li class="book_favorite">お気に入り：${bookInfo.favorite}</li>
                        <li class="book_genre">ジャンル：${bookInfo.genre}</li>
                        <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                    </ul>
                </div>
            </div>
        </div>
    </main>
</body>
</html>