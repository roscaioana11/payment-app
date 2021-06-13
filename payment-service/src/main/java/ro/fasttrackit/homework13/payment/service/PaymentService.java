package ro.fasttrackit.homework13.payment.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fasttrackit.homework13.exceptions.ResourceNotFoundException;
import ro.fasttrackit.homework13.payment.model.PaymentEntity;
import ro.fasttrackit.homework13.payment.model.mappers.PaymentMappers;
import ro.fasttrackit.homework13.payment.repository.PaymentRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMappers mapper;
    private final ObjectMapper objectMapper;
    private final PaymentNotifications paymentNotifications;
    private final PaymentValidator validator;

    public List<PaymentEntity> getAllPayments(){
        return paymentRepository.findAll();
    }

    @Transactional
    public PaymentEntity addPayment(PaymentEntity paymentEntity) {
        return paymentRepository.save(paymentEntity);
    }


    public PaymentEntity patchPayment(String paymentId, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        validator.validateExistsOrThrow(paymentId);
        PaymentEntity dbPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find payment with id " + paymentId));

        JsonNode patchedPaymentJson = patch.apply(objectMapper.valueToTree(dbPayment));
        PaymentEntity patchedPayment = objectMapper.treeToValue(patchedPaymentJson, PaymentEntity.class);
        PaymentEntity savedEntity = paymentRepository.save(patchedPayment);
        paymentNotifications.notifyPaymentUpdate(savedEntity);
        return savedEntity;
    }
}
