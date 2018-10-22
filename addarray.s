	.text
.globl addarray
	.type	addarray, @function
addarray:
	mov $0, %al
	mov $0, %rdx
SumLoop:
	cmp %rdx, %rdi
	je EndLoop
	mov (%rsi), %cl
	add %cl, %al
	mov $4, %rcx
	add %rcx, %rsi
	mov $1, %rcx
	add %rcx, %rdx
	jmp SumLoop
EndLoop:
	ret
	
