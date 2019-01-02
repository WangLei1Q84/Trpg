package com.brett.controller;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.brett.Entity.User_Message;
import com.brett.service.User_MessageService;

@ServerEndpoint("/community/{ro_user}")
public class WSP2PController {
	private static final User_MessageService service;
	private static Logger log = LoggerFactory.getLogger(WSP2PController.class);
	 
    static {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ((User_MessageService) ctx.getBean("user_MessageServiceImpl"));
    }
 
    private static final Map<Integer, CopyOnWriteArraySet<WSP2PController>> rooms = new HashMap<>();
 
    private Session session;
 
    private Integer userId;
 
    private Integer roomId;
 
    @OnOpen
    public void onOpen(@PathParam(value = "ro_user") String ro_user, Session session) {
        this.session = session;
        String[] param = ro_user.split("-");
        this.roomId = Integer.parseInt(param[0]);
        this.userId = Integer.parseInt(param[1]);
        CopyOnWriteArraySet<WSP2PController> friends = rooms.get(roomId);
        if (friends == null) {
            synchronized (rooms) {
                if (!rooms.containsKey(roomId)) {
                    friends = new CopyOnWriteArraySet<>();
                    rooms.put(roomId, friends);
                }
            }
        }
        friends.add(this);
    }
 
    @OnClose
    public void onClose() {
        CopyOnWriteArraySet<WSP2PController> friends = rooms.get(roomId);
        if (friends != null) {
            friends.remove(this);
        }
    }
 
    @OnMessage
    public void onMessage(final String message, Session session) {
        //新建线程来保存用户聊天信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                service.save(new User_Message(0, userId, message, roomId, new Date()));
            }
        }).start();
 
 
        CopyOnWriteArraySet<WSP2PController> friends = rooms.get(roomId);
        if (friends != null) {
            for (WSP2PController item : friends) {
                item.session.getAsyncRemote().sendText(message);
            }
        }
    }
 
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误" + new Date());
        error.printStackTrace();
    }
}
