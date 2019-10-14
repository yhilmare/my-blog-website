<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://localhost:8088" prefix="fn"%>
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
	function sendHttpPOST(url, data, callBack){
		var array = new Array();
		var names = Object.getOwnPropertyNames(data);
		for(var i = 0; i < names.length; i++){
			if(!data.hasOwnProperty(names[i]))continue;
			data[names[i]] = encodeURIComponent(data[names[i]]);
			array.push(names[i] + "=" + data[names[i]]);
		}
		var request = new XMLHttpRequest();
		request.open("POST", url);
		request.onreadystatechange = function(event){
			var res = event.target;
			if(res.readyState == XMLHttpRequest.DONE && res.status == 200){
				if(callBack){
					callBack(request);
				}
			}
		}
		request.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		request.send(array.join("&"));
	}
	function removerAllChildNodes(div){
		while(div.hasChildNodes()){
			div.removeChild(div.firstChild);
		}
	}
	function initVisitorUser(){
		sendHttpPOST("/blog/IsVistorLoginController", {}, function(request){
			if(request.responseText != "false"){
				visitor = JSON.parse(request.responseText);
				var img = document.getElementById("comment_avator");
				img.src = visitor.figureurl_qq == null ? "/blog/Images/avator.jpg" : visitor.figureurl_qq;
				var qqloginbutton = document.getElementById("qqloginbutton");
				var commentSubmitbutton = document.getElementById("commentSubmitbutton");
				commentSubmitbutton.style.display = "block";
				qqloginbutton.style.display = "none";
			}
		});
	}
	
	function getQueryString(name) {
		var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
		var r = window.location.search.substr(1).match(reg);
		if (r != null) {
			return unescape(r[2]);
		}
		return null;
	}
	window.onload = function(){
		document.getElementById("liuyanbutton").addEventListener("click", function(){
			window.open("/blog/message.html");
		}, false);
		document.getElementById("topbutton").addEventListener("click", function(){window.scrollTo(0, 0);}, false);
		initVisitorUser();
		initArticleComment();
		initCommentSubmitButton();
	}
	function initCommentSubmitButton(){
		var commentSubmitbutton = document.getElementById("commentSubmitbutton");
		commentSubmitbutton.addEventListener("click", function(){
			var comment_textarea = document.getElementById("comment_textarea");
			if (comment_textarea.value == null || comment_textarea.value.length == 0){
				alert("评论内容不能为空");
			}else{
				if (commentReplyInfo.commentID != "undefined" && commentReplyInfo.visitorNickname != "undefined"){
					sendHttpPOST("/blog/InsertCommentReplyController", {commentID:commentReplyInfo.commentID, commentReplyContent:comment_textarea.value}, 
							function(request){
						if (request.responseText != "评论发布成功"){
							alert(request.responseText);
						}else{
							comment_textarea.value = null;
							comment_textarea.placeholder = "说点什么吧...";
							commentReplyInfo.commentID = "undefined";
							commentReplyInfo.visitorNickname = "undefined";
							sendHttpPOST("/blog/SelectArticleCommentController", {pageIndex:"1", articleID:articleInfo.articleID}, function(request1){
								if (request1.responseTest == "参数传递错误"){
									alert("参数传递错误");
								}else{
									var obj = JSON.parse(request1.responseText);
									var total_comment_record = document.getElementById("total_comment_record");
									total_comment_record.innerHTML = obj.totalRecord;
									initArticleCommentList(obj.list)
									updateCommentPageIndex(obj, getQueryString("article_id"));
								}
							});
						}
					});
					
				}else{
					sendHttpPOST("/blog/InsertArticleCommentController", {articleID:articleInfo.articleID, commentContent:comment_textarea.value}, 
							function(request){
						if (request.responseText != "评论发布成功"){
							alert(request.responseText);
						}else{
							comment_textarea.value = null;
							sendHttpPOST("/blog/SelectArticleCommentController", {pageIndex:"1", articleID:articleInfo.articleID}, function(request1){
								if (request1.responseTest == "参数传递错误"){
									alert("参数传递错误");
								}else{
									var obj = JSON.parse(request1.responseText);
									var total_comment_record = document.getElementById("total_comment_record");
									total_comment_record.innerHTML = obj.totalRecord;
									initArticleCommentList(obj.list)
									updateCommentPageIndex(obj, getQueryString("article_id"));
								}
							});
						}
					});
				}
			}
			event.stopPropagation();
			if(event.preventDefault){
				event.preventDefault();
			}
			if(event.returnValue){
				event.returnValue = false;
			}
			return false;
		}, false);
	}
	function setCSSStyle(css, div){
		if(css == null || div == null) return;
		var names = Object.getOwnPropertyNames(css);
		for(var i = 0; i < names.length; i++){
			if(!css.hasOwnProperty(names[i]))continue;
			div.style[names[i]] = css[names[i]];
		}
	}
	function updateCommentPageIndex(page, ID){
		var array = new Array();
		var pageIndex_container = document.getElementById("pageIndex_container");
		pageIndex_container.innerHTML = "";
		removerAllChildNodes(pageIndex_container);
		var currentPage = parseInt(page.currentPage);
		var startPage = parseInt(page.startPage);
		var endPage = parseInt(page.endPage);
		var totalPage = parseInt(page.totalPage);
		var a = document.createElement("a");
		a.id = "pageIndex=1";a.innerHTML = "首页  ";
		a.className = "comment_list_pageIndex";
		pageIndex_container.appendChild(a);
		if(currentPage > 1){
			a = document.createElement("a");
			a.id = "pageIndex=" + (currentPage - 1);a.innerHTML = " 上一页 ";
			a.className = "comment_list_pageIndex";
			pageIndex_container.appendChild(a);
		}
		if(startPage <= endPage){
			for(var i = startPage; i <= endPage; i++){
				a = document.createElement("a");
				a.id = "pageIndex=" + i;a.innerHTML = " " + i + "";
				a.className = "comment_list_pageIndex";
				pageIndex_container.appendChild(a);
			}
		}
		if(currentPage < totalPage){
			a = document.createElement("a");
			a.id = "pageIndex=" + (currentPage + 1);a.innerHTML = " 下一页  ";
			a.className = "comment_list_pageIndex";
			pageIndex_container.appendChild(a);
		}
		a = document.createElement("a");
		a.id = "pageIndex=" + totalPage;a.innerHTML = " 尾页";
		a.className = "comment_list_pageIndex";
		pageIndex_container.appendChild(a);
		var div = document.createElement("span");
		div.innerHTML = " 第" + currentPage + "页";
		pageIndex_container.appendChild(div);
		var as = document.getElementsByClassName("comment_list_pageIndex");
		for(var i = 0; i < as.length; i++){
			var elt = as[i];
			elt.addEventListener("click", function(){
				var info = this.id;
				array = info.split("=");
				info = array[array.length - 1];
				sendHttpPOST("/blog/SelectArticleCommentController", {pageIndex:info, articleID:ID}, function(request){
					if (request.responseTest == "参数传递错误"){
						alert("参数传递错误");
					}else{
						var obj = JSON.parse(request.responseText);
						initArticleCommentList(obj.list);
						updateCommentPageIndex(obj, getQueryString("article_id"));
					}
				});
				event.stopPropagation();
				if(event.preventDefault){
					event.preventDefault();
				}
				if(event.returnValue){
					event.returnValue = false;
				}
				return false;
			}, false);
		}
	}
	function generateAllCommentReplyList(array, comment_item_content_reply){
		for(var i = 0; i < array.list.length; i ++){
			commentReply = array.list[i];
			var comment_item_reply_item = document.createElement("div");
			comment_item_reply_item.className = "comment_item_reply_item";
			
			var comment_item_reply_meta = document.createElement("div");
			comment_item_reply_meta.className = "comment_item_reply_meta";
			var comment_item_reply_meta_name = document.createElement("span");
			comment_item_reply_meta_name.className = "comment_item_reply_meta_name";
			comment_item_reply_meta_name.innerHTML = commentReply.visitor_nickname + "&nbsp;";
			comment_item_reply_meta.appendChild(comment_item_reply_meta_name);
			var comment_item_reply_meta_date = document.createElement("span");
			comment_item_reply_meta_date.className = "comment_item_reply_meta_date";
			comment_item_reply_meta_date.innerHTML = "发表于&nbsp;" + commentReply.comment_reply_date;
			comment_item_reply_meta.appendChild(comment_item_reply_meta_date);
			
			if (visitor.visitor_id == commentReply.visitor_id){
				var comment_item_reply_meta_op1 = document.createElement("span");
				comment_item_reply_meta_op1.setAttribute("data-tag", commentReply.comment_reply_id);
				comment_item_reply_meta_op1.className = "comment_item_reply_meta_op1";
				comment_item_reply_meta_op1.innerHTML = "删除";
				comment_item_reply_meta.appendChild(comment_item_reply_meta_op1);
				comment_item_reply_meta_op1.addEventListener("click", function(){
					if (confirm("该操作不可恢复，确认要删除吗？")){
						sendHttpPOST("/blog/DeleteArticelCommentReplyController", {commentReplyID:this.getAttribute("data-tag")}, function(request){
							alert(request.responseText);
						});
					}
					event.stopPropagation();
					if(event.preventDefault){
						event.preventDefault();
					}
					if(event.returnValue){
						event.returnValue = false;
					}
					return false;
				}, false);
			}
			
			comment_item_reply_item.appendChild(comment_item_reply_meta);
			
			var comment_item_reply_detail = document.createElement("div");
			comment_item_reply_detail.className = "comment_item_reply_detail";
			comment_item_reply_detail.innerHTML = commentReply.comment_reply_content;
			comment_item_reply_item.appendChild(comment_item_reply_detail);
			
			if (i == array.list.length - 1){
				setCSSStyle({paddingBottom: "5px", borderBottom: 0}, comment_item_reply_item);
			}
			comment_item_content_reply.appendChild(comment_item_reply_item);
		}
	}
	function initCommentReplyList(array){
		var comment_item_content_reply = document.createElement("div");
		comment_item_content_reply.className = "comment_item_content_reply";
		comment_item_content_reply.id = array.list[0].comment_id;
		for(var i = 0; i < array.list.length; i ++){
			commentReply = array.list[i];
			var comment_item_reply_item = document.createElement("div");
			comment_item_reply_item.className = "comment_item_reply_item";
			
			var comment_item_reply_meta = document.createElement("div");
			comment_item_reply_meta.className = "comment_item_reply_meta";
			var comment_item_reply_meta_name = document.createElement("span");
			comment_item_reply_meta_name.className = "comment_item_reply_meta_name";
			comment_item_reply_meta_name.innerHTML = commentReply.visitor_nickname + "&nbsp;";
			comment_item_reply_meta.appendChild(comment_item_reply_meta_name);
			var comment_item_reply_meta_date = document.createElement("span");
			comment_item_reply_meta_date.className = "comment_item_reply_meta_date";
			comment_item_reply_meta_date.innerHTML = "发表于&nbsp;" + commentReply.comment_reply_date;
			comment_item_reply_meta.appendChild(comment_item_reply_meta_date);
			if (visitor.visitor_id == commentReply.visitor_id){
				var comment_item_reply_meta_op1 = document.createElement("span");
				comment_item_reply_meta_op1.setAttribute("data-tag", commentReply.comment_reply_id);
				comment_item_reply_meta_op1.className = "comment_item_reply_meta_op1";
				comment_item_reply_meta_op1.innerHTML = "删除";
				comment_item_reply_meta.appendChild(comment_item_reply_meta_op1);
				comment_item_reply_meta_op1.addEventListener("click", function(){
					if (confirm("该操作不可恢复，确认要删除吗？")){
						sendHttpPOST("/blog/DeleteArticelCommentReplyController", {commentReplyID:this.getAttribute("data-tag")}, function(request){
							alert(request.responseText);
						});
					}
					event.stopPropagation();
					if(event.preventDefault){
						event.preventDefault();
					}
					if(event.returnValue){
						event.returnValue = false;
					}
					return false;
				}, false);
			}
			comment_item_reply_item.appendChild(comment_item_reply_meta);
			
			var comment_item_reply_detail = document.createElement("div");
			comment_item_reply_detail.className = "comment_item_reply_detail";
			comment_item_reply_detail.innerHTML = commentReply.comment_reply_content;
			comment_item_reply_item.appendChild(comment_item_reply_detail);
			
			if (i == array.list.length - 1 && array.totalRecord == array.list.length){
				setCSSStyle({paddingBottom: "5px", borderBottom: 0}, comment_item_reply_item);
			}
			
			comment_item_content_reply.appendChild(comment_item_reply_item);
		}
		
		if(array.totalRecord > array.list.length){
			var comment_item_reply_loadmore = document.createElement("div");
			comment_item_reply_loadmore.className = "comment_item_reply_loadmore";
			var a = document.createElement("a");
			a.innerHTML = "共" + array.totalRecord + "条回复>>";
			comment_item_reply_loadmore.appendChild(a);
			comment_item_content_reply.appendChild(comment_item_reply_loadmore);
			a.addEventListener("click", function(){
				var comment_id = array.list[0].comment_id;
				sendHttpPOST("/blog/SelectArticleCommentReplyController", {pageIndex:"1", commentID:comment_id, pageContain:array.totalRecord}, function(request){
					var obj = JSON.parse(request.responseText);
					var comment_item_content_reply = document.getElementById(comment_id);
					comment_item_content_reply.innerHTML = "";
					removerAllChildNodes(comment_item_content_reply);
					generateAllCommentReplyList(obj, comment_item_content_reply);
				});
				event.stopPropagation();
				if(event.preventDefault){
					event.preventDefault();
				}
				if(event.returnValue){
					event.returnValue = false;
				}
				return false;
			}, false);
		}
		
		return comment_item_content_reply;
	}
	function initArticleCommentList(array){
		var passage_comment_content = document.getElementById("passage_comment_content");
		passage_comment_content.innerHTML = "";
		removerAllChildNodes(passage_comment_content);
		for(var i = 0; i < array.length; i ++){
			var comment  = array[i];
			var comment_item = document.createElement("div");
			comment_item.className = "comment_item";
			
			var comment_item_avator = document.createElement("div");
			comment_item_avator.className = "comment_item_avator";
			var img = document.createElement("img");
			if(comment.figureurl_qq == null){
				img.src="/blog/Images/avator.jpg";
			}else{
				img.src=comment.figureurl_qq;
			}
			img.alt="avator";
			comment_item_avator.appendChild(img);
			comment_item.appendChild(comment_item_avator);
						
			var comment_item_content = document.createElement("div");
			comment_item_content.className = "comment_item_content";
			var comment_item_content_meta = document.createElement("div");
			comment_item_content_meta.className = "comment_item_content_meta";
			var comment_item_content_meta_name = document.createElement("span");
			comment_item_content_meta_name.className = "comment_item_content_meta_name";
			comment_item_content_meta_name.innerHTML = comment.visitor_nickname + "&nbsp;";
			comment_item_content_meta.appendChild(comment_item_content_meta_name);
			var comment_item_content_meta_date = document.createElement("span");
			comment_item_content_meta_date.className = "comment_item_content_meta_date";
			comment_item_content_meta_date.innerHTML = "发表于&nbsp;" + comment.comment_date;
			comment_item_content_meta.appendChild(comment_item_content_meta_date);
			
			if (visitor.visitor_id != "undefined"){
				if (visitor.visitor_id == comment.visitor_id){
					var comment_item_content_meta_op1 = document.createElement("span");
					comment_item_content_meta_op1.setAttribute("data-tag", comment.comment_id);
					comment_item_content_meta_op1.className = "comment_item_content_meta_op1";
					comment_item_content_meta_op1.innerHTML = "删除";
					comment_item_content_meta.appendChild(comment_item_content_meta_op1);
					comment_item_content_meta_op1.addEventListener("click", function(){
						if (confirm("该操作不可恢复，确认要删除吗？")){
							sendHttpPOST("/blog/DeleteArticleCommentController", {commentID:this.getAttribute("data-tag")}, function(request){
								alert(request.responseText);
							});
						}
						event.stopPropagation();
						if(event.preventDefault){
							event.preventDefault();
						}
						if(event.returnValue){
							event.returnValue = false;
						}
						return false;
					}, false);
				}
				
				var comment_item_content_meta_op2 = document.createElement("span");
				comment_item_content_meta_op2.setAttribute("data-tag", comment.comment_id);
				comment_item_content_meta_op2.setAttribute("data-tag1", comment.visitor_nickname);
				var tmp_content = comment.comment_content;
				if (comment.comment_content.length > 25){
					tmp_content = tmp_content.substring(0, 24) + "...";
				}
				comment_item_content_meta_op2.setAttribute("data-tag2", tmp_content);
				comment_item_content_meta_op2.className = "comment_item_content_meta_op2";
				comment_item_content_meta_op2.innerHTML = "回复";
				comment_item_content_meta.appendChild(comment_item_content_meta_op2);
				
				comment_item_content_meta_op2.addEventListener("click", function(){
					commentReplyInfo.commentID = this.getAttribute("data-tag");
					commentReplyInfo.visitorNickname = this.getAttribute("data-tag1");
					var comment_textarea = document.getElementById("comment_textarea");
					comment_textarea.placeholder = "@" + this.getAttribute("data-tag1") + "：" + this.getAttribute("data-tag2");
					
					document.querySelector("#passage_slogan").scrollIntoView();
				}, "false");
				
			}
			
			comment_item_content.appendChild(comment_item_content_meta);
			
			var comment_item_content_detail = document.createElement("div");
			comment_item_content_detail.className = "comment_item_content_detail";
			comment_item_content_detail.innerHTML = comment.comment_content;
			comment_item_content.appendChild(comment_item_content_detail);
			
			if (comment.subComment.list.length != 0){
				var comment_item_content_reply = initCommentReplyList(comment.subComment);
				comment_item_content.appendChild(comment_item_content_reply);
			}
			
			comment_item.appendChild(comment_item_content);
			passage_comment_content.appendChild(comment_item);
		}
	}
	function initArticleComment(){
		articleInfo.articleID = getQueryString("article_id");
		sendHttpPOST("/blog/SelectArticleCommentController", {pageIndex:"1", articleID:getQueryString("article_id")}, function(request){
			if (request.responseTest == "参数传递错误"){
				alert("参数传递错误");
			}else{
				var obj = JSON.parse(request.responseText);
				var total_comment_record = document.getElementById("total_comment_record");
				total_comment_record.innerHTML = obj.totalRecord;
				initArticleCommentList(obj.list)
				updateCommentPageIndex(obj, getQueryString("article_id"));
			}
		});
	}
	var articleInfo = {}
	var commentReplyInfo = {
		commentID:"undefined",
		visitorNickname:"undefined"
	}
	var holder = {};
	var visitor = {
		visitor_id:"undefined",
		visitor_pwd:"undefined",
		visitor_nickname:"undefined",
		visitor_gender:"undefined"
	}
	function toLogin() {
		window.open("/blog/QQLoginController");
	}
	</script>
	<style>
	body{padding:0;margin:auto;width:99%}
	#top{opacity:0.8;height:120px;z-index:20;box-shadow:2px 2px 20px 2px #CCCCCC;width:60px;background-color:#FAFAF9;position:fixed;bottom:20px;right:0px;}
	#topbutton{font-family:"Microsoft YaHei";font-weight:900;width:60px;height:60px;color:#999;font-size:14px;border:1px solid #CCC;outline:0;}
	#liuyanbutton{font-family:"Microsoft YaHei";font-weight:900;background-color:#fafaf9;width:60px;height:60px;color:#999;font-size:14px;border:1px solid #CCC;outline:0;border-bottom:0}
	#bottom_toolBar{border-top:1px #ccc solid;font-family:Arial, Helvetica, sans-serif;background-color:#CCC;height:160px;width:100%;background-color:#f6f6f6;border-top:1px #FFFFFF solid;margin-top:20px;}
	#pageInfo{margin:auto;margin-top:30.5px;font-size:15px;color:#666;text-align:center;}
	#head_div{position:fixed;left:0px;top:0px;background-color:#fff;border:1px #fff solid;width:100%;border-bottom:1px #ccc solid;height:80px;}
	#head_div_name{width:50%;margin-left:15px;margin-top:7px;color:#666;font-family:Arial, Helvetica, sans-serif;font-size:36px;font-weight:900;}
	#head_div_domain{width:50%;margin-left:15px;color:#666;font-family:Arial, Helvetica, sans-serif;font-size:16px;font-weight:900;}
	#head_div_domain a{color:#ccc;text-decoration:none;}
	#middle_content{margin-top:80px;width:100%;border:1px #fff solid;}
	#passage_area{width:94%;margin:auto;margin-top:10px;}
	#passage_date{width:100%;font-size:16px;color:#999;}
	#passage_content{width:100%;font-size:17px;font-family:serif;color:#333;}
	#passage_content pre{overflow-x:auto;}
	img{width:100%;}
	#passage_slogan{padding:25px 0 25px 0;border-top:1px #ccc solid;margin:auto;width:94%;margin-top:40px;}
    #passage_slogan_name{font-family:'Microsoft YaHei';font-size:32px;color:#999}
    #passage_slogan_content{margin-top:10px;font-family:'Microsoft YaHei';font-size:15px;color:#999;}
    
    
    #passage_comment{margin:auto;margin-top:35px;width:94%;}
    #passage_total{color:#606060;font-family:"Microsoft YaHei";font-size:15px;padding-bottom:25px;border-bottom:1px #ccc solid;}
    #passage_comment_edit{margin-top:25px;height:150px;}
    #passage_comment_avator{width:10%;height:38px;}
    #passage_comment_avator img{margin:auto;width:38px;height:38px;border-radius:19px;}
    #passage_comment_editreal{margin-left:15%;margin-top:-38px;width:85%;height:100%;}
    #passage_comment_editreal textarea{border:1px #d5d5d5 solid;background-color:#f1f1f1;outline:0;width:96%;height:40%;overflow:auto;resize:none;border-radius:6px;color:#999;font-family:"Microsoft YaHei";font-size:14px;padding:10px 2% 10px 2%;}
    #passage_comment_loginbutton{margin-top:10px;width:100%; height:30%;}
    #passage_comment_loginbutton button{font-family:"Microsoft YaHei";font-size:12px;border-radius:5px;width:100px;height:30px;float:right;outline:0;color:white;background-color:#4f7ae2;border:0;}
    #passage_comment_loginbutton div{float:left;font-size:12px;color:#4f7ae2;}
    
    #passage_comment_content{margin-top:0px;}
    
    .comment_item{width:100%;margin-bottom:15px;}
    .comment_item_avator{width:10%;height:38px;}
    .comment_item_avator img{margin:auto;width:38px;height:38px;border-radius:19px;}
    .comment_item_content{margin-left:15%;margin-top:-38px;width:85%;}
    .comment_item_content_meta{padding:5px 0 5px 0;width:100%;font-family:"Microsoft YaHei";font-size:13px;}
    .comment_item_content_meta_name{color:#4f7ae2;}
    .comment_item_content_meta_date{color:#8c8c8c;}
    .comment_item_content_meta_op1{margin-left:10px;font-size:10px;color:#4f7ae2}
    .comment_item_content_meta_op2{margin-left:10px;font-size:10px;color:#4f7ae2}
    .comment_item_content_detail{padding:5px 0 10px 0;margin-top:10px;width:100%;font-size:13px;font-family:"Microsoft YaHei";color:#262626;}
    .comment_item_content_reply{width:100%;background-color:#f7f7f7;margin-top:5px;padding-top:5px;}
    .comment_item_reply_item{margin:auto;width:94%;border-bottom:1px #ccc solid;}
    
    .comment_item_reply_meta{padding:7px 0 3px 0;width:100%;font-family:"Microsoft YaHei";font-size:11px;}
    .comment_item_reply_meta_name{color:#4f7ae2;}
    .comment_item_reply_meta_date{color:#8c8c8c;}
    .comment_item_reply_meta_op1{margin-left:10px;font-size:10px;color:#4f7ae2}
    .comment_item_reply_detail{padding:3px 0 13px 0;margin-top:5px;width:100%;font-size:11px;font-family:"Microsoft YaHei";color:#262626;}
    
    .comment_item_reply_loadmore{padding-bottom:15px;margin:auto;margin-top:10px;width:94%;}
    .comment_item_reply_loadmore a{color:#4f7ae2;font-size:10px;}
    
    #comment_pageIndex{width:100%;color:#4f7ae2;font-size:10px;height:20px;}
    #pageIndex_container{float:right;height:20px;}
    #comment_pageIndex a{color:#4f7ae2;}
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
        	<div id="passage_slogan">
                <div id="passage_slogan_name">IL MARE</div>
                <div id="passage_slogan_content">人类最大的智慧就是等待和希望</div>
            </div>
            <hr style="border:0;border-top:1px #ccc solid;"/>
            <div id="passage_comment">
                <div id="passage_total">全部评论&nbsp;<span id="total_comment_record" style="color:#4f7ae2"></span></div>
                <div id="passage_comment_edit">
                	<div id="passage_comment_avator">
                		<img id="comment_avator" src="/blog/Images/avator.jpg" alt="avator" />
                	</div>
                	<div id="passage_comment_editreal">
                		<textarea id="comment_textarea" placeholder="说点什么吧..."></textarea>
                		<div id="passage_comment_loginbutton">
                			<div>评论支持emoji表情😍</div>
                			<button id="qqloginbutton"  onclick="toLogin()">使用QQ登录</button>
                			<button style="display:none" id="commentSubmitbutton">发表评论</button>
                		</div>
                	</div>
                </div>
                	
                <!-- 评论区内容开始 -->
                <div id="passage_comment_content">
                
                </div>
                <!-- 评论区内容结束 -->
                <div id="comment_pageIndex">
                	<div id="pageIndex_container">
	                	
                	</div>
                </div>
            </div>
        </div>
        <div id="bottom_toolBar">
        	<div id="pageInfo"><font color="#FF0000">For my dear Crystal</font><br/><br/>Designed and implemented by IL MARE<br/><br/>May all your wishes come true - IL MARE</div>
        </div>
	</body>
</html>