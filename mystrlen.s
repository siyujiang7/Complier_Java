	.file	"mystrlen.c"
	.text
.globl mystrlen
	.type	mystrlen, @function
mystrlen:
movq $0, %rax
while:
movq $0, %rdx 
mov (%rdi), %cl
cmp %rcx, %rdx
je EndLoop
movq $1, %rcx 
addq %rcx, %rax
addq %rcx, %rdi 
jmp while
EndLoop:
ret
