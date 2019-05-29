package com.knoldus.producer

import java.util.Properties

import com.knoldus.models.{Student, Teacher}
import com.knoldus.ApplicationConfig._
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.log4j.Logger

class CustomProducer {

  val log = Logger.getLogger(this.getClass)
  val props = new Properties()
  val config = ConfigFactory.load()

  props.put("bootstrap.servers", bootstrapServer)
  props.put("key.serializer", keySerializer)
  props.put("value.serializer", valueSerializer)

  val studentProducer = new KafkaProducer[String, Student](props)
  val teacherProducer = new KafkaProducer[String, Teacher](props)

  /**
    * This method will write data to given topic.
    * @param studentTopic String
    * @param teacherTopic String
    */
  def writeToKafka(studentTopic: String, teacherTopic: String) {
    for (i <- 1 to 100)
      studentProducer.send(new ProducerRecord[String, Student](studentTopic, i.toString, Student(i, s"name-$i")))
    for (i <- 1 to 10)
      teacherProducer.send(new ProducerRecord[String, Teacher](teacherTopic, i.toString, Teacher(i, s"Teacher-$i", "IT")))
    log.info(s"Record has been written to kafka.\n")
    studentProducer.close()
    teacherProducer.close()
  }

}

object ProducerMain extends App {
  val studentTopic = studentTopicName
  val teacherTopic = teacherTopicName
  (new CustomProducer).writeToKafka(studentTopic, teacherTopic)
}
