package riscvtests

import chisel3._
import chisel3.util._
import common.Consts._
import chisel3.util.experimental.loadMemoryFromFile

// addr: 32bit memory address
// inst: 32bit instruction
class ImemPortIo extends Bundle {
  val addr = Input(UInt(WORD_LEN.W))
  val inst = Output(UInt(WORD_LEN.W))
}

class DmemPortIo extends Bundle {
  val addr  = Input(UInt(WORD_LEN.W))
  val rdata = Output(UInt(WORD_LEN.W))
  val wen   = Input(Bool())
  val wdata = Input(UInt(WORD_LEN.W))
}

class Memory extends Module {
  val io = IO(new Bundle {
    val imem = new ImemPortIo()
    val dmem = new DmemPortIo()
  })

  // 8bit x 16384 registers as memory
  // to make PC count-up 4
  // 1address for 8bit and 32bit = 4address
  val mem = Mem(16384, UInt(8.W))
  // change hex file for test
  loadMemoryFromFile(mem, "src/hex/sw.hex")
  // 4 x 8bit data -> 1 x 32bit date
  io.imem.inst := Cat(
    mem(io.imem.addr + 3.U(WORD_LEN.W)),
    mem(io.imem.addr + 2.U(WORD_LEN.W)),
    mem(io.imem.addr + 1.U(WORD_LEN.W)),
    mem(io.imem.addr)
  )
  io.dmem.rdata := Cat(
    mem(io.dmem.addr + 3.U(WORD_LEN.W)),
    mem(io.dmem.addr + 2.U(WORD_LEN.W)),
    mem(io.dmem.addr + 1.U(WORD_LEN.W)),
    mem(io.dmem.addr)
  )

  when(io.dmem.wen) {
    mem(io.dmem.addr)                   := io.dmem.wdata(7, 0)
    mem(io.dmem.addr + 1.U(WORD_LEN.W)) := io.dmem.wdata(15, 8)
    mem(io.dmem.addr + 2.U(WORD_LEN.W)) := io.dmem.wdata(23, 16)
    mem(io.dmem.addr + 3.U(WORD_LEN.W)) := io.dmem.wdata(31, 24)
  }
}
