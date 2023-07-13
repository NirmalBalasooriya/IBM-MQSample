package jms;

import com.ibm.mq.jms.MQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Random;

@Service
public class PaymentService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "QMQUEUE")
    public void receive(Message message) throws JMSException {
        // receive message
        TextMessage textMessage = (TextMessage) message;
        final String textMessageBody = textMessage.getText();
        System.out.println("### 2 ### Payment Service received message: "+textMessageBody+" with correlationId: "+ textMessage.getJMSCorrelationID());

        // some random logic to complete the order (80% of times it returns true)
        Random random = new Random();
        String orderCompleted = (random.nextInt(101) >= 20) ? "payment_ok" : "payment_failed";

        // send response
        System.out.println("### 3 ### Payment Service sending response");
        MQQueue orderRequestQueue = new MQQueue("RESPONSE");
        jmsTemplate.convertAndSend(orderRequestQueue, orderCompleted, responseMessage -> {
            responseMessage.setJMSCorrelationID(textMessage.getJMSCorrelationID());
            return responseMessage;
        });
    }
}
