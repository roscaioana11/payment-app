package ro.fasttrackit.homework13.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.homework13.payment.model.PaymentEntity;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentEntity, String> {
}
