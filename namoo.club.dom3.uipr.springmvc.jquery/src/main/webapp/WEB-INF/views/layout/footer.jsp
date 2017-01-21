<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<footer>
    <div class="row">
        <div class="col-lg-12">
            <ul class="list-unstyled">
                <li class="pull-right"><a href="#top">위로 이동</a></li>
                <li><a href="${ctx}/club/list">나무클럽 홈</a>
                </li>
                <li><a href="#">RSS</a></li>
                <li><a href="#">이용약관</a></li>
                <li><a href="#">도움말</a></li>
                <c:if test="${loginTowner != null}">
                  <li><a href="javascript:;" id="withdrawalTownerBtn">회원탈퇴</a></li>
                </c:if>
            </ul>
            <p>© NamooSori 2014.</p>
        </div>
    </div>
</footer>