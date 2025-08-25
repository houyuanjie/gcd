package gcd
package implicit_function_aux

import scala.compiletime.*, ops.int.*

// 使用 Aux 模式实现计算最大公约数

trait GCD[A <: Int, B <: Int]:
  type R <: Int

object GCD:
  type Aux[A <: Int, B <: Int, _R <: Int] = GCD[A, B] { type R = _R }

  def apply[A <: Int, B <: Int](using inst: GCD[A, B], r: ValueOf[inst.R]): Int =
    r.value

  given base[A <: Int]: Aux[A, 0, A] = new GCD[A, 0]:
    type R = A

  given inductive[A <: Int, B <: Int, Next <: Int](using next: Aux[B, A % B, Next]): Aux[A, B, Next] = new GCD[A, B]:
    type R = Next

@main
def test =
  assert(GCD[48, 18] == 6)
  assert(GCD[54, 24] == 6)
  assert(GCD[17, 13] == 1)
  assert(GCD[0, 5] == 5)
  assert(GCD[0, 0] == 0)
