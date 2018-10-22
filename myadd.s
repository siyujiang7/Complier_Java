	.text
.globl add
	.type add, @function	
add:
	movq %rdi, %rax
	addq %rsi, %rax
	addq %rdx, %rax
	ret


