/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 7. 5.
 */

/**
 * 주민등록(회원가입) 화면 JavaScript
 */
$(document).ready(function(){
	
	$("#register-form").submit(function(event){	
		
		// form의 기본이벤트를 막음
		event.preventDefault();
		
		$(this).find("div.form-group").removeClass("has-error");
		
		var valid = true;	
		$(this).find("input.form-control").each(function(){
			if (!$(this).val()) {
				$(this).parents("div.form-group:first").addClass("has-error");
				valid = false;
			}
		});
		if (valid == false) return false;
		
		var url = ncjs.ctx + "/towner/register";
		$.ajax(url, {
			type : "post",
			dataType : "json",
			data : $(this).serialize(),
			success : function(result) {
				
				if (result.success) {
				    var message = "회원가입이 완료되었습니다. 로그인 후 사용가능합니다.";
				    ncjs.informationMessage(message, function(){
				        window.location.href = ncjs.ctx + "/towner/login";
				    });
				} else {
					ncjs.errorMessage(result.message);
				}
			}
		});
	});
});