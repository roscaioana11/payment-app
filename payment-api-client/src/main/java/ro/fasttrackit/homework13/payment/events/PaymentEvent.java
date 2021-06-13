package ro.fasttrackit.homework13.payment.events;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import ro.fasttrackit.homework13.payment.dto.PaymentDto;

@Value
@Builder
@JsonDeserialize(builder = PaymentEvent.PaymentEventBuilder.class)
public class PaymentEvent {
    PaymentDto payment;
    Status status;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PaymentEventBuilder{
    }
}
