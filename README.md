# GCD - Scala 3 编译时编程示例

一个展示 Scala 3 编译时编程特性的示例项目，实现编译时计算最大公约数（GCD）。

## 项目简介

本项目使用四种不同的 Scala 3 编译时技术实现 GCD 计算：

- **内联函数** (`1_inline_function.scala`) - 使用 `inline` 和 `constValue`
- **类型函数** (`2_type_function.scala`) - 使用 Match Types
- **隐式函数** (`3_implicit_function.scala`) - 使用 `given`/`using`
- **辅助隐式函数** (`4_implicit_function_aux.scala`) - 使用 Aux 模式

## 环境要求

- Scala 3.8.2
- Mill 1.1.3

## 构建

编译项目：

```sh
./mill gcd.compile
```

## 运行测试

由于项目包含多个测试主类，需要分别运行：

```sh
./mill gcd.runMain gcd.inline_function.runTest
./mill gcd.runMain gcd.type_function.runTest
./mill gcd.runMain gcd.implicit_function.runTest
./mill gcd.runMain gcd.implicit_function_aux.runTest
```

## 代码格式化

使用 scalafmt 格式化代码：

```sh
./mill gcd.reformat
```

检查代码格式：

```sh
./mill gcd.checkFormat
```

## 项目结构

```
gcd/
├── src/
│   ├── 1_inline_function.scala       # 内联函数实现
│   ├── 2_type_function.scala         # 类型函数实现
│   ├── 3_implicit_function.scala     # 隐式函数实现
│   └── 4_implicit_function_aux.scala # Aux 模式实现
├── build.mill                        # Mill 构建定义
└── .scalafmt.conf                    # 代码格式化配置
```

## 技术要点

- 所有计算在**编译时**完成，无运行时开销
- 使用 `scala.compiletime.*` 和 `ops.int.*`
- 类型安全的编译时计算
- 递归必须在编译时终止

## 开发规范

- 使用 Scala 3 语法
- 遵循 `.scalafmt.conf` 格式化规则
- 提交前运行 `./mill gcd.reformat`
