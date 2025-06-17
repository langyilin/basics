package cn.nero.lang.core.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {


    @Autowired
    private WebClient.Builder webClientBuilder;


    private WebClient.Builder productWebClientBuilder() {
        return webClientBuilder.baseUrl("http://localhost:8081");
    }


    public Mono<String> products() {
        return productWebClientBuilder()
                .build()
                .get()
                .uri("/products")
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("Fallback response"));
    }


}
