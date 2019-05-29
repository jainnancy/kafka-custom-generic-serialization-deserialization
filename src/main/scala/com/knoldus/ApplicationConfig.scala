package com.knoldus

import com.typesafe.config.ConfigFactory

object ApplicationConfig {
  val config = ConfigFactory.load()
  val bootstrapServer = config.getString("BOOTSTRAP_SERVER")
  val keyDeserializer = config.getString("DESERIALIZER")
  val keySerializer = config.getString("SERIALIZER")
  val valueDeserializer = config.getString("VALUE_DESERIALIZER")
  val valueSerializer = config.getString("VALUE_SERIALIZER")
  val group_id_1 = config.getString("GROUP_ID_1")
  val group_id_2 = config.getString("GROUP_ID_2")
  val offset = config.getString("OFFSET")
  val studentTopicName = config.getString("STUDENT_TOPIC")
  val teacherTopicName = config.getString("TEACHER_TOPIC")
}
