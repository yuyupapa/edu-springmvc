<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${ctx}/club">나무클럽</a>
        </div>
        <div class="navbar-collapse collapse navbar-responsive-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="${ctx}/club">홈</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                  <c:choose>
                    <c:when test="${loginTowner != null}">
                      <a href="${ctx}/towner/logout">로그아웃 [ ${loginTowner.name} ]</a>
                    </c:when>
                    <c:otherwise>
                      <a href="${ctx}/towner/login">로그인</a>
                    </c:otherwise>
                  </c:choose>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">관리 <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">클럽 관리</a></li>
                        <li><a href="#">회원 관리</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">설정 <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">개인정보</a></li>
                        <li><a href="#">모바일 환경설정</a></li>
                    </ul> 
                </li>
            </ul>
        </div>
    </div>
</div>
