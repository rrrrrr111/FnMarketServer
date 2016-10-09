package ru.roman.example

import java.util.Arrays

object HelloScala {

  def main(args: Array[String]) {
    println("hello scala".r)

    val mult: (Int, Int) => Int = (y, x) => y
    val first: ((String, AnyVal)) => _ = (pair: (String, AnyVal)) => pair._1
    val person: (String, Int) = ("Anne", 27)
    val name = first {
      person
    }

    println {
      <hello>
        <d>{first}</d>
        <d></d>
      </hello>
    }

    val set = Set(1, 2, 3, 4)
    println {
      set.map {
        _ / 2
      }
    }
    println {
      Arrays.toString(Array(1, 2, 3))
    }
    println {
      """as
        |
        |
        |as""".stripMargin.eq("")}



  }
}
