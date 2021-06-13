package ro.fasttrackit.homework13.payment.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ro.fasttrackit.homework13.payment.dto.PaymentDto;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;

@Slf4j
@Component
public class PaymentApiClient {
    private final String baseUrl;
    private final RestTemplate rest;

    public PaymentApiClient(@Value("${payment.service.location:NOT_DEFINED}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.rest = new RestTemplate();
    }

    public List<PaymentDto> getAllStudents() {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/payments")
                .toUriString();
        log.info("Getting all students: " + url);
        return rest.exchange(url, GET, new HttpEntity<>(null), new ParameterizedTypeReference<List<PaymentDto>>() {
        }).getBody();
    }

    public PaymentDto patchPayment(PaymentApiClient payment) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/payments")
                .toUriString();

        return rest.patchForObject(url, payment, PaymentDto.class);
    }
}
