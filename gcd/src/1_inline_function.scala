package gcd
package inline_function

import scala.compiletime.*, ops.int.*

/*
 * Inline + constValue 编译时欧几里得算法
 *
 * 核心机制：
 * - inline def：让函数在调用点完全展开，消除运行时开销
 * - constValue[T]：从字面量类型中提取具体 Int 值
 * - 递归内联展开：通过 match + 递归调用实现欧几里得算法
 * - 类型约束 A <: Int, B <: Int 保证参数为编译时常量
 *
 * 展开过程（以 computeGCD[48, 18] 为例）：
 * 1. inline 展开 → constValue[18] match
 * 2. 非 0 分支 → 递归调用 gcd[18, 48 % 18]
 * 3. 继续展开：18→12→6→0
 * 4. 最终匹配 case 0 → 返回 constValue[48 % ...] = 6
 *
 * 适用场景与优势：
 * - 最简单直接的编译时计算方式
 * - 零运行时开销、代码体积小
 * - 适合所有参数在编译期已知的场景
 * - 缺点：不支持运行时变量
 */

inline def gcd[A <: Int, B <: Int]: Int =
    inline constValue[B] match
        case 0 => constValue[A]
        case _ => gcd[B, A % B]

object Test extends GCDTestSpec:
    inline def computeGCD[A <: Int, B <: Int]: Int = gcd[A, B]

@main
def runTest = Test.runTests()
