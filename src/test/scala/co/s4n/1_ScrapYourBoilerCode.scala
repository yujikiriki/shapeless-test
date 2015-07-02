package co.s4n

import org.scalatest.FunSuite
import shapeless._
import syntax.typeable._

object inc extends Poly1 {
  implicit def caseInt = at[Int](_ + 1)
}

class ScrapYourBoilerCode extends FunSuite {
  val nested = List(Option(List(Option(List(Option(23))))))

  test("Big brother") {
    val result = everywhere(inc)(nested)
    assert(List(Option(List(Option(List(Option(24)))))) === result)
  }

  test("Safe casting") {
    val a: Any = List(Vector("foo", "bar", "baz"), Vector("wibble"))
    val lvs: Option[List[Vector[String]]] = a.cast[List[Vector[String]]]
    assert(lvs.isDefined == true)

    val lvi: Option[List[Vector[Int]]] = a.cast[List[Vector[Int]]]
    assert(lvi.isEmpty == true)

  }
}
