package com.vasiliyzhigalov.config;

import com.vasiliyzhigalov.messagesystem.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageSystemConfig {
    @Bean
    MessageSystem getMessageSystem(){
        return new MessageSystemImpl();
    }

}
