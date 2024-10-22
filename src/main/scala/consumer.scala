package app.etf

import java.time.Duration
import java.util.{Collections, Properties}
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer, ConsumerRecord}
import org.apache.kafka.common.TopicPartition
import scala.jdk.CollectionConverters._

object consumer extends App {
  // Kafka configuration properties
  val props = new Properties()
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "ip-172-31-13-101.eu-west-2.compute.internal:9092,ip-172-31-3-80.eu-west-2.compute.internal:9092,ip-172-31-5-217.eu-west-2.compute.internal:9092,ip-172-31-9-237.eu-west-2.compute.internal:9092")
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "simple-consumer-group")
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest") // Read from the earliest offset

  // Create the Kafka consumer
  val consumer = new KafkaConsumer[String, String](props)

  // Specify the topic and partition
  val topic = "livee"
  val partition = 0 // Specify the partition you want to consume from
  val topicPartition = new TopicPartition(topic, partition)

  // Assign the consumer to the specified partition
  consumer.assign(Collections.singletonList(topicPartition))

  // Poll for new messages and print them
  println(s"Starting to consume messages from topic: '$topic', partition: $partition...")
  try {
    while (true) {
      // Poll for new records
      val records = consumer.poll(Duration.ofSeconds(1)) // Poll every second

      // Iterate over the records
      for (record <- records.asScala) {
        println(
          s"""
             |----------------------------------------
             |Key       : ${record.key()}
             |Value     : ${record.value()}
             |Partition : ${record.partition()}
             |Offset    : ${record.offset()}
             |----------------------------------------
             |""".stripMargin)
      }
    }
  } catch {
    case e: Exception =>
      e.printStackTrace()
  } finally {
    // Close the consumer when done
    consumer.close()
    println("Consumer closed.")
  }
}
