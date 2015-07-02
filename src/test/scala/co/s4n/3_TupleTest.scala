package co.s4n

import org.scalatest.FunSuite

import shapeless._
import poly._

object option extends (Id ~> Option) {
  def apply[T](t: T) = Option(t)
}

class TupleTest extends FunSuite {
  import shapeless.syntax.std.tuple._

  test("Tuples as HList") {
    val tuple: (Int, String, Boolean) = (23, "foo", true)
    assert(23 === tuple.head)
    assert(("foo", true) === tuple.tail)
    assert(("foo", true) === tuple.drop(1))
    assert(23 +: ("foo", true) === tuple)
    assert((23, "foo") :+ (true) === tuple)
    assert((Some(23), Some("foo"), Some(true)) === tuple.map(option))
  }
}
