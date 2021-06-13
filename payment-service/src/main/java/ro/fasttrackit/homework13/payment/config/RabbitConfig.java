package ro.fasttrackit.homework13.payment.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {
    private final ConnectionFactory connectionFactory;

    @Bean
    FanoutExchange invoiceExchange() {
        return new FanoutExchange("invoice.exchange");
    }

    @Bean
    FanoutExchange paymentExchange() {
        return new FanoutExchange("payment.exchange");
    }

    @Bean
    Queue invoiceQueue() {
        return new AnonymousQueue();
    }

    @Bean
    Queue paymentQueue() {
        return new AnonymousQueue();
    }

    @Bean
    Binding invoiceBinding(Queue invoiceQueue, FanoutExchange invoiceExchange) {
        return BindingBuilder.bind(invoiceQueue).to(invoiceExchange);
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
