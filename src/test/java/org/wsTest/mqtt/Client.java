package org.wsTest.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.ScheduledExecutorService;

public class Client {

    public static final String SERVER_URL = "tcp://120.79.49.63:1883";
    public static final String TOPIC = "尼玛";
    public static final String clientid = "client4";

    private MqttClient client;
    private MqttConnectOptions options;

    private String userName = "dd";
    private String password = "password";

    private ScheduledExecutorService scheduler;

    private void start() throws MqttException {
        client = new MqttClient(SERVER_URL,clientid,new MemoryPersistence());
        options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(20);
        options.setKeepAliveInterval(20);
        client.setCallback(new PushCallback());
        MqttTopic topic = client.getTopic(TOPIC);
        client.connect(options);
        int[] Qos = {1};
        String[] topic1 = {TOPIC};
        client.subscribe(topic1,Qos);
    }

    public static void main(String[] hhhh) throws MqttException {
        Client client = new Client();
        client.start();
    }
}
