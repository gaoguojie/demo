package com.example.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/08
 */
public class MsgProducter {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", "127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094");
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i = 0; i < 5; i++){
            //同步方式发送消息
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test",0,Integer.toString(i),i + "-megTwo");
            Future<RecordMetadata> result = producer.send(producerRecord);
            //等待消息发送成功的同步阻塞方法
            RecordMetadata metadata = result.get();
            System.out.println("同步方法发送消息结果：" + "topic-" + metadata.topic() + "|partition-" + metadata.partition()
            + "|offset-" + metadata.offset());

        }

        producer.close();

    }
}
