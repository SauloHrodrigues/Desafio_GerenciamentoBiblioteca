package com.desafio.db.GerenciamentoBiblioteca.configuracoes;

import org.springframework.context.annotation.Configuration;
import java.util.List;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiSwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Votação")
                        .version("v1")
                        .description(
                                "O presente projeto é um crud para gestão de uma biblioteca, contando com os " +
                                        "modulos: Autor, Livro, Locatário e aluguel."
                        )
                        .contact(new Contact()
                                .name("Saulo Henrique Rodrigues")
                                .email("saulo.rodrigues@db.tec.br"))
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local")
                ));
    }
}
