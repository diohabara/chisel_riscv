package common

import chisel3.util._

/** RISC-V Instruction Representation format
  *
  * R format: funct7[31:25] | rs2[24:20] |rs1[19:15] | funct3[14:12] | rd[11:7] \| opcode[6:0]
  *
  * I format: imm[31:20] | rs1[19:15] | funct3[14:12] | rd[11:7] | opcode[6:0]
  *
  * S format: imm[31:25] | rs2[24:20] | rs1[19:15] | funct3[14:12] | imm[11:7] | opcode[6:0]
  *
  * B format: imm[31:31] | imm[30:25] | imm[24:20] | rs2[19:15] | rs1[14:12] | imm[11:7] | imm[6:0] | funct3[5:3] | opcode[2:0]
  *
  * U format: imm[31:12] | rd[11:7] | opcode[6:0]
  *
  * J format: imm[31:20] | imm[19:12] | imm[11:1] | opcode[0]
  */

object Instructions {
  // load, store
  /** I-format
    *
    * lw rd, offset(rs1)
    *
    * x[rd] = M[x[rs1] + sext[imm_i]]
    *
    * x: register
    *
    * M: memory
    *
    * x[rs1]: base address
    *
    * sext[imm_i]: sign-extended immediate
    */
  val LW = BitPat("b?????????????????010?????0000011")

  /** S-format
    *
    * sw rs2, offset(rs1)
    *
    * M[x[rs1] + sext[imm_s]] = x[rs2]
    *
    * x: register
    *
    * M: memory
    *
    * x[rs1]: base address
    *
    * sext[imm_s]: sign-extended immediate
    *
    * x[rs2]: data to be stored
    */
  val SW = BitPat("b?????????????????010?????0100011")

  // add/subtract
  /** R-format
    *
    * add rd, rs1, rs2
    *
    * x[rd] = x[rs1] + x[rs2]
    */
  val ADD = BitPat("b0000000??????????000?????0110011")

  /** I-format
    *
    * addi rd, rs1, imm_i
    *
    * x[rd] = x[rs1] + sext[imm_i]
    */
  val ADDI = BitPat("b?????????????????000?????0010011")

  /** R-format
    *
    * sub rd, rs1, rs2
    *
    * x[rd] = x[rs1] - x[rs2]
    */
  val SUB = BitPat("b0100000??????????000?????0110011")

  // logical operation
  /** R-format
    *
    * and rd, rs1, rs2
    *
    * x[rd] = x[rs1] & x[rs2]
    */
  val AND = BitPat("b0000000??????????111?????0110011")

  /** R-format
    *
    * or rd, rs1, rs2
    *
    * x[rd] = x[rs1] | x[rs2]
    */
  val OR = BitPat("b0000000??????????110?????0110011")

  /** R-format
    *
    * xor rd, rs1, rs2
    *
    * x[rd] = x[rs1] ^ x[rs2]
    */
  val XOR = BitPat("b0000000??????????100?????0110011")

  /** I-format
    *
    * andi rd, rs1, imm_i
    *
    * x[rd] = x[rs1] & sext[imm_i]
    */
  val ANDI = BitPat("b?????????????????111?????0010011")

  /** I-format
    *
    * ori rd, rs1, imm_i
    *
    * x[rd] = x[rs1] | sext[imm_i]
    */
  val ORI = BitPat("b?????????????????110?????0010011")

  /** I-format
    *
    * xori rd, rs1, imm_i
    *
    * x[rd] = x[rs1] ^ sext[imm_i]
    */
  val XORI = BitPat("b?????????????????100?????0010011")

  // shift
  /** R-format
    *
    * sll rd, rs1, rs2
    *
    * x[rd] = x[rs1] << x[rs2]
    */
  val SLL = BitPat("b0000000??????????001?????0110011")

  /** R-format
    *
    * srl rd, rs1, rs2
    *
    * x[rd] = x[rs1] >> x[rs2]
    */
  val SRL = BitPat("b0000000??????????101?????0110011")

  /** R-format
    *
    * sra rd, rs1, rs2
    *
    * x[rd] = x[rs1] >> x[rs2]
    */
  val SRA = BitPat("b0100000??????????101?????0110011")

  /** I-format
    *
    * slli rd, rs1, imm_i
    *
    * x[rd] = x[rs1] << sext[imm_i]
    */
  val SLLI = BitPat("b0000000??????????001?????0010011")

  /** I-format
    *
    * srli rd, rs1, imm_i
    *
    * x[rd] = x[rs1] >> sext[imm_i]
    */
  val SRLI = BitPat("b0000000??????????101?????0010011")

  /** I-format
    *
    * srai rd, rs1, imm_i
    *
    * x[rd] = x[rs1] >> sext[imm_i]
    */
  val SRAI = BitPat("b0100000??????????101?????0010011")

  // compare
  /** R-format
    *
    * signed
    *
    * slt rd, rs1, rs2
    *
    * x[rd] = (x[rs1] < x[rs2]) ? 1 : 0
    */
  val SLT = BitPat("b0000000??????????010?????0110011")

  /** R-format
    *
    * unsigned
    *
    * sltu rd, rs1, rs2
    *
    * x[rd] = (x[rs1] < x[rs2]) ? 1 : 0
    */
  val SLTU = BitPat("b0000000??????????011?????0110011")

  /** I-format
    *
    * slti rd, rs1, imm_i
    *
    * x[rd] = (x[rs1] < sext[imm_i]) ? 1 : 0
    */
  val SLTI = BitPat("b?????????????????010?????0010011")

  /** I-format
    *
    * sltiu rd, rs1, imm_i
    *
    * x[rd] = (x[rs1] < sext[imm_i]) ? 1 : 0
    */
  val SLTIU = BitPat("b?????????????????011?????0010011")

  // conditional branch
  val BEQ  = BitPat("b?????????????????000?????1100011")
  val BNE  = BitPat("b?????????????????001?????1100011")
  val BLT  = BitPat("b?????????????????100?????1100011")
  val BGE  = BitPat("b?????????????????101?????1100011")
  val BLTU = BitPat("b?????????????????110?????1100011")
  val BGEU = BitPat("b?????????????????111?????1100011")

  // jump
  val JAL  = BitPat("b?????????????????????????1101111")
  val JALR = BitPat("b?????????????????000?????1100111")

  // immediate load
  val LUI   = BitPat("b?????????????????????????0110111")
  val AUIPC = BitPat("b?????????????????????????0010111")

  // CSR
  val CSRRW  = BitPat("b?????????????????001?????1110011")
  val CSRRWI = BitPat("b?????????????????101?????1110011")
  val CSRRS  = BitPat("b?????????????????010?????1110011")
  val CSRRSI = BitPat("b?????????????????110?????1110011")
  val CSRRC  = BitPat("b?????????????????011?????1110011")
  val CSRRCI = BitPat("b?????????????????111?????1110011")

  // exception
  val ECALL = BitPat("b00000000000000000000000001110011")

  // vector
  val VSETVLI = BitPat("b?????????????????111?????1010111")
  val VLE     = BitPat("b000000100000?????????????0000111")
  val VSE     = BitPat("b000000100000?????????????0100111")
  val VADDVV  = BitPat("b0000001??????????000?????1010111")

  // custom
  val PCNT = BitPat("b000000000000?????110?????0001011")
}
