package app.etf

object consumer extends  App{
  import org.apache.kafka.clients.consumer.{KafkaConsumer, ConsumerRecords, ConsumerConfig}
  import java.time.Duration
  import java.util.Properties
  import scala.collection.JavaConverters._

  object ConsumerApp {
    def main(args: Array[String]): Unit = {
      val topic = "liveDemo"
      val brokers = "ip-172-31-13-101.eu-west-2.compute.internal:9092, ip-172-31-5-217.eu-west-2.compute.internal:9092, ip-172-31-9-237.eu-west-2.compute.internal:9092"

      val props = new Properties()
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group")
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")

      val consumer = new KafkaConsumer[String, String](props)
      consumer.subscribe(java.util.Collections.singletonList(topic))

      while (true) {
        val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofMillis(100))
        for (record <- records.asScala) {
          println(s"Consumed: Key = ${record.key()}, Value = ${record.value()}")
        }
      }

      // Remember to close the consumer
      consumer.close()
    }
  }


}
