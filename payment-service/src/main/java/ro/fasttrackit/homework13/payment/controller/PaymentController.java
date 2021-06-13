package ro.fasttrackit.homework13.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.homework13.exceptions.ResourceNotFoundException;
import ro.fasttrackit.homework13.payment.dto.PaymentDto;
import ro.fasttrackit.homework13.payment.model.mappers.PaymentMappers;
import ro.fasttrackit.homework13.payment.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;
    private final PaymentMappers mapper;

    @GetMapping
    List<PaymentDto> getAllPayments(){
        return mapper.toApi(service.getAllPayments());
    }

    @PatchMapping("{paymentId}")
    ResponseEntity<PaymentDto> patchPayment(@PathVariable String paymentId, @RequestBody JsonPatch patch){
        try{
            return ResponseEntity.ok(mapper.toApi(service.patchPayment(paymentId,patch)));
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
