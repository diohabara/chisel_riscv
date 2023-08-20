# chisel_riscv

## setup

Develop inside [Devcontainer](https://code.visualstudio.com/docs/devcontainers/containers).

or

use Dockerfile.

```bash
docker build . -t riscv/mycpu
docker run -it -v $PWD/mycpu:/src riscv/mycpu
```

## references

- <https://gihyo.jp/book/2021/978-4-297-12305-5>
- <https://github.com/chadyuu/riscv-chisel-book>
