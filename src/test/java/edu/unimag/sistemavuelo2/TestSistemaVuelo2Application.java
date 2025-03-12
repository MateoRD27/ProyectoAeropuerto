package edu.unimag.sistemavuelo2;

import org.springframework.boot.SpringApplication;

public class TestSistemaVuelo2Application {

    public static void main(String[] args) {
        SpringApplication.from(SistemaVuelo2Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
