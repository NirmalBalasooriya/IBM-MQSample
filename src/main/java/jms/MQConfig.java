package jms;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

@Configuration
public class MQConfig {

    @Value("${ibm.mq.host}")
    private String host;

    @Value("${ibm.mq.port}")
    private int port;

    @Value("${ibm.mq.channel}")
    private String channel;

    @Value("${ibm.mq.queueManager}")
    private String queueManager;

    @Value("${ibm.mq.username}")
    private String username;

    @Value("${ibm.mq.password}")
    private String password;

    @Bean
    public MQConnectionFactory mqConnectionFactory() {
        MQConnectionFactory factory = new MQConnectionFactory();
        try {
            factory.setHostName(host);
            factory.setPort(port);
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            factory.setQueueManager(queueManager);
            factory.setChannel(channel);
            factory.setCCSID(1208); // Use the appropriate CCSID for your environment
            factory.setStringProperty(WMQConstants.USERID, username);
            factory.setStringProperty(WMQConstants.PASSWORD, password);
        } catch (Exception e) {
            // Handle exception
        }
        return factory;
    }
}
