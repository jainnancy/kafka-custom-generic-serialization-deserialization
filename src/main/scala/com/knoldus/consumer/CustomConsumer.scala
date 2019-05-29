package com.knoldus.consumer

import java.util.{Collections, Properties}

import com.knoldus.ApplicationConfig._
import com.knoldus.models.{Student, Teacher}
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.log4j.Logger

import scala.collection.JavaConverters._

class CustomConsumer {

  val log = Logger.getLogger(this.getClass)
  val props = new Properties()
  val props1 = new Properties()
  val config = ConfigFactory.load()

  props.put("bootstrap.servers", bootstrapServer)
  props.put("key.deserializer", keyDeserializer)
  props.put("value.deserializer", valueDeserializer)
  props.put("group.id", group_id_1)
  props.put("enable.auto.commit", "false")
  props.put("auto.offset.reset", offset)


  props1.put("bootstrap.servers", bootstrapServer)
  props1.put("key.deserializer", keyDeserializer)
  props1.put("value.deserializer", valueDeserializer)
  props1.put("group.id", group_id_2)
  props1.put("enable.auto.commit", "false")
  props1.put("auto.offset.reset", offset)

  val studentConsumer = new KafkaConsumer[String, Student](props)
  val teacherConsumer = new KafkaConsumer[String, Teacher](props1)

  /**
    * This method will read data from given topic.
    * @param studentTopic String
    * @param teacherTopic String
    */
  def readFromKafka(studentTopic: String, teacherTopic: String) {
    studentConsumer.subscribe(Collections.singletonList(studentTopic))
    teacherConsumer.subscribe(Collections.singletonList(teacherTopic))
    while (true) {
      val studentRecords: ConsumerRecords[String, Student] = studentConsumer.poll(5000)
      val teacherRecords: ConsumerRecords[String, Teacher] = teacherConsumer.poll(5000)
      for (studentRecord <- studentRecords.asScala) {
        log.info(s"received message in Student consumer-\n key: ${studentRecord.key} value: ${studentRecord.value} \n")
      }
      for (teacherRecord <- teacherRecords.asScala) {
        log.info(s"received message in Teacher consumer-\n key: ${teacherRecord.key} value: ${teacherRecord.value} \n")
      }
    }
  }

}

object ConsumerMain extends App {
  val studentTopic = studentTopicName
  val teacherTopic = teacherTopicName
  (new CustomConsumer).readFromKafka(studentTopic, teacherTopic)
}
