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
  /** B-format
    *
    * beq rs1, rs2, offset
    *
    * if (x[rs1] == x[rs2]) pc += sext[imm_b]
    */
  val BEQ = BitPat("b?????????????????000?????1100011")

  /** B-format
    *
    * bne rs1, rs2, offset
    *
    * if (x[rs1] != x[rs2]) pc += sext[imm_b]
    */
  val BNE = BitPat("b?????????????????001?????1100011")

  /** B-format
    *
    * blt rs1, rs2, offset
    *
    * if (x[rs1] < x[rs2]) pc += sext[imm_b]
    */
  val BLT = BitPat("b?????????????????100?????1100011")

  /** B-format
    *
    * bge rs1, rs2, offset
    *
    * if (x[rs1] >= x[rs2]) pc += sext[imm_b]
    */
  val BGE = BitPat("b?????????????????101?????1100011")

  /** unsigned BLT */
  val BLTU = BitPat("b?????????????????110?????1100011")

  /** unsigned BGE */
  val BGEU = BitPat("b?????????????????111?????1100011")

  // jump
  /** J-format
    *
    * jal rd, offset
    *
    * This cycle: x[rd] = pc + 4
    *
    * next cycle: x[rd] = pc + sext[imm_j]
    */
  val JAL = BitPat("b?????????????????????????1101111")

  /** I-format
    *
    * jalr rd, rs1, offset
    *
    * This cycle: x[rd] = pc + 4
    *
    * next cycle: x[rd] = pc + (x[rs1] + sext[imm_i]) & ~1
    */
  val JALR = BitPat("b?????????????????000?????1100111")

  // immediate load
  /** U-format
    *
    * lui rd, imm_u
    *
    * x[rd] = imm_u
    *
    * LUI: load upper immediate
    */
  val LUI = BitPat("b?????????????????????????0110111")

  /** U-format
    *
    * auipc rd, imm_u
    *
    * x[rd] = pc + imm_u
    *
    * AUIPC: add upper immediate to pc
    */
  val AUIPC = BitPat("b?????????????????????????0010111")

  // CSR(Controll and Status Register)
  // CSR is a 12-bit register
  // CSR register examples
  // | CSR   | Name    | Description                                |
  // | 0x300 | mstatus | Machine status register(allow instruption) |
  // | 0x305 | mtvec   | Machine trap-handler base address          |
  // | 0x341 | mepc    | Machine exception program counter          |
  // | 0x342 | mcause  | Machine trap cause                         |
  // CSR comes with both write/read to the register
  /** I-format
    *
    * csrrw rd, csr, rs1
    *
    * CSRs[csr] <- x[rs1]
    *
    * x[rd] <- CSRs[csr]
    *
    * CSRRW: read and write
    */
  val CSRRW = BitPat("b?????????????????001?????1110011")

  /** I-format
    *
    * csrrwi rd, csr, imm_z
    *
    * CSRs[csr] <- uext(imm_z)
    *
    * x[rd] <- CSRs[csr]
    *
    * CSRRWI: read and write immediate
    */
  val CSRRWI = BitPat("b?????????????????101?????1110011")

  /** I-format
    *
    * csrrs rd, csr, rs1
    *
    * CSRs[csr] <- CSRs[csr] | x[rs1]
    *
    * x[rd] <- CSRs[csr]
    *
    * CSRRS: read and set
    */
  val CSRRS = BitPat("b?????????????????010?????1110011")

  /** I-format
    *
    * csrrsi rd, csr, imm_z
    *
    * CSRs[csr] <- CSRs[csr] | uext(imm_z)
    *
    * x[rd] <- CSRs[csr]
    *
    * CSRRSI: read and set immediate
    */
  val CSRRSI = BitPat("b?????????????????110?????1110011")

  /** I-format
    *
    * csrrc rd, csr, rs1
    *
    * CSRs[csr] <- CSRs[csr] & ~x[rs1]
    *
    * x[rd] <- CSRs[csr]
    *
    * CSRRC: read and clear
    */
  val CSRRC = BitPat("b?????????????????011?????1110011")

  /** I-format
    *
    * csrrc rd, csr, rs1
    *
    * CSRs[csr] <- CSRs[csr] & ~uext(imm_z)
    *
    * x[rd] <- CSRs[csr]
    *
    * CSRRCI: read and clear immediate
    */
  val CSRRCI = BitPat("b?????????????????111?????1110011")

  // exception
  /** I-format
    *
    * ecall
    *
    * write value to the mcause (0x342) register of CSR
    *
    * | value | meaning         |
    * |:------|:----------------|
    * | 8     | user mode       |
    * | 9     | supervisor mode |
    * | 10    | hypervisor mode |
    * | 11    | machine mode    |
    */
  // In our case, we only use machine mode
  // Then, jump to trap vector address in mtvec (0x305) of CSR
  // syscall is written in the trap vector
  val ECALL = BitPat("b00000000000000000000000001110011")

  // vector
  // VSETVLI stands for vector set vector length immediate
  val VSETVLI = BitPat("b?????????????????111?????1010111")
  val VLE     = BitPat("b000000100000?????????????0000111")
  val VSE     = BitPat("b000000100000?????????????0100111")
  val VADDVV  = BitPat("b0000001??????????000?????1010111")

  // custom
  val PCNT = BitPat("b000000000000?????110?????0001011")
}
