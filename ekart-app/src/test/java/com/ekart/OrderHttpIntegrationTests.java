package com.ekart;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderHttpIntegrationTests {

    @LocalServerPort
    private int port;

    @Test
    void adminCanListOrdersOverRealHttp() throws Exception {
        String credentials = Base64.getEncoder().encodeToString(
                "admin@ekart.com:password123".getBytes(StandardCharsets.UTF_8));
        HttpResponse<String> response = HttpClient.newHttpClient().send(
                HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:" + port + "/v1/orders"))
                        .header("Authorization", "Basic " + credentials)
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("customerName");
    }
}
