	.text
.globl fact
	.type	fact, @function
fact:
	movq $1, %rax
while:
	movq $0, %rdx
	cmp %rdi, %rdx
	je EndLoop
	imulq %rdi, %rax
	decq %rdi
	jmp while
EndLoop:
	cvtsi2sd %rax, %xmm0
	ret
