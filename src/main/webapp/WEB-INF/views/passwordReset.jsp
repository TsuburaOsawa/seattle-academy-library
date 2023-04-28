<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <div class="wrapper">
        <main>
            <div class="authorization_head">
                <img class="mark" src="resources/img/logo.png" />
                <div class="logo">Seattle Library</div>
            </div>
            <div class="authorization">
                <div class="authorization_form">
                    <form method="post" action="resetAccount">
                        <div class="title">アカウントを再作成</div>
                        <label class="label">メールアドレス</label> <input type="email" class="input" id="email" name="email" autocomplete="off" required> <label class="label">パスワード</label> <input type="password" class="input" id="password" name="password" required> <label class="label">パスワード（確認用）</label> <input type="password" class="input" id="passwordForCheck" name="passwordForCheck" required>
                        <c:if test="${!empty errorMessage}">
                            <div class="error">${errorMessage}</div>
                        </c:if>
                        <input type="submit" class="button primary" value="作成する">
                    </form>
                </div>
                <div class="authorization_navi">
                    <label class="authorization_text">すでにアカウントをお持ちですか？</label>
                    <form method="get" action="<%=request.getContextPath()%>/">
                        <a class="authorization_link marker" href="javascript:void(0)" onclick="this.parentNode.submit()">ログイン</a>
                    </form>
                </div>
            </div>
        </main>
        <footer>
            <div class="copyright">© 2019 Seattle Consulting Co., Ltd. All rights reserved.</div>
        </footer>
    </div>
</body>
</html>