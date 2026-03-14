package gcd
package implicit_function

import scala.compiletime.*, ops.int.*

/*
 * Type Class（given/using）+ Low Priority 优先级控制
 *
 * 核心机制：
 * - trait GCD[A, B] 定义类型类，保存计算结果
 * - LowPriorityGCD trait 存放归纳规则（优先级较低）
 * - object GCD 继承 LowPriority 并提供高优先级 base case
 * - 使用 summonInline 在 inline 上下文中安全解析 given
 * - 巧妙利用优先级规则避免 % 0 错误
 *
 * 隐式解析过程（以 GCD[48, 18] 为例）：
 * 1. apply 调用 summonInline[GCD[48, 18]]
 * 2. 先匹配 object GCD 中的 base（仅 B=0 有效，不匹配）
 * 3.  fallback 到 LowPriorityGCD 的 inductive
 * 4. 递归展开直到 GCD[6, 0]，命中 base case
 * 5. 逐层回溯得到最终值 6
 *
 * 适用场景与优势：
 * - 经典 Type Class 模式在编译时计算的完美应用
 * - 通过 trait 分层精确控制隐式优先级
 * - 代码结构清晰，易于扩展新规则
 * - 适合需要“默认实现 + 可覆盖”的库设计
 */

trait GCD[A <: Int, B <: Int]:
    val value: Int

trait LowPriorityGCD:
    given inductive[A <: Int, B <: Int](using next: GCD[B, A % B]): GCD[A, B] with
        val value: Int = next.value

object GCD extends LowPriorityGCD:
    inline def apply[A <: Int, B <: Int]: Int = summonInline[GCD[A, B]].value

    given base[A <: Int](using a: ValueOf[A]): GCD[A, 0] with
        val value: Int = a.value

object Test extends GCDTestSpec:
    inline def computeGCD[A <: Int, B <: Int]: Int = GCD[A, B]

@main
def runTest = Test.runTests()
