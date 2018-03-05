<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://localhost:8088" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${article.article_title}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
	</head>
	<script type="text/javascript">
	window.onload = function(){
		document.getElementById("liuyanbutton").addEventListener("click", function(){
			window.open("/blog/message.html");
		}, false);
		document.getElementById("topbutton").addEventListener("click", function(){window.scrollTo(0, 0);}, false);
	}
	</script>
	<style>
	body{padding:0;margin:auto;width:99%}
	#top{opacity:0.8;height:120px;z-index:20;box-shadow:2px 2px 20px 2px #CCCCCC;width:60px;background-color:#FAFAF9;position:fixed;bottom:20px;right:0px;}
	#topbutton{font-family:"Microsoft YaHei";font-weight:900;width:60px;height:60px;color:#999;font-size:14px;border:1px solid #CCC;outline:0;}
	#liuyanbutton{font-family:"Microsoft YaHei";font-weight:900;background-color:#fafaf9;width:60px;height:60px;color:#999;font-size:14px;border:1px solid #CCC;outline:0;border-bottom:0}
	#bottom_toolBar{border-top:1px #ccc solid;font-family:Arial, Helvetica, sans-serif;background-color:#CCC;height:160px;width:100%;background-color:#f6f6f6;border-top:1px #FFFFFF solid;margin-top:40px;}
	#pageInfo{margin:auto;margin-top:30.5px;font-size:17px;color:#666;text-align:center;}
	#head_div{position:fixed;left:0px;top:0px;background-color:#fff;border:1px #fff solid;width:100%;border-bottom:1px #ccc solid;height:80px;}
	#head_div_name{width:50%;margin-left:15px;margin-top:7px;color:#666;font-family:Arial, Helvetica, sans-serif;font-size:36px;font-weight:900;}
	#head_div_domain{width:50%;margin-left:15px;color:#666;font-family:Arial, Helvetica, sans-serif;font-size:16px;font-weight:900;}
	#head_div_domain a{color:#ccc;text-decoration:none;}
	#middle_content{margin-top:80px;width:100%;border:1px #fff solid;}
	#passage_area{width:90%;margin:auto;margin-top:10px;}
	#passage_date{width:100%;font-size:16px;color:#999;}
	#passage_content{width:100%;font-size:18px;font-family:serif;color:#333;}
	img{width:100%;}
	</style>
	<body>
		<div id="top">
    	 	<button id="liuyanbutton">留言</button>
    	 	<button id="topbutton">顶部</button>
        </div>
        <div id="head_div">
        	<div id="head_div_name">IL MARE</div>
        	<div id="head_div_domain"><a href="http://www.ilmareblog.com/blog">ilmareblog.com</a></div>
        </div>
        <div id="middle_content">
        	<div id="passage_area">
        		<h1>${article.article_title}</h1>
        		<div id="passage_date">${fn:DateFormateUtil(article.create_time)}</div><br/>
        		<div id="passage_content">${article.article_content}</div>
        	</div>
        </div>
        <div id="bottom_toolBar">
        	<div id="pageInfo"><font color="#FF0000">For my dear Crystal</font><br/><br/>Designed and implemented by IL MARE<br/><br/>May all your wishes come true - IL MARE</div>
        </div>
	</body>
</html>