<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>黑马商城购物车</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="css/style.css" type="text/css" />
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
			
			font {
				color: #3164af;
				font-size: 18px;
				font-weight: normal;
				padding: 0 10px;
			}
		</style>
<!-- 		<script type="text/javascript" src="myjs/cart.js"></script> -->
		<script type="text/javascript">
			function delproduct(pid){
				if(window.confirm("你确定要删除购物车该商品吗")){
					location.href="${pageContext.request.contextPath}/product?method=delProductCar&pid="+pid;
				}
				
			}
			var tempbuyNum="";
			function updatebuyNum(pid,buyNum,index1) {
				
				//var buyNum = $("#buyNum").val();
				 //alert("buyNum"+buyNum +" pid  "+pid+" "+index1);
				 tempbuyNum=$(".tbuyNum"+index1).val();
				 //alert("eee"+ tempbuyNum);
				 if(buyNum<=0){
					 alert("对不起，购物车数量小于或等于为0");
					 $(".buyNum"+index1).val(tempbuyNum);
					 return;
				 }
				 $.ajax({  
			         type : "POST",  //提交方式  
			         url : "${pageContext.request.contextPath}/product",//路径  
			         data : {  
			        	 "method":"updateProductbuyNum",
			 			"pid":pid,
			 			"buyNum":buyNum
			         },
			         success:function(data) {
			        	// alert("bbbb");
			            alert(data.total+" "+data.subTotal);
			            $("#subTotal"+index1).html(data.subTotal);
			            $(".total").html(data.total); 
			         },
			         dataType: "json"
			     });  
				// alert("bbbb");
			}
			function clearCart(){
				if(window.confirm("你是否要清空购物车?")){
					location.href="${pageContext.request.contextPath}/product?method=clearCart";
				}
			}
		</script>
	</head>

	<body>
		<!-- 引入header.jsp -->
		<jsp:include page="/header.jsp"></jsp:include>

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong style="font-size:16px;margin:5px 0;">订单详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
							
							
							<c:forEach items="${cart.cartItems}" var="entry" varStatus="v">
								<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img  src="${entry.value.product.pimage }" width="70" height="60">
								</td>
								 <td width="30%">
									<a target="_blank">${entry.value.product.pname }(${entry.key })</a>
								</td>
								<td width="20%">
									${entry.value.product.shop_price }
								</td>
								<td width="10%">
									<input type="text"  onchange="updatebuyNum(${entry.key},this.value,${v.index })"
													 class="buyNum${v.index }" name="buyNum${v.index }" value="${entry.value.buyNum }" maxlength="4" size="10">
									<input type="hidden" class="tbuyNum${v.index }" name="tbuyNum${v.index }" value="${entry.value.buyNum }" maxlength="4" size="10">				 
								</td>
								<td width="15%">
									<span name="subTotal${v.index }" id="subTotal${v.index}" class="subtotal" id="subtotal">${entry.value.subTotal}</span>
								</td>
								<td>
									<a onclick="delproduct(${entry.key})" href="#"  class="delete">删除</a>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

			<div style="margin-right:130px;">
				<div style="text-align:right;">
					<em style="color:#ff6600;">
				登录后确认是否享有优惠&nbsp;&nbsp;
			</em> 赠送积分: <em style="color:#ff6600;" class="total">${cart.total }(一元一积分)</em>&nbsp; 商品金额: <strong style="color:#ff6600;" class="total">￥${cart.total }</strong>
				</div>
				<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
					<a href="#" onclick="clearCart()" id="clear" class="clear">清空购物车</a>
					<a href="${pageContext.request.contextPath}/order?method=submitOrder">
						<input type="button" width="100" value="提交订单" name="submit" border="0" style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
						height:35px;width:100px;color:white;">
					</a>
				</div>
			</div>

		</div>

		<!-- 引入footer.jsp -->
		<jsp:include page="/footer.jsp"></jsp:include>

	</body>

</html>