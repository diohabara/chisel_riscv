#include <stdio.h>

int main()
{
  unsigned int size = 5; // elements to compute
  unsigned int vl;

  while (0 < size)
  {
    asm volatile("vsetvli %0, %1, e32, m1"
                 : "=r"(vl)
                 : "r"(size));
    size -= vl;
    // do something with vl
  }
  asm volatile("unimp");
  return 0;
}