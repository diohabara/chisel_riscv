package sw

import chisel3._
import org.scalatest._
import chiseltest._
import riscvtests.Top

/** FlatSpecはshouldメソッドを提供 ChiselScalatestTesterはtestメソッドを提供
  */
class HexTest extends FlatSpec with ChiselScalatestTester {
  "mycpu" should "work through hex" in {
    test(new Top) { c =>
      while (!c.io.exit.peek().litToBoolean) {
        c.clock.step(1)
      }
    }
  }
}
