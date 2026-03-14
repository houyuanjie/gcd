package gcd

trait GCDTestSpec:
    inline def computeGCD[A <: Int, B <: Int]: Int

    inline def runTests(): Unit =
        assert(computeGCD[48, 18] == 6)
        assert(computeGCD[54, 24] == 6)
        assert(computeGCD[17, 13] == 1)

        assert(computeGCD[0, 0] == 0)
        assert(computeGCD[42, 0] == 42)
        assert(computeGCD[0, 42] == 42)

        assert(computeGCD[42, 42] == 42)
        assert(computeGCD[1, 42] == 1)
        assert(computeGCD[42, 1] == 1)

        assert(computeGCD[123, 456] == 3)
        assert(computeGCD[96, 64] == 32)
        assert(computeGCD[100, 75] == 25)
        assert(computeGCD[1071, 462] == 21)
        assert(computeGCD[252, 105] == 21)

        println("All tests passed!")
