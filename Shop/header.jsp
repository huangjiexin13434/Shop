<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<c:if test="${user!=null &&user!=''}">
		<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<li><a style="color: green;">你好,${user.name }</a></li>
			<li><a href="register.jsp">注册</a></li>
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="${pageContext.request.contextPath }/order?method=myOrder">我的订单</a></li>
			<li><a href="${pageContext.request.contextPath }/user?method=logout">退出登陆</a></li>
		</ol>
		</div>
	</c:if>
	<c:if test="${user==null ||user=='' }">
		<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<li><a href="login.jsp">登陆</a></li>
			<li><a href="register.jsp">注册</a></li>
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="${pageContext.request.contextPath }/order?method=myOrder">我的订单</a></li>
		</ol>
		</div>
	</c:if>
</div>

<!-- 导航条 -->
<!-- 			动态导航条 -->
			<script type="text/javascript">
					//alert("aaa");
					var jsString="";
					$(function(){
						//alert("bbb");
						$.ajax({
							
							url: "${pageContext.request.contextPath}/product?method=categoryList",
							
							type: "GET",
							success: function(data){
								//[{"xx":xx,"xx1","xx1"}]
								for(var i=0;i<data.length;i++){
									jsString+="<li><a href='${pageContext.request.contextPath}/product?method=productListbyCid&cid="+data[i].cid+"'>"+data[i].cname+"</a></li>";
								}
								
								$("#headcategory").html(jsString);
							},
							dataType: "json"
						});
						
					});
				</script>


<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/default.jsp">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="headcategory">
					<%-- <c:forEach items="${categoryList}" var="category">
						
						<li><a href="#">${category.cname}</a></li>
					</c:forEach> --%>
				</ul>
				
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
	</nav>
</div>