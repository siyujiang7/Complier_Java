#!/bin/env python3

import re

class Err():
    def __init__(self, regex, name):
        self.regex = re.compile(regex)
        self.name = name
        self.count = 0

# We treat warnings & errors the exact same, and just use the name error for both

var_type = "(char|char\*|char\*\*|long|long\*|void|double|double\*)"
unary_type = "(\+|-|\*|&)"
binary_type = "(\+|-|>|<|<=|>=|\/|\*)"

errors = [
    ### Statements
    Err("SimpleC forbids mixed declarations and code", "Code can't precede declarations"),
    Err("break statement not within loop", "Break not in loop"),
    Err("continue statement not within loop", "Continue not in loop"),
    Err("'return' with a value, in function returning void", "Returning value in void function"),
    Err("incompatible types when returning type '"+ var_type + "' but '" + var_type + "' was expected", "Incompatible return type"),

    ### AssignStatement
    Err("incompatible types when assigning to type '" + var_type + "' from type '" + var_type + "'", "Incompatible type assigning"),
    Err("'\w+' undeclared", "Assigning to an undeclared variable"),
    Err("array subscript is not an integer", "Non-Integer array subscript"),
    Err("assignment makes integer from pointer without a cast", "Assigning pointer to integer"),

    ### Expressions
    Err("id '\w+' is undeclared", "Undeclared Variable in expression"),
    Err("division by zero", "Divide by zero"),
    Err("wrong type argument to unary '" + unary_type + "'", "Invalid unary operation"),
    Err("invalid operands to binary " + binary_type + " \(have '" + var_type + "' and '" + var_type + "'\)", "Invalid operands to binary operator"),

    ### Values/Function
    Err("redeclaration of '\w+'", "Variable/Function Redeclaration"),
    Err("incompatible type for argument \d+ of '\w+'", "Incompatible function argument types"),
    Err("too few arguments for function '\w+'", "Too few function arguments"),
    Err("too many arguments for function '\w+'", "Too many function arguments"),
]

def get_input():
    ret = []
    while True:
        try:
            line = input()
            if line:
                ret.append(line)
            else:
                break
        except:
            break
    return ret

lines = get_input()
error_count = 0
for line in lines:
    if "error" in line or "warn" in line:
        error_count += 1
        for error in errors:
            if re.search(error.regex, line):
                error.count += 1


print("----------\n", "Errors/Warnings:", error_count, "\n----------")
for error in errors:
    print(str(error.count) + " : " + error.name)
