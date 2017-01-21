<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>나무클럽</title>
    <%@ include file="/WEB-INF/views/layout/common.jsp" %>
    <script type="text/javascript" src="${ctx}/resources/club/js/list.js"></script>
</head>
<body>

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
                    <p><a href="${ctx}/club/open" class="btn btn-warning btn-lg">클럽 개설하기</a></p>
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
          <ul class="nav nav-tabs" style="margin-bottom: 15px;">
              <li class="active"><a href="#joined" data-toggle="tab">가입 클럽&nbsp;<span class="badge">${joinedList.size()}</span></a></li>
              <li><a href="#unjoinded" data-toggle="tab">미가입 클럽&nbsp;<span class="badge">${unjoinedList.size()}</span></a></li>
          </ul>

          <!-- ★★★ Tab Panel -->
          <div id="clubList" class="tab-content">
              <!-- ★★★ 가입 클럽 -->
              <div class="tab-pane fade active in" id="joined">
                  <div class="page-header">
                      <h2 id="container">가입 클럽</h2>
                  </div>

                  <ul class="list-group" id="joinedClubList">
                  	
                  
                  </ul>
              </div>

              <!-- ★★★ 미가입 클럽 -->
              <div class="tab-pane fade" id="unjoinded">
                  <div class="page-header">
                      <h2 id="container">미가입 클럽</h2>
                  </div>

                  <ul class="list-group" id="unjoinedClubList">
                  </ul>
              </div>
          </div>
        </div>
    </div>

<!-- Footer ========================================================================================== -->
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

</div>

</body>
</html>