<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="common/head.jsp"%>
<title>消息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${basePath}js/modules/activemq/mq.js"></script>
<script type="text/javascript">
$(function(){
	//初始化
	mq.init();
});
</script>
</head>
<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="text-center">发送消息</h3>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label for="name">文本框</label>
					<textarea class="form-control" rows="3" id="msg"></textarea>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<a href="javascript:void(0);" id="queue" class="btn btn-primary">单点发送</a>
						<a href="javascript:void(0);" id="topic" class="btn btn-primary">订阅发送</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>