package com.modules.websocket.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.modules.sys.entity.User;
import com.modules.websocket.consts.Constants;
import com.modules.websocket.service.TextMessageHandler;

@Controller
@RequestMapping("/chat")
public class MessageCTRL {
	
	/**
	 * 跳转到聊天页面
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String gotoChat(User user,HttpServletRequest request){
		HttpSession session = request.getSession();
        session.setAttribute(Constants.DEFAULT_SESSION_USERNAME, user.getUsername());
		return "/chat";
	}
	
	@Bean
    public TextMessageHandler textMessageHandler() {
        return new TextMessageHandler();
    }

    @RequestMapping
    public String view() {
        return "message";
    }

    @RequestMapping("send")
    @ResponseBody
    public String send(HttpServletRequest request, @RequestParam("username") String username) {
        TextMessage message = new TextMessage(request.getParameter("message"));
        textMessageHandler().sendMessageToUser(username, message);
        return "true";
    }

}
