package gcd
package implicit_function_aux

import scala.compiletime.*, ops.int.*

/*
 * Aux Pattern + summonFrom（类型成员传递）
 *
 * 核心机制：
 * - trait GCD[A, B] 使用抽象类型成员 type R <: Int 保存结果
 * - Aux 类型别名把 R 提升为类型参数，解决类型成员在隐式搜索中无法确定的问题
 * - LowPriorityGCD 存放归纳规则，object GCD 提供 base case
 * - apply 使用 summonFrom + summonInline 在 inline 上下文中安全提取结果
 *
 * 隐式解析过程（以 GCD[48, 18] 为例）：
 * 1. apply 中的 summonFrom 查找 Aux[48, 18, r]
 * 2. base case（B=0）不匹配 → 进入 LowPriority 的 inductive
 * 3. 递归搜索 Aux[6, 0, 6]，命中 base
 * 4. 逐层回溯：Aux[12, 6, 6] → Aux[18, 12, 6] → Aux[48, 18, 6]
 * 5. summonInline[ValueOf[6]] 得到最终值
 *
 * 适用场景与优势：
 * - 当类型类需要把计算结果作为类型成员传递时，Aux 是标准解法
 * - 结合 summonFrom 可实现更灵活的隐式选择逻辑
 * - 类型层面携带信息的能力最强，适合复杂类型编程场景
 * - 是 Scala 3 中“类型级计算 + 值提取”的高级模式
 */

trait GCD[A <: Int, B <: Int]:
    type R <: Int

trait LowPriorityGCD:
    type Aux[A <: Int, B <: Int, _R <: Int] = GCD[A, B] { type R = _R }

    given inductive[A <: Int, B <: Int, Next <: Int](using next: Aux[B, A % B, Next]): Aux[A, B, Next] = new GCD[A, B]:
        type R = Next

object GCD extends LowPriorityGCD:
    inline def apply[A <: Int, B <: Int]: Int = summonFrom:
        case given Aux[A, B, r] =>
            summonInline[ValueOf[r]].value

    given base[A <: Int]: Aux[A, 0, A] = new GCD[A, 0]:
        type R = A

object Test extends GCDTestSpec:
    inline def computeGCD[A <: Int, B <: Int]: Int = GCD[A, B]

@main
def runTest = Test.runTests()
