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

```bash
cd /mycpu
sbt "testOnly fetch.HexTest"
```

## References

- <https://gihyo.jp/book/2021/978-4-297-12305-5>
- <https://github.com/chadyuu/riscv-chisel-book>
