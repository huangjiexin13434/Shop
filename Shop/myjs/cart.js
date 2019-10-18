/**

 * 
 */
function updatebuyNum(pid,buyNum) {
	// alert("2222 "+pid);
	//var buyNum = $("#buyNum").val();
	 alert("buyNum"+buyNum +" pid  "+pid);
	/*$.ajax({
		url:"${pageContext.request.contextPath}/product",
		data:{
			"method":"updateProductbuyNum",
			"pid":pid,
			"buyNum":buyNum
		},
		success: function(data){
			alert("success")
		},
		dataType: "json"
	});*/
	 location.href="${pageContext.request.contextPath}/product?method=updateProductbuyNum&pid=3";
	 alert("bbbb");
	 /*$.ajax({  
         type : "POST",  //提交方式  
         url : "${pageContext.request.contextPath}/product",//路径  
         data : {  
        	 "method":"updateProductbuyNum",
 			"pid":pid,
 			"buyNum":buyNum
         },//数据，这里使用的是Json格式进行传输  
         success : function(result) {//返回数据根据结果进行相应的处理  
            alert("");
         }  
     });  */
	// alert("bbbb");
}