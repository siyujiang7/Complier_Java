.data
.comm array, 400
.comm n, 4
.text
format1:
.string "sort\n"
format2:
.string "%d"
format3:
.string "sorted:\n"
format4:
.string "%d\n"

.global main
main:
movq $format1, %rdi
movq $0, %rax
movq $0, %r13
call printf
movq $array, %rbx
movq $1, %rax

while:
movq $n, %rsi
movq $0, %rax
movq $format2, %rdi
call scanf
movq $1, %rdx
cmp %rax, %rdx
jne Sort
movq $n, %rsi
movq (%rsi), %rsi
movq %rsi, (%rbx)
movq $4, %rdx
addq %rdx, %rbx
movq $1, %rdx
addq %rdx, %r13
jmp while

Sort:
movq $array, %rbx
movq $1, %r15
movq $0, %r14

Start:
cmp %r14, %r13
je End

Swap:
cmp %r15, %r13
je AfterSwap
mov (%rbx), %dl
movq $4, %rdi
addq %rdi, %rbx
mov (%rbx), %dh
cmp %dh, %dl
jg Greater
jle Less

Less:
movq $1, %rdx
addq %rdx, %r15
jmp Swap
AfterSwap:
movq $array,%rbx
movq $1, %rdx
addq %rdx, %r14
movq %rdx, %r15
jmp Start

Greater:
mov (%rbx), %ch
movq $4, %rdi
subq %rdi, %rbx
mov (%rbx), %cl
mov %ch, (%rbx)
addq %rdi, %rbx
mov %cl, (%rbx)
movq $1, %rdx
addq %rdx, %r15
jmp Swap

End:
movq $0, %rax
movq $format3, %rdi
call printf
movq $0, %r15
movq $array, %rbx

Print:
cmp %r15, %r13
je End
movq $format4, %rdi
movq (%rbx), %rsi
movq $0, %rax
call printf
movq $4, %rdx
addq %rdx, %rbx
movq $1, %rdx
addq %rdx, %r15
jmp Print
