<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>消息页面</title>
	</head>
	<script type="text/javascript">
	window.onload = function(){
		var result = confirm("您确定要给我匿名留言？");
		if(result){
			window.location = "/blog/message.html";
		}else{
			window.location = "/blog";
		}
	}
	</script>
	<body>
		${message}
	</body>
</html>