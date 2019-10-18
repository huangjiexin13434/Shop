<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	//alert($);
	$(function(){
		$("#btn").click(function() {
			//alert("222");
			m=$(".carimage").css("margin-top").replace("px","");
			alert(m.replace("px",""));
			/* setInterval(function() {
				$("#carimage").css("margin-top",$("#carimage").css("margin-top")-10)
			}, 100) */
			var i=parseInt(m)+50;
			alert(i);
			$(".carimage").css("background-color","pink");
			$(".carimage").css("margin-top",i+"px");
		})
	});
</script>
</head>
<body>
<div>
	 <div style="border-style:solid;background-color:yellow;">
	 	<input type="submit" value="加入购物车" style=" margin-top: 100px;margin-left: 100px;" id="btn">
	 
	 </div>
	 <div class="carimage" style="border-style:solid; margin-top: 70px;margin-left: 250px;width: 200px;height:100px;">
	 	<img src="/Shop/images/cart1.gif">
	 	sssssssss
	 </div>
</div>

</body>
</html>