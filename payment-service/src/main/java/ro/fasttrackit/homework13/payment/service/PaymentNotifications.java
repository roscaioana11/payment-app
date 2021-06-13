package ro.fasttrackit.homework13.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ro.fasttrackit.homework13.payment.events.PaymentEvent;
import ro.fasttrackit.homework13.payment.model.PaymentEntity;
import ro.fasttrackit.homework13.payment.model.mappers.PaymentMappers;

import static ro.fasttrackit.homework13.payment.events.Status.DONE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentNotifications {
    private final RabbitTemplate rabbit;
    private final PaymentMappers mapper;
    private final FanoutExchange paymentExchange;

    public void notifyPaymentUpdate(PaymentEntity payment) {
        PaymentEvent event = PaymentEvent.builder()
                .payment(mapper.toApi(payment))
                .status(DONE)
                .build();
        log.info("Sending event : " + event);
        rabbit.convertAndSend(paymentExchange.getName(), "", event);
    }
}
