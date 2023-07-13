package jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJms
public class MessageController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/send")
    public void sendMessage(@RequestBody String messageText) {
        jmsTemplate.send("YOUR_QUEUE_NAME", new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage();
                message.setText(messageText);
                return message;
            }
        });
    }


    @GetMapping("recv")
    String recv(){
        try{
            return jmsTemplate.receiveAndConvert("QMQUEUE").toString();
        }catch(JmsException ex){
            ex.printStackTrace();
            return "FAIL";
        }
    }
}

