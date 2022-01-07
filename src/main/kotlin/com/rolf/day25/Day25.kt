package com.rolf.day25

import com.rolf.Day
import com.rolf.util.isNumeric

fun main() {
    Day25().run()
}

class Day25 : Day() {
    override fun solve1(lines: List<String>) {
        val instructions = parseInstruction(lines)
        for (i in 0..1000) {
            val computer = Computer(instructions)
            computer.registers["a"] = i
            val result = computer.execute(10)
            if (result == "01".repeat(5)) {
                println(i)
                return
            }
        }
    }

    override fun solve2(lines: List<String>) {
    }

    private fun parseInstruction(lines: List<String>): List<Instruction> {
        val instructions = mutableListOf<Instruction>()
        for (line in lines) {
            instructions.add(Instruction(line))
        }
        return instructions
    }
}

class Instruction(input: String) {
    val parts: List<String> = input.split(" ")
    var operator: String = parts[0]
    val arguments: Int = parts.size - 1

    operator fun get(index: Int): String {
        return parts[index]
    }

    override fun toString(): String {
        return "Instruction(operator='$operator', parts=${parts.toList().joinToString(" ")})"
    }
}

class Computer(val instructions: List<Instruction>) {
    val registers = mutableMapOf<String, Int>()
    var pointer = 0
    var output: MutableList<Int> = mutableListOf()

    fun execute(outputCount: Int): String {
        output.clear()
        while (pointer < instructions.size) {
            pointer += execute(instructions[pointer])
            if (output.size >= outputCount) {
                return output.joinToString("")
            }
        }
        return output.joinToString("")
    }

    fun execute(instruction: Instruction): Int {
        when (instruction.operator) {
            "cpy" -> {
                val x = getValue(instruction[1])
                val y = instruction[2]
                registers[y] = x
            }
            "inc" -> {
                val x = getValue(instruction[1])
                registers[instruction[1]] = x + 1
            }
            "dec" -> {
                val x = getValue(instruction[1])
                registers[instruction[1]] = x - 1
            }
            "jnz" -> {
                val x = getValue(instruction[1])
                if (x != 0) {
                    return getValue(instruction[2])
                }
            }
            "tgl" -> {
                val x = getValue(instruction[1])
                val instructionPointer = pointer + x
                if (instructionPointer < instructions.size) {
                    val inst = instructions[instructionPointer]
                    when (inst.arguments) {
                        1 -> {
                            when (inst.operator) {
                                "inc" -> inst.operator = "dec"
                                else -> inst.operator = "inc"
                            }
                        }
                        2 -> {
                            when (inst.operator) {
                                "jnz" -> inst.operator = "cpy"
                                else -> inst.operator = "jnz"
                            }
                        }

                    }
                }
            }
            "out" -> {
                val x = getValue(instruction[1])
                output.add(x)
            }
        }
        return 1
    }

    private fun getValue(s: String): Int {
        if (s.isNumeric()) {
            return s.toInt()
        }
        registers.computeIfAbsent(s) { 0 }
        return registers[s]!!
    }
}
