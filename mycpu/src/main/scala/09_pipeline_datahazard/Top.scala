package pipeline_datahazard

import chisel3._
import chisel3.util._
import common.Consts._

// Q: Moduleってなんだっけ？
// A: これは、Chiselのモジュールを継承しているのか。
class Top extends Module {
  val io = IO(new Bundle {
    val exit = Output(Bool())
    val gp   = Output(UInt(WORD_LEN.W))
  })
  val core   = Module(new Core())
  val memory = Module(new Memory())
  // <> は左辺と右辺の信号を接続する(一括)
  core.io.imem <> memory.io.imem
  core.io.dmem <> memory.io.dmem
  // := は右辺から左辺へ(->)信号を接続する
  io.exit := core.io.exit
  io.gp   := core.io.gp
}
