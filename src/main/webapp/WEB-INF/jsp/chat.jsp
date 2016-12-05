<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  
            + request.getServerName() + ":" + request.getServerPort()  
            + path + "/";  
    pageContext.setAttribute("basePath",basePath);    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 根目录 -->
<script type="text/javascript" src="${basePath}js/modules/main/main.js"></script>
<script type="text/javascript" src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}js/sockjs.min.js"></script>  
<title>聊天页面</title>
<style type="text/css">
body {background: #E2E2E2; font-size:13.5px;}
div[message] { background: #5170ad; width: 400px; height: 600px; border: 1px solid #ccc; color: #fff; overflow-y: auto; }
ul, li { list-style-type: square;}
table tr td {font-size:12px;}
input, textarea {font-size: 12px; }
</style>
<script type="text/javascript">
var PATH = "${pageContext.request.contextPath}";
var websocket = null;
if (window['WebSocket']) {
	// ws://host:port/project/websocketpath
	websocket = new WebSocket("ws://" + window.location.host + PATH + '/websocket');
}
else{
	websocket = new new SockJS(PATH + '/websocket/socketjs');
}

websocket.onopen = function(event) {
	console.log('open', event);
};
websocket.onmessage = function(event) {
	console.log('message', event.data);
	$('div[message] > ul').append('<li>' + event.data + '</li>');
};
</script>
</head>
<body>
	<form action="${basePath}chat/send" method="post">
        <table>
            <tr>
                <td align="right">用户名:</td>
                <td><input name="username" type="text" style="width: 300px;"></td>
            </tr>
            <tr>
                <td align="right">内容:</td>
                <td><textarea name="message" style="width: 300px;"></textarea></td>
            </tr>
            <tr>
                <td colspan="2" align="center" ><input type="submit" /></td>
            </tr>
        </table>
    </form>
    <div message>
        <ul> </ul>
    </div>
</body>
</html>