/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 7. 5.
 */

/**
 * 클럽개설화면 JavaScript
 */
$(document).ready(function(){
	
	$("#open-form").submit(function(event){	
		
		// form의 기본이벤트를 막음
		event.preventDefault();
		
		$(this).find("div.form-group").removeClass("has-error");
		
		var valid = true;	
		$(this).find(".form-control").each(function(){
			if (!$(this).val()) {
				$(this).parents("div.form-group:first").addClass("has-error");
				valid = false;
			}
		});
		
		if (valid == false) return false;
		
		var url = ncjs.ctx + "/club/open";
		var data = $(this).serialize();
		
		$.post(url, data, function(result){
			if (result.success) {
			    var message = "클럽이 개설되었습니다.";
			    ncjs.informationMessage(message, function(){
			        window.location.href = ncjs.ctx + "/club";
			    });
			} else {
				ncjs.errorMessage(result.message);
			}
		});
	});
});