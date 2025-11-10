package com.vasiliyzhigalov;

import com.vasiliyzhigalov.serverSocket.ServerSocket;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MSMain implements CommandLineRunner {
    private final ServerSocket serverSocket;

    public MSMain(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) {
        SpringApplication.run(MSMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        serverSocket.go();
    }
}
