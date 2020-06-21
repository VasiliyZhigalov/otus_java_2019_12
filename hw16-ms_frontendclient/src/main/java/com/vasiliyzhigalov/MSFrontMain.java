package com.vasiliyzhigalov;

import com.vasiliyzhigalov.serverSocket.ServerSocket;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MSFrontMain implements CommandLineRunner {
    private final ServerSocket serverSocket;

    public MSFrontMain(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MSFrontMain.class);
        springApplication.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        serverSocket.go();
    }
}
