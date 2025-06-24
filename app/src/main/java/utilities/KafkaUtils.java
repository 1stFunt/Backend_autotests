package utilities;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Pattern;

public class KafkaUtils {

    private KafkaUtils() {
    }

    public static Properties newNewKafkaProps(String url) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "AQA-test-group");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");
        return props;
    }

    public static KafkaConsumer<String, String> createKafkaConsumer(Properties properties) {
        KafkaConsumer<String, String> consumer = null;
        try {
            consumer = new KafkaConsumer<>(properties);
        } catch (NullPointerException exception) {
            exception.getLocalizedMessage();
        }
        return consumer;
    }

    public static void subscribeConsumerToTopics(KafkaConsumer<String, String> consumer, Pattern topics) {
        if (consumer != null) {
            consumer.subscribe(topics);
        }
    }

    @SneakyThrows
    public static ConsumerRecords<String, String> getTopicsRecords(KafkaConsumer<String, String> consumer,
                                                                   int millis) {
        if (consumer == null) {
            return null;
        }
        return consumer.poll(Duration.ofMillis(millis));
    }

    public static boolean checkCreditUpdateRecord(ConsumerRecords<String, String> records,
                                                  String messageVal) {
        if (records.isEmpty()) {
            return false;
        }
        for (ConsumerRecord<String, String> record : records) {
            if (record.value().contains(messageVal)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkCreditUpdateRecord(ConsumerRecords<String, String> records,
                                                  String messageVal1, String messageVal2) {
        if (records.isEmpty()) {
            return false;
        }
        for (ConsumerRecord<String, String> record : records) {
            if (record.value().contains(messageVal1) && record.value().contains(messageVal2)) {
                return true;
            }
        }
        return false;
    }

    @SneakyThrows
    public static boolean checkRecordsHeaderVal(ConsumerRecords<String, String> records, String headerVal) {
        if (records.isEmpty()) {
            return false;
        }
        for (ConsumerRecord<String, String> record : records) {
            for (Header header : record.headers()) {
                Thread.sleep(1000);
                if (new String(header.value()).contains(headerVal)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void closeKafkaConsumer(KafkaConsumer<String, String> consumer) {
        if (consumer != null) {
            consumer.close();
        }
    }
}