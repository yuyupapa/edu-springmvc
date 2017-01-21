/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 7. 5.
 */

/**
 * 클럽목록화면 JavaScript
 */
$(document).ready(function(){
	
	var url = ncjs.ctx + "/club/list";
	$.getJSON(url, function(result){
		
		if (result.success && result.message) {
			
			var joinedList = result.message.joinedList;
			var unjoinedList = result.message.unjoinedList;

			var joinedHtml = '';
			var unjoinedHtml = '';

			// 가입클럽목록
			if (joinedList && joinedList.length > 0) {
				
				for (var i = 0, length = joinedList.length; i < length; i++) {
					joinedHtml += createJoinedClubHtml(joinedList[i]);
				}
			} else {
				joinedHtml = createEmptyClubHtml("가입된 클럽이 없습니다.");
			}
			
			$("#joinedClubList").html(joinedHtml);
			
			// 미가입클럽목록
			if (unjoinedList && unjoinedList.length > 0) {
				
				for (var i = 0, length = unjoinedList.length; i < length; i++) {
					unjoinedHtml += createUnjoinedClubHtml(unjoinedList[i]);
				}
			} else {
				html = createEmptyClubHtml("미가입된 클럽이 없습니다.");
			}
			
			$("#unjoinedClubList").html(unjoinedHtml);
		}
	});
	
	// 멤버가입 버튼 클릭 이벤트 처리 (delegate 방식)
	$("#unjoinedClubList").on("click", "[name=joinMemberBtn]", function(){
		
		var clubId = $(this).data("clubId"); // data-club-id 속성값 추출
		var url = ncjs.ctx + "/club/" + clubId + "/join";
		
		$.post(url, function(result) {
			
			if (result.success) {
				var message = "클럽멤버로 가입되었습니다.";
				ncjs.informationMessage(message, function(){
					window.location.reload();
				});
			} else {
				ncjs.errorMessage(result.message);
			}
		});
	});
	
	// 멤버탈퇴 버튼 클릭 이벤트 처리 (delegate 방식)
	$("#joinedClubList").on("click", "[name=withdrawalMemberBtn]", function(){
		
		var clubId = $(this).data("clubId"); // data-club-id attribute value
		var url = ncjs.ctx + "/club/" + clubId + "/withdrawal";
		
		$.post(url, function(result) {
			
			if (result.success) {
				var message = "클럽멤버에서 탈퇴하였습니다.";
				ncjs.informationMessage(message, function(){
					window.location.reload();
				});
			} else {
				ncjs.errorMessage(result.message);
			}
		});
	});
	
	// 가입클럽 HTML 생성
	var createJoinedClubHtml = function(club) {
		//
		var html = '';
		
		html += '<li class="list-group-item">';
		html += '	<span class="badge" title="개설일자" name="openDate">'+ncjs.formatDate(club.openDate)+'</span>';
		html += '	<h4 name="name">'+club.name+'</h4>';
		html += '	<p name="description">'+club.description+'</p>';
		html += '	<a href="javascript:;" class="btn btn-default btn-sm" data-club-id="'+club.id+'" name="withdrawalMemberBtn">';
		html += '		멤버탈퇴 신청하기';
		html += '	</a>';
		html += '</li>';
		
		return html;
	};
	
	// 미가입 클럽 HTML 생성
	var createUnjoinedClubHtml = function(club) {

		var html = '';
		
		html += '<li class="list-group-item">';
		html += '	<span class="badge" title="개설일자" id="openDate">'+ncjs.formatDate(club.openDate)+'</span>';
		html += '	<h4 id="name"><span class="label label-info">추천</span>&nbsp;'+club.name+'</h4>';
		html += '	<p id="description">'+club.description+'</p>';
		html += '	<a href="javascript:;" class="btn btn-default btn-sm" data-club-id="'+club.id+'" name="joinMemberBtn">';
		html += '		멤버 가입하기';
		html += '	</a>';
		html += '</li>';
		
		return html;
	};
	
	// 빈 목록 HTML
	var createEmptyClubHtml = function(emptyMessage) {

		var html = '';
		
		html += '<li class="list-group-item"><p class="text-center">';
		html += emptyMessage;
		html += '</p></li>';
		
		return html;
	};

	
});