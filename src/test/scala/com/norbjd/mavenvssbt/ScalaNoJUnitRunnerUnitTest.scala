package com.norbjd.mavenvssbt

import org.scalatest._

class ScalaNoJUnitRunnerUnitTest extends FlatSpec with Matchers {

  "A unit test" should "run tests from classes" in {
    val mainClass = new MainClass()
    val pathToClass = mainClass.getClassName()
    pathToClass should startWith("file")
  }

}