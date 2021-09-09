# chisel_riscv

https://gihyo.jp/book/2021/978-4-297-12305-5

## setup

build Docker image

```bash
docker build . -t riscv/mycpu
```

create a container

```bash
docker run -it -v ~/chisel_riscv:/src riscv/mycpu
```
