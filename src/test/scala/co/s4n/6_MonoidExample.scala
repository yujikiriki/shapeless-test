package co.s4n

import org.scalatest.FunSuite
import shapeless._

class MonoidExamples extends FunSuite {
  import MonoidSyntax._

  case class Foo(i: Int, s: String)
  case class Bar(b: Boolean, s: String, d: Double)

  test("Composition") {
    val f = Foo(13, "foo") |+| Foo(23, "bar")
    assert(f == Foo(36, "foobar"))
  }

  test("Composition 2") {
    implicit val barInstance = Monoid[Bar]

    val b = Bar(true, "foo", 1.0) |+| Bar(false, "bar", 3.0)
    assert(b == Bar(true, "foobar", 4.0))
  }
}

/**
 * Pedagogic subset of the Scalaz monoid
 */
trait Monoid[T] {
  def zero: T
  def append(a: T, b: T): T
}

object Monoid extends ProductTypeClassCompanion[Monoid] {
  def mzero[T](implicit mt: Monoid[T]) = mt.zero

  implicit def booleanMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    def zero = false
    def append(a: Boolean, b: Boolean) = a || b
  }

  implicit def intMonoid: Monoid[Int] = new Monoid[Int] {
    def zero = 0
    def append(a: Int, b: Int) = a + b
  }

  implicit def doubleMonoid: Monoid[Double] = new Monoid[Double] {
    def zero = 0.0
    def append(a: Double, b: Double) = a + b
  }

  implicit def stringMonoid: Monoid[String] = new Monoid[String] {
    def zero = ""
    def append(a: String, b: String) = a + b
  }

  object typeClass extends ProductTypeClass[Monoid] {
    def emptyProduct = new Monoid[HNil] {
      def zero = HNil
      def append(a: HNil, b: HNil) = HNil
    }

    def product[F, T <: HList](mh: Monoid[F], mt: Monoid[T]) = new Monoid[F :: T] {
      def zero = mh.zero :: mt.zero
      def append(a: F :: T, b: F :: T) = mh.append(a.head, b.head) :: mt.append(a.tail, b.tail)
    }

    def project[F, G](instance: => Monoid[G], to: F => G, from: G => F) = new Monoid[F] {
      def zero = from(instance.zero)
      def append(a: F, b: F) = from(instance.append(to(a), to(b)))
    }
  }
}

trait MonoidSyntax[T] {
  def |+|(b: T): T
}

object MonoidSyntax {
  implicit def monoidSyntax[T](a: T)(implicit mt: Monoid[T]): MonoidSyntax[T] = new MonoidSyntax[T] {
    def |+|(b: T) = mt.append(a, b)
  }
}
