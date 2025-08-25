package gcd
package inline_function

import scala.compiletime.*, ops.int.*

// 使用内联函数实现计算最大公约数

inline def gcd[A <: Int, B <: Int]: Int =
  inline constValue[B] match
    case 0 => constValue[A]
    case _ => gcd[B, A % B]

@main
def test =
  assert(gcd[48, 18] == 6)
  assert(gcd[54, 24] == 6)
  assert(gcd[17, 13] == 1)
  assert(gcd[0, 5] == 5)
  assert(gcd[0, 0] == 0)
