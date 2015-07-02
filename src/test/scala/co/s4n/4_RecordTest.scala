package co.s4n

import org.scalatest.FunSuite
import shapeless._
import record._
import syntax.singleton._

class RecordTest extends FunSuite {

  test("") {
    val cars = ('make ->> Make("Tesla")) ::
      ('model ->> Model("S")) ::
      ('year ->> Year(2015)) :: HNil

    assert(Make("Tesla") === cars('make))
    assert(Model("S") === cars('model))
    assert(Year(2015) === cars('year))
    // cars('yuji) // No compila :D

  }
}

case class Make(brand: String)
case class Model(m: String)
case class Year(num: Int)
