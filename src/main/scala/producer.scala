package app.etf

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import java.util.Properties
import scala.io.Source

object producer extends App{

    // See Logger
  import org.apache.log4j.{Level, Logger}
  Logger.getLogger("org.apache.kafka").setLevel(Level.DEBUG)

    val topic = "livee"
    val brokers = "ip-172-31-13-101.eu-west-2.compute.internal:9092,ip-172-31-3-80.eu-west-2.compute.internal:9092,ip-172-31-5-217.eu-west-2.compute.internal:9092,ip-172-31-9-237.eu-west-2.compute.internal:9092" // Kafka broker

    val props = new Properties()
    props.put("bootstrap.servers", brokers)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    props.put(ConsumerConfig.GROUP_ID_CONFIG, "new-consumer-group") //Using a new group ID
    // Set the auto.offset.reset configuration here
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest") // Options: "earliest", "latest", "none"

    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)

    // Read data from CSV file
    val filePath = "UKUSSeptBatch/Ian/ADRE.csv"
    val csvData = Source.fromFile(filePath).getLines()

    // Simulate real-time data by sending each row to Kafka
    csvData.drop(1).foreach { line => // drop(1) skips the header

      // Split the CSV line by comma to extract the values
      val Array(stringValue, value1, value2) = line.split(",")

      // Create a formatted message for the Kafka record
      val formattedValue = s"StringValue: $stringValue, Value1: $value1, Value2: $value2"

      // Create a new ProducerRecord with partition 0, the string value as the key, and formattedValue as the value
      val record = new ProducerRecord[String, String](topic, 0, stringValue, formattedValue)

      // Send the record to the Kafka topic
      producer.send(record)
      println(s"Sent: $formattedValue")

      // Simulate delay for real-time streaming
      Thread.sleep(10000)
    }

    producer.close()

}
