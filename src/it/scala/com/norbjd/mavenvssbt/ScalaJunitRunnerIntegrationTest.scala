package com.norbjd.mavenvssbt

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ScalaJunitRunnerIntegrationTest extends FlatSpec with Matchers {

  "An integration test" should "run tests from JARs" in {
    val mainClass = new MainClass()
    val pathToClass = mainClass.getClassName()
    pathToClass should startWith("jar")
    println(System.getProperty("java.class.path"))
  }

}