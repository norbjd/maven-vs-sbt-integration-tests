package com.norbjd.mavenvssbt

class MainClass {

  def getClassName(): String = {
    this.getClass.getResource(this.getClass.getSimpleName + ".class").toString
  }

}
