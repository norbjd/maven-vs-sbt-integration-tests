package com.norbjd.mavenvssbt

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ScalaJUnitRunnerUnitTest extends FlatSpec with Matchers {

  "A unit test" should "run tests from classes" in {
    val mainClass = new MainClass()
    val pathToClass = mainClass.getClassName()
    pathToClass should startWith("file")
    println(System.getProperty("java.class.path"))
  }

}