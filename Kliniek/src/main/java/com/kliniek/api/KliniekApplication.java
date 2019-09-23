package com.kliniek.api;

import com.kliniek.api.model.Usuario;
import com.kliniek.api.repository.UsuarioRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KliniekApplication {

    public static void main(String[] args) {

        SpringApplication.run(KliniekApplication.class, args);
        
    }

}
