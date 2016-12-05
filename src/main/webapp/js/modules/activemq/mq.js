var mq = {
		url:{
			queueURL: basePath + '/activemq/queueSender',
			topicURL: basePath + '/activemq/topicSender'
		},
		init:function(){
			$("#queue").click(function(){
				var msg = $('#msg').val();
				$.post(mq.url.queueURL,{message:msg},function(v){
					if(v=='suc'){
						alert("发送成功！");
					}
				});
			}),
			$('#topic').click(function(){
				var msg = $('#msg').val();
				$.post(mq.url.topicURL,{message:msg},function(v){
					if(v=='suc'){
						alert("发送成功！");
					}
				});
			});
		},
};