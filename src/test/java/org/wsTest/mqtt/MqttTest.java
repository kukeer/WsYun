package org.wsTest.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MqttTest {

		Logger log  = LoggerFactory.getLogger(getClass());

		private static final String SERVER_URL = "tcp://120.79.49.63:1883";

		private static List<String> TOPList = new ArrayList<String>(Arrays.asList("wether","car","topic"));

		public static String clientId;

		private MqttClient mqttClient;
		public MqttTopic mqttTopic;
		private MqttTopic getMqttTopic;
		private String password = "pp";
		private  String username= "uu";
		private MqttMessage mqttMessage;

		public MqttTest() throws MqttException {
			clientId = "wether";
//			org.eclipse.paho.client.mqttv3.persis.
			mqttClient = new MqttClient(SERVER_URL,clientId,new MemoryPersistence());
			String topic = "wether";
			connect(topic);
		}

		public MqttTest(String topic ,String clientid) throws MqttException {
			TOPList.add(topic);
			mqttClient = new MqttClient(SERVER_URL,clientid,new MemoryPersistence());
			this.createOn(topic);
		}

		public void connect(String topic){
			MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
			mqttConnectOptions.setCleanSession(false);
			mqttConnectOptions.setUserName(username);
			mqttConnectOptions.setPassword(password.toCharArray());
			mqttConnectOptions.setConnectionTimeout(20);
			mqttConnectOptions.setKeepAliveInterval(20);

			try {
				mqttClient.setCallback(new PushCallback());
				mqttClient.connect(mqttConnectOptions);
				getMqttTopic = mqttClient.getTopic(topic);
			} catch (MqttException e) {
				e.printStackTrace();
			}

		}

		public static void publish(MqttTopic topic,MqttMessage message) throws MqttException {
			MqttDeliveryToken publish_token = topic.publish(message);
			publish_token.waitForCompletion();
		}
		public String createOn(String topic) throws MqttException {
			MqttTest mqttTest = new MqttTest();
			mqttTest.mqttMessage = new MqttMessage();
			mqttTest.mqttMessage.setQos(2);
			mqttTest.mqttMessage.setRetained(false);
			mqttTest.mqttMessage.setPayload("裝配成功".getBytes());
			mqttTest.publish(mqttTest.getMqttClient().getTopic(topic),mqttTest.mqttMessage);
			return "builder success";
		}
		public static void main(String[] args) throws MqttException {
			MqttTest test = new MqttTest();
			MqttTest test1 = new MqttTest("尼玛","jk123");
			test1.mqttMessage = new MqttMessage();
			test1.mqttMessage.setQos(2);
			test1.mqttMessage.setRetained(false);
			test1.mqttMessage.setPayload("裝備成功".getBytes());
//			test1.publish(test.getMqttTopic,test.mqttMessage);
			
		}
	public static String getServerUrl() {
		return SERVER_URL;
	}

	public static List<String> getTOPList() {
		return TOPList;
	}

	public static String getClientId() {
		return clientId;
	}

	public MqttClient getMqttClient() {
		return mqttClient;
	}

	public MqttTopic getMqttTopic() {
		return mqttTopic;
	}

	public MqttTopic getGetMqttTopic() {
		return getMqttTopic;
	}

	public String getPassword() {
		return password;
	}

	public MqttMessage getMqttMessage() {
		return mqttMessage;
	}
}
