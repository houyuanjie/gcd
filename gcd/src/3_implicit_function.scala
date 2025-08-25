package gcd
package implicit_function

import scala.compiletime.*, ops.int.*

// 使用 given/using 实现计算最大公约数

trait GCD[A <: Int, B <: Int]:
  val value: Int

object GCD:
  def apply[A <: Int, B <: Int](using inst: GCD[A, B]): Int = inst.value

  given base[A <: Int](using a: ValueOf[A]): GCD[A, 0] with
    val value: Int = a.value

  given inductive[A <: Int, B <: Int](using next: GCD[B, A % B]): GCD[A, B] with
    val value: Int = next.value

@main
def test =
  assert(GCD[48, 18] == 6)
  assert(GCD[54, 24] == 6)
  assert(GCD[17, 13] == 1)
  assert(GCD[0, 5] == 5)
  assert(GCD[0, 0] == 0)
