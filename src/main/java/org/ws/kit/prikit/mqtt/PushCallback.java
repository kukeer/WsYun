package org.ws.kit.prikit.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ws.kit.MqqtServer;

public class PushCallback implements MqttCallback {
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    MqqtServer mqqtServer;
    
    @Override
    public void connectionLost(Throwable cause) {
        //斷開 可以重連
    	mqqtServer.connect();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        //
        logger.info("接收消息主題 "+topic);
        logger.info("接收到的Qos "+message.getQos());
        logger.info("消息的內容"+new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
