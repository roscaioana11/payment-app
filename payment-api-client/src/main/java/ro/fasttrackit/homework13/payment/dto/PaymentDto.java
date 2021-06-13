package ro.fasttrackit.homework13.payment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import ro.fasttrackit.homework13.payment.events.Status;

@Value
@Builder
@JsonDeserialize(builder = PaymentDto.PaymentDtoBuilder.class)
public class PaymentDto {
    String id;
    String invoiceId;
    Status status;
    double amountPayable;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PaymentDtoBuilder{
    }
}
