/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 29.
 */

var ncjs = ncjs || {};

(function(){
	//
	/**
	 * @param popupType : info - 정보메시지, error - 에러메시지
	 * @param title     : (필수) 팝업 제목
	 * @param message   : (필수) 팝업에 보여줄 메시지
	 * @param callback  : (선택) 확인버튼을 클릭할 때 수행할 콜백함수 
	 */
	var _popup = function(popupType, title, message, callback){

		if ($("#modelPopup").length == 0) {
			$("body").append('<div class="modal fade" id="modalPopup" tabindex="-1" role="dialog"></div>');
		}
		
	    var popupHtml = '<div class="modal-dialog">';
	    popupHtml += '<div class="'+popupType+'-header">';
		popupHtml += '<h2 class="'+popupType+'-heading">안내</h2>';
		popupHtml += '</div>';
		popupHtml += '<div class="'+popupType+'-body">';
		
		popupHtml += '<h3>'+title+'</h3>';
		popupHtml += '<p>' + message + '</p>';
		
		popupHtml += '<div class="row info-btn">';
		popupHtml += '<a href="#" id="_confirmBtn" class="btn btn-large btn-default">확인</a>';
		popupHtml += '</div>';
		popupHtml += '</div>';
		popupHtml += '</div>';
		
		$popup = $(popupHtml);
		
		var callbackProxy = function() {
			// 콜백함수가 제공된 경우 콜백수행 후 팝업창을 닫는다.
			if (callback) callback(); // 
			$("#modalPopup").modal('hide');
		};
		$popup.find(".btn").click(callbackProxy);
		
		$("#modalPopup").html($popup).modal();
	};
	
	
	var informationMessage = function(message, callback) {
		_popup('info', '안내메시지', message, callback);
	};
	
	var errorMessage = function(message, callback) {
		_popup('error', '오류메시지', message, callback);
	};

	// jQuery Ajax default error handling
	$.ajaxSetup({
		error : function(xhr, status, error) {
			errorMessage("[서버에러] " + error);
		}
	});
	
	// utility
	var formatDate = function(inputDate) {

		if (typeof inputDate == "number") {
			inputDate = new Date(inputDate);
		}
		var year = inputDate.getFullYear();
		var month = inputDate.getMonth() + 1;
		var date = inputDate.getDate();
		
		month = (month < 10) ? "0"+month : month;
		date = (date < 10) ? "0" + date : date;
		
		return year + "." + month + "." + date;
	};
	
	// 나무클럽 탈퇴버튼 이벤트 처리
	$(document).ready(function(){
		
		// 나무클럽 회원탈퇴
		$("#withdrawalTownerBtn").click(function(){
			
			var url = ncjs.ctx + "/towner/withdrawal";
			$.post(url, function(result){
				
				if (result.success) {
					ncjs.informationMessage("나무클럽에서 탈퇴하였습니다.", function(){
						window.location.href = ncjs.ctx + "/towner/login";
					});
				} else {
					ncjs.errorMessage(result.message);
				}
			});
		});
	});
	
	// public methods
	ncjs.informationMessage = informationMessage;
	ncjs.errorMessage = errorMessage;
	ncjs.formatDate = formatDate;
	
}());
