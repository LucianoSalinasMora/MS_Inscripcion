package SigueTuCarrera.Inscripciones.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.services.estudiantes.url:http://localhost:8001}")
    private String estudiantesUrl;

    @Value("${app.services.malla.url:http://localhost:8002}")
    private String mallaUrl;

    @Bean
    public WebClient estudiantesWebClient() {
        return WebClient.builder().baseUrl(estudiantesUrl).build();
    }

    @Bean
    public WebClient mallaWebClient() {
        return WebClient.builder().baseUrl(mallaUrl).build();
    }
}