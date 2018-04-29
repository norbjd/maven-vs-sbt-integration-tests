package com.norbjd.mavenvssbt

import org.scalatest._

class ScalaNoJunitRunnerIntegrationTest extends FlatSpec with Matchers {

  "An integration test" should "run tests from JARs" in {
    val mainClass = new MainClass()
    val pathToClass = mainClass.getClassName()
    pathToClass should startWith("jar")
  }

}