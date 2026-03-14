package gcd
package type_function

import scala.compiletime.*, ops.int.*

/*
 * Match Types（类型级递归计算）
 *
 * 核心机制：
 * - type GCD[A, B] <: Int = B match { ... } 定义纯类型级别的欧几里得算法
 * - 递归类型别名：利用类型匹配实现 GCD[B, A % B]
 * - 当 B = 0 时终止递归，返回 A
 * - constValue[GCD[A, B]] 把最终计算出的类型转换为运行时值
 *
 * 类型计算过程（以 GCD[48, 18] 为例）：
 * GCD[48, 18]  →  GCD[18, 48 % 18]  →  GCD[18, 12]
 * GCD[18, 12]  →  GCD[12, 6]        →  GCD[6, 0]
 * GCD[6, 0]    →  6
 *
 * 适用场景与优势：
 * - 完全在类型层面完成计算，可用于更复杂的类型证明
 * - 类型推导能力极强，可与其他类型特性（如 given、inline）无缝结合
 * - 提供最强的编译期类型安全性
 * - 适合库作者设计类型安全的 API
 */

type GCD[A <: Int, B <: Int] <: Int = B match
    case 0 => A
    case _ => GCD[B, A % B]

object GCD:
    inline def apply[A <: Int, B <: Int]: Int = constValue[GCD[A, B]]

object Test extends GCDTestSpec:
    inline def computeGCD[A <: Int, B <: Int]: Int = GCD[A, B]

@main
def runTest = Test.runTests()
