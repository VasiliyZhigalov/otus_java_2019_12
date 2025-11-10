package com.vasiliyzhigalov.controllers;

import com.vasiliyzhigalov.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.vasiliyzhigalov.services.frontendservises.FrontendService;

import java.util.List;

@Slf4j
@Controller
public class AdminWebSocketController {
    private final FrontendService frontendService;
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
        log.info("get all users request");
        frontendService.findAll(users -> getAllUsers(users));
    }

    private void saveUser(Long id) {
        this.template.convertAndSend("/topic/response/addUser", id);
    }

    private void getAllUsers(List<User> users) {
        this.template.convertAndSend("/topic/response/allUsers", users);
    }

}
