<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>나무클럽</title>
    <%@ include file="/WEB-INF/views/layout/common.jsp" %>
</head>
<body>
<c:set var="loginYn" value="N" />
<c:if test="${loginTowner != null}">
  <c:set var="loginYn" value="Y" />
</c:if>

<!-- Main Navigation ========================================================================================== -->
<%@ include file="/WEB-INF/views/layout/menu.jsp" %>

<!-- Header ========================================================================================== -->
<header>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="jumbotron">
                    <h1>나무 클럽과 함께!</h1>
                    <p>나무 클럽과 함께 특정 취미와 관심사, 특정 그룹 또는 조직에 관한 대화를 시작하세요.</p>
                    <c:choose>
                        <c:when test="${loginYn == 'Y'}">
                          <p><a href="${ctx}/club/open" class="btn btn-warning btn-lg">클럽 개설하기</a></p>
                        </c:when>
                        <c:otherwise>
                          <p><a href="${ctx}/towner/register" class="btn btn-primary btn-lg">신규회원가입</a></p>
                        </c:otherwise>
                    </c:choose>
                    
                </div>
            </div>
        </div>
    </div>
</header>

<!-- Container ======================================================================================= -->

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <!-- ★★★ Tab Menu -->
            
            <%-- 로그인 사용자는 가입/미가입 클럽 목록을 보여준다. --%>
            <c:if test="${loginYn == 'Y'}">
              <ul class="nav nav-tabs" style="margin-bottom: 15px;">
                  <li class="active"><a href="#joined" data-toggle="tab">가입 클럽&nbsp;<span class="badge">${fn:length(joinedList)}</span></a></li>
                  <li><a href="#unjoinded" data-toggle="tab">미가입 클럽&nbsp;<span class="badge">${fn:length(unjoinedList)}</span></a></li>
              </ul>
  
              <!-- ★★★ Tab Panel -->
              <div id="clubList" class="tab-content">
                  <!-- ★★★ 가입 클럽 -->
                  <div class="tab-pane fade active in" id="joined">
                      <div class="page-header">
                          <h2 id="container">가입 클럽</h2>
                      </div>
  
                      <ul class="list-group">
                          <c:forEach var="club" items="${joinedList}">
                              <li class="list-group-item">
                                  <span class="badge" title="개설일자"><fmt:formatDate value="${club.openDate}" pattern="yyyy-MM-dd"/></span>
                                  <h4>${club.name}</h4>
                                  <p>${club.description}</p>
                                  <a href="${ctx}/club/${club.id}/withdrawal" class="btn btn-default btn-sm">멤버탈퇴 신청하기</a>
                              </li>
                          </c:forEach>
                          <c:if test="${empty joinedList}">
                              <li class="list-group-item">
                                  <p class="text-center">가입한 클럽이 없습니다.</p>
                              </li>
                          </c:if>
                      </ul>
                  </div>
  
                  <!-- ★★★ 미가입 클럽 -->
                  <div class="tab-pane fade" id="unjoinded">
                      <div class="page-header">
                          <h2 id="container">미가입 클럽</h2>
                      </div>
  
                      <ul class="list-group">
                          <c:forEach var="club" items="${unjoinedList}">
                              <li class="list-group-item">
                                  <span class="badge" title="개설일자"><fmt:formatDate value="${club.openDate}" pattern="yyyy-MM-dd"/></span>
                                  <h4><span class="label label-info">추천</span>&nbsp;${club.name}</h4>
                                  <p>${club.description}</p>
                                  <a href="${ctx}/club/${club.id}/join" class="btn btn-default btn-sm">멤버 가입하기</a>
                              </li>
                          </c:forEach>
                          <c:if test="${empty unjoinedList}">
                              <li class="list-group-item">
                                  <p class="text-center">가입하지 않은 클럽이 없습니다.</p>
                              </li>
                          </c:if>
                      </ul>
                  </div>
              </div>
              
            </c:if>
            <%-- 로그인이 되지 않으면 전체 목록을 보여준다. --%>
            <c:if test="${loginYn == 'N'}">
              <!-- ★★★ Tab Panel -->
              <div id="clubList" class="tab-content">
                  <div class="page-header">
                      <h2 id="container">클럽 목록</h2>
                  </div>              
                  <!-- ★★★ 모든 클럽 -->
                  <ul class="list-group">
                      <c:forEach var="club" items="${allClubs}">
                        <li class="list-group-item">
                            <span class="badge" title="개설일자"><fmt:formatDate value="${club.openDate}" pattern="yyyy-MM-dd"/></span>
                            <h4><span class="label label-info">추천</span>&nbsp;${club.name}</h4>
                            <p>${club.description}</p>
                            <a href="${ctx}/club/${club.id}/join" class="btn btn-default btn-sm">멤버 가입하기</a>
                        </li>
                      </c:forEach>
                      <c:if test="${empty allClubs}">
                          <li class="list-group-item">
                              <p class="text-center">개설된 클럽이 없습니다.</p>
                          </li>
                      </c:if>
                  </ul>
              </div>
            </c:if>
            
        </div>
    </div>

<!-- Footer ========================================================================================== -->
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

</div>

</body>
</html>