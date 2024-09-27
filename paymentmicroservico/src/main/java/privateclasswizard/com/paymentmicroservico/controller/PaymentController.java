package privateclasswizard.com.paymentmicroservico.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import privateclasswizard.com.paymentmicroservico.model.Payment;

@RestController
@RequestMapping("/pagar")
public class PaymentController {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "payments"; // Nome do tópico Kafka

    public PaymentController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<String> processPayment(@RequestBody Payment payment) {
        // Simular o processamento do pagamento
        String confirmationMessage = String.format("Pagamento de %.2f para o pedido %s processado com sucesso!", 
                                                    payment.getAmount(), 
                                                    payment.getOrderId());

        // Enviar a mensagem para o Kafka
        kafkaTemplate.send(TOPIC, payment);

        return ResponseEntity.ok(confirmationMessage);
    }
}
