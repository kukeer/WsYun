package org.ws.kit;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ws.kit.prikit.mqtt.PushCallback;

//@Component
public class MqqtServer {
	
	Logger log  = LoggerFactory.getLogger(getClass());
	
	private static final String SERVER_URL = "tcp://120.79.49.63:1883";
	
	public static String clientId="mini";
	
	private MqttClient mqttClient;
	
//	public MqttTopic mqttTopic;
	
	private String password = "evil";
	
	private String  username = "god";
	
	private MqttMessage mqttMessage;
	
	private MqqtServer mqttServer;
	
	public MqqtServer(){
		init();
	}
	
	private void init() {
		try {
			mqttClient = new MqttClient(SERVER_URL,clientId,new MemoryPersistence());
			connect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public boolean connect(){
		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		mqttConnectOptions.setCleanSession(false);
		mqttConnectOptions.setUserName(username);
		mqttConnectOptions.setPassword(password.toCharArray());
		mqttConnectOptions.setConnectionTimeout(20);
		mqttConnectOptions.setKeepAliveInterval(20);
		mqttClient.setCallback(new PushCallback());
		try {
			mqttClient.connect(mqttConnectOptions);
		} catch (MqttException e) {
			String message = e.getMessage();
			log.warn("发生了错误  错误信息为{} ",message);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public void publish(String topic,String message) throws MqttPersistenceException, MqttException{
		if(mqttServer==null){
			synchronized (MqqtServer.class) {
				if(mqttServer==null){
					mqttServer= new MqqtServer();
				}
			}
		}
		mqttServer.mqttMessage = new MqttMessage();
		mqttServer.mqttMessage.setQos(1);
		mqttServer.mqttMessage.setRetained(false);
		MqttTopic mqttTopic = mqttClient.getTopic(topic);
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setQos(2);
		mqttMessage.setRetained(false);
		mqttMessage.setPayload(message.getBytes());
		MqttDeliveryToken token = mqttTopic.publish(mqttMessage);
//		try {
//			mqttClient.publish(topic, mqttMessage);
//		} catch (MqttException e) {
//			e.printStackTrace();
//		}
		token.waitForCompletion();
        System.out.println("Token is complete:" + token.isComplete());
	}
	
}
