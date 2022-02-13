# chisel_riscv

## setup

build Docker image

```bash
docker build . -t riscv/mycpu
```

create a container

```bash
docker run -it -v ~/chisel_riscv:/src riscv/mycpu
```

## references

- [RISC-V と Chisel で学ぶ はじめての CPU 自作――オープンソース命令セットによるカスタム CPU 実装への第一歩](https://gihyo.jp/book/2021/978-4-297-12305-5)
