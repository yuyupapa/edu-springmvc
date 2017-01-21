<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/layout/common.jsp" %>
    <style type="text/css">
        body {
            padding-top: 100px;
            padding-bottom: 40px;
            background-color: #ecf0f1;
        }
        .login-header {
            max-width: 400px;
            padding: 15px 29px 25px;
            margin: 0 auto;
            background-color: #2c3e50;
            color: white;
            text-align: center;
            -webkit-border-radius: 15px 15px 0px 0px;
            -moz-border-radius: 15px 15px 0px 0px;
            border-radius: 15px 15px 0px 0px;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            box-shadow: 0 1px 2px rgba(0,0,0,.05);
        }
        .login-footer {
            max-width: 400px;
            margin: 0 auto 20px;
            padding-left: 10px;
        }
        .form-signin {
            max-width: 400px;
            padding: 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            -webkit-border-radius: 0px 0px 15px 15px;
            -moz-border-radius: 0px 0px 15px 15px;
            border-radius: 0px 0px 15px 15px;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            box-shadow: 0 1px 2px rgba(0,0,0,.05);
        }
        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 15px;
        }
        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }
        .form-btn {
            text-align: center;
            padding-top: 20px;
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
    <form class="form-signin" action="${ctx}/towner/login" method="post">
        <input type="text" class="form-control" name="inputEmail" placeholder="아이디(이메일)" required>
        <input type="password" class="form-control" name="inputPassword" placeholder="비밀번호" required>
        
        <%-- redirect가 Y로 세팅되어 로그인페이지가 요청된 경우 
                로그인이 성공하면 referer 페이지로 이동한다. --%>
        <c:if test="${param.redirect == 'Y'}"> 
          <input type="hidden" name="redirectUrl" value="${referer}" />
        </c:if>
        <!-- 
        <label class="checkbox">
            <input type="checkbox" value="remember-me"> 아이디 기억하기
        </label>
         -->
        <div class="row form-btn">
            <button class="btn btn-large btn-warning" type="submit">로그인</button>
            <a href="${ctx}/towner/register" class="btn btn-large btn-default">회원가입</a>
        </div>
    </form>

    <!-- footer -->
    <div class="login-footer">
        <p>© NamooSori 2014.</p>
    </div>
</div>
</body>
</html>