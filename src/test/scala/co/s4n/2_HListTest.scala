package co.s4n

import org.scalatest.FunSuite
import shapeless._

case class TestADT(attr01: String, attr02: Int)

object size extends Poly1 {
  implicit def caseInt = at[Int](x => 4)
  implicit def caseString = at[String](_.length)
  implicit def caseTestADT = at[TestADT](x => 1)
}

class HListTest extends FunSuite {

  test("PolyFunction map test") {
    val hlist = 10 :: "Hello" :: TestADT("attr01", 1) :: HNil
    val list = hlist.map(size).toList
    assert(10 === list.sum)
  }

  test("case class binding") {
    val gen = Generic[TestADT]

    val t: TestADT = TestADT("attr01", 1)
    val attrs = "attr01" :: 1 :: HNil

    val from: TestADT = gen.from(attrs)
    assert(t === from)
    val to = gen.to(t)
    assert(attrs === to)
  }
}

object TypeTest {
  trait Fruit
  case class Apple() extends Fruit
  case class Pear() extends Fruit

  type FFFF = Fruit :: Fruit :: Fruit :: Fruit :: HNil
  type APAP = Apple :: Pear :: Apple :: Pear :: HNil
  type PPPP = Pear :: Pear :: Pear :: Pear :: HNil

  val a: Apple = Apple()
  val p: Pear = Pear()

  val apap: APAP = a :: p :: a :: p :: HNil
  val pppp: PPPP = p :: p :: p :: p :: HNil
  val ffff: FFFF = apap // APAP <: FFFF
}