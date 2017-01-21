<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/layout/common.jsp" %>
    <script type="text/javascript" src="${ctx}/resources/towner/js/login.js"></script>    
    
    <style type="text/css">
        body {
           padding-top: 100px;
           padding-bottom: 40px;
           background-color: #ecf0f1;
        }
    </style>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">

    <!-- header -->
    <div class="login-header">
        <h2 class="form-signin-heading">나무 클럽</h2>
    </div>

    <!-- form -->
    <form class="form-signin">
        <div class="form-group">
            <input type="text" class="form-control" id="email" placeholder="아이디(이메일)">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" id="password" placeholder="비밀번호">
        </div>
        <!-- 로그인 실패 메시지 -->
        <div class="alert alert-danger" id="errorMessage" style="display:none;"></div>
        
        <%-- redirect가 Y로 세팅되어 로그인페이지가 요청된 경우 
                로그인이 성공하면 referer 페이지로 이동한다. --%>
        <c:if test="${param.redirect == 'Y'}"> 
          <input type="hidden" name="redirectUrl" value="${referer}" />
        </c:if>
        <div class="row form-btn">
            <button class="btn btn-large btn-warning" id="loginBtn">로그인</button>
            <a href="${ctx}/towner/register" id="registerBtn" class="btn btn-large btn-default">회원가입</a>
        </div>
    </form>

    <!-- footer -->
    <div class="login-footer">
        <p>© NamooSori 2014.</p>
    </div>
</div>
</body>
</html>