package ro.fasttrackit.homework13.payment.model.mappers;

import org.springframework.stereotype.Component;
import ro.fasttrackit.homework13.payment.dto.PaymentDto;
import ro.fasttrackit.homework13.payment.model.PaymentEntity;
import ro.fasttrackit.homework13.utils.ModelMappers;

@Component
public class PaymentMappers implements ModelMappers<PaymentDto, PaymentEntity> {
    @Override
    public PaymentDto toApi(PaymentEntity source) {
        return PaymentDto.builder()
                .id(source.getId())
                .invoiceId(source.getInvoiceId())
                .status(source.getStatus())
                .amountPayable(source.getAmountPayable())
                .build();
    }

    @Override
    public PaymentEntity toDb(PaymentDto source) {
        return PaymentEntity.builder()
                .id(source.getId())
                .invoiceId(source.getInvoiceId())
                .status(source.getStatus())
                .amountPayable(source.getAmountPayable())
                .build();
    }
}
