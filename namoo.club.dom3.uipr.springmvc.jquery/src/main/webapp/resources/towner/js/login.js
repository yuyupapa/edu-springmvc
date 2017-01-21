/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 7. 5.
 */

/**
 * 로그인화면 JavaScript
 */
$(document).ready(function(){

	// 로그인 버튼 클릭
	$("#loginBtn").click(function(){
		
		// form의 기본이벤트를 막음
		event.preventDefault();
		
		var $formArea = $("form.form-signin");
		$formArea.find("div.form-group").removeClass("has-error");
		
		var valid = true;
		$formArea.find("input.form-control").each(function(){
			if (!$(this).val()) {
				$(this).parents("div.form-group:first").addClass("has-error");
				valid = false;
			}
		});
		
		if (valid == false) return false;
		
		var url = ncjs.ctx + "/towner/login";
		$.ajax(url, {
			type : "post",
			dataType : "json",
			data : {
				"email" : $("#email").val(),
				"password" : $("#password").val()
			},
			success : function(result){
				if (result.success) {
					window.location.href = ncjs.ctx + "/club";
				} else {
					$("#errorMessage").text(result.message).show();
				}
			}
		});
	});
});