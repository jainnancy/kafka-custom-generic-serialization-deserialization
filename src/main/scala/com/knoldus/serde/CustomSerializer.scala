package com.knoldus.serde

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.util

import com.knoldus.models.Student
import org.apache.kafka.common.serialization.Serializer

class CustomSerializer[T] extends Serializer[T] {

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {
  }

  override def serialize(topic: String, data: T): Array[Byte] = {

    try {
      val byteOutputStream = new ByteArrayOutputStream()
      val objectSerialized = new ObjectOutputStream(byteOutputStream)
      objectSerialized.writeObject(data)
      byteOutputStream.toByteArray
    }
    catch {
      case ex: Exception => throw ex
    }
  }

  override def close(): Unit = {
  }

}
