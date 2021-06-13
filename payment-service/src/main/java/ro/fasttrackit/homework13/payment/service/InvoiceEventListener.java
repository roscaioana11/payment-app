package ro.fasttrackit.homework13.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ro.fasttrackit.homework13.invoice.dto.InvoiceDto;
import ro.fasttrackit.homework13.invoice.events.InvoiceEvent;
import ro.fasttrackit.homework13.payment.dto.PaymentDto;
import ro.fasttrackit.homework13.payment.model.PaymentEntity;

import static ro.fasttrackit.homework13.invoice.events.InvoiceEventType.ADDED;
import static ro.fasttrackit.homework13.payment.events.Status.PENDING;
import static ro.fasttrackit.homework13.payment.events.Status.REJECTED;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceEventListener {
    private final PaymentService paymentService;

    @RabbitListener(queues = "#{invoiceQueue.name}")
    void receiveInvoiceEvent(InvoiceEvent event) {
        if (event.getType() == ADDED) {
            PaymentEntity paymentEntity = paymentService.addPayment(cratePaymentEntity(event));
            log.info("Payment Created: " + paymentEntity);
        }
    }

    private PaymentEntity cratePaymentEntity(InvoiceEvent event) {
        PaymentEntity paymentEntity = new PaymentEntity();
        InvoiceDto invoiceDto = event.getInvoice();
        paymentEntity.setInvoiceId(invoiceDto.getId());
        paymentEntity.setAmountPayable(invoiceDto.getAmount());

        if(invoiceDto.getSender() == null || invoiceDto.getReceiver() == null){
            paymentEntity.setStatus(REJECTED);
        }else {
            paymentEntity.setStatus(PENDING);
        }
        return paymentEntity;
    }
}
