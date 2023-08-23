# chisel_riscv

## Setup

```bash
direnv allow .
```

## Build

Develop inside [Devcontainer](https://code.visualstudio.com/docs/devcontainers/containers)

or

use Dockerfile

```bash
docker build . -t riscv/mycpu
docker run -it -v $PWD/mycpu:/mycpu riscv/mycpu
```

or

use existing image

```bash
docker pull yutaronishiyama/riscv-chisel-book
docker run -it -v $PWD/mycpu:/mycpu yutaronishiyama/riscv-chisel-book
```

## Format

```bash
scalafmt
```

## Test

In Docker

### Basic test

Change file path of Memory.scala

```bash
cd /mycpu
sbt "testOnly fetch.HexTest"
sbt "testOnly decode.HexTest"
sbt "testOnly lw.HexTest"
sbt "testOnly sw.HexTest"
```

### `riscv-tests`

change the starting address in `/opt/riscv/riscv-tests/env/p/link.ld`:

```bash
SECTIONS
{
  . = 0x00000000; // from 0x80000000
  ...
}
```

Run the following commands:

```bash
cd /opt/riscv/riscv-tests
autoconf
./configure --prefix=/src/target
make
make install
```

ELF and dump files will be generated in `/src/target/share/riscv-tests/isa/`. We will be using

- `rv32ui-p-` (for user-level instructions)
- `rv32um-p-` (for machine-level instructions)

Convert ELF file into BIN files:
  
```bash
mkdir /mycpu/src/riscv
cd /mycpu/src/riscv
riscv64-unknown-elf-objcopy -O binary /src/target/share/riscv-tests/isa/rv32ui-p-add rv32ui-p-add.bin
od -An -tx1 -w1 -v rv32ui-p-add.bin >> rv32ui-p-add.hex
```

Run

```bash
sbt "testOnly riscvtests.RiscvTest"
```

Automate making `hex` files

```bash
cd /mycpu/src/shell
./tohex.sh
./riscv_tests.sh
```

The results will be in `mycpu/results`

## References

- <https://gihyo.jp/book/2021/978-4-297-12305-5>
- <https://github.com/chadyuu/riscv-chisel-book>
