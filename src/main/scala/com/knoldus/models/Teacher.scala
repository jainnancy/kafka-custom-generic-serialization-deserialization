package com.knoldus.models

case class Teacher(id: Int, name: String, department: String) {

  override def toString: String = {
    s"$id: $name: $department"
  }

}
