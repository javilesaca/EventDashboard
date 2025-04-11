package pro.javilesaca.eventdashboard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Event Dashboard API",
                version = "v1",
                description = "API para gestionar eventos de logueo y ver estadísticas de la actividad del sistema.",
                contact = @Contact(name = "Javier Lesaca", email = "javier@example.com", url = "https://javier.com")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Servidor local")
        }
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Event Dashboard API")
                        .version("v1")
                        .description("API para gestionar eventos, loguear actividades y visualizar estadísticas.")
                        .contact(new io.swagger.v3.oas.models.info.Contact().name("Javier Lesaca").email("javier@example.com").url("https://javier.com"))
                );
    }
}
