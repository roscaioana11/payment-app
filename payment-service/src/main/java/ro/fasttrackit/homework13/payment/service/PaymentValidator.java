package ro.fasttrackit.homework13.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.homework13.exceptions.ValidationException;
import ro.fasttrackit.homework13.payment.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class PaymentValidator {
    private final PaymentRepository repository;

    private Optional<ValidationException> exists(String paymentId) {
        return repository.existsById(paymentId)
                ? empty()
                : Optional.of(new ValidationException(List.of("Payment with id " + paymentId + " doesn't exist")));
    }

    public void validateExistsOrThrow(String paymentId) {
        exists(paymentId).ifPresent(ex -> {
            throw ex;
        });
    }
}
