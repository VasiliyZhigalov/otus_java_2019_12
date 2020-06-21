package com.vasiliyzhigalov.controllers;

import com.vasiliyzhigalov.domain.User;
import com.vasiliyzhigalov.services.frontendservises.FrontendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AdminWebSocketController {
    private final FrontendService frontendService;
    private static final Logger logger = LoggerFactory.getLogger(AdminWebSocketController.class);
    private final SimpMessagingTemplate template;

    public AdminWebSocketController(FrontendService frontendService, SimpMessagingTemplate template) {
        this.frontendService = frontendService;
        this.template = template;
    }

    @MessageMapping("/admin.addUser")
    public void addUser(User user) {
        frontendService.addUser(user, (id) -> saveUser(id));
    }
    @MessageMapping("/admin.allUsers")
    public void allUsers() {
        frontendService.findAll(users -> getAllUsers(users));
    }

    private void saveUser(Long id) {
        this.template.convertAndSend("/topic/response/addUser", id);
    }

    private void getAllUsers(List<User> users) {
        this.template.convertAndSend("/topic/response/allUsers", users);
    }

}
