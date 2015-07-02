package co.s4n

import org.scalatest.FunSuite
import shapeless._

case class Foo(x: Int, y: String)

class SortableTest extends FunSuite {

  test("Sorting case classes") {
    import GenericOrdering._
    implicitly[Ordering[Foo]]

    val a = Foo(9, "x")
    val b = Foo(1, "z")
    val c = Foo(9, "w")
    val foos = List(a, b, c)
    val sorted = foos.sorted
    assert(List(Foo(1, "z"), Foo(9, "w"), Foo(9, "x")) === sorted)
  }
}

object GenericOrdering {

  implicit def hnilOrdering: Ordering[HNil] = new Ordering[HNil] {
    def compare(a: HNil, b: HNil) = 0
  }

  implicit def hlistIsoOrdering[A, H <: HList]
  (implicit gen: Generic.Aux[A, H], oh: Ordering[H]): Ordering[A] = new Ordering[A] {
    def compare(a1: A, a2: A) = oh.compare(gen to a1, gen to a2)
  }

  implicit def hlistOrdering[H, T <: HList]
  (implicit oh: Ordering[H], ot: Ordering[T]): Ordering[H :: T] = new Ordering[H :: T] {
    def compare(a: H :: T, b: H :: T) = {
      val i = oh.compare(a.head, b.head)
      println(s"a = $a, b = $b, i = $i")
      if (i == 0)
        ot.compare(a.tail, b.tail)
      else
        i
    }
  }

}
