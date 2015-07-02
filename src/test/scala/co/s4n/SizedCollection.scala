package co.s4n

import org.scalatest.FunSuite
import shapeless._

class SizedCollection extends FunSuite {

  test("") {
    def row(cols: Seq[String]) =
      cols.mkString("""""", """,""", """""")

    def csv[N <: Nat](
      hdrs: Sized[Seq[String], N],
      rows: List[Sized[Seq[String], N]]) =
      row(hdrs) :: rows.map(row(_))

    val hdrs = Sized("Title", "Author")

    val rows = List(
      Sized("Types and Programming Languages", "Benjamin Pierce"),
      Sized("The Implementation of Functional Programming Languages", "Simon Peyton-Jones")
    )

    // hdrs and rows statically known to have the same number of columns
    val formatted = csv(hdrs, rows) // Compiles
    info(s"formatted = $formatted")

    // extendedHdrs has the wrong number of columns for rows
    val extendedHdrs = Sized("Title", "Author", "ISBN")
    //    val badFormatted = csv(extendedHdrs, rows) // Does not compile
  }

}
