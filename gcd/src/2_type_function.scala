package gcd
package type_function

import scala.compiletime.*, ops.int.*

// 使用类型函数实现计算最大公约数

type GCD[A <: Int, B <: Int] <: Int = B match
  case 0 => A
  case _ => GCD[B, A % B]

object GCD:
  inline def apply[A <: Int, B <: Int]: Int = constValue[GCD[A, B]]

@main
def test =
  assert(GCD[48, 18] == 6)
  assert(GCD[54, 24] == 6)
  assert(GCD[17, 13] == 1)
  assert(GCD[0, 5] == 5)
  assert(GCD[0, 0] == 0)
