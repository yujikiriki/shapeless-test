package co.s4n

import org.scalatest.FunSuite

class PathDependantTypes extends FunSuite {

  test("Path") {

    def userWithEmail[T](nEmail: T): User = new User {
      override type Email = T
      override val email: Email = nEmail
    }

    // La instancia ya existe!!!
    val user: User = userWithEmail("y@s4n.co")
    assert("y@s4n.co" === user.email)
  }

}

trait User {
  type Email

  val email: Email
}

