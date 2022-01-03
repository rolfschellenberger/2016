package com.rolf.day12

import com.rolf.Day
import com.rolf.util.isNumeric

fun main() {
    Day12().run()
}

class Day12 : Day() {
    override fun getDay(): Int {
        return 12
    }

    override fun solve1(lines: List<String>) {
        val computer = Computer(lines)
        computer.execute()
        println(computer.registers["a"])
    }

    override fun solve2(lines: List<String>) {
        val computer = Computer(lines)
        computer.registers["c"] = 1
        computer.execute()
        println(computer.registers["a"])
    }
}

class Computer(val instructions: List<String>) {
    val registers = mutableMapOf<String, Int>()
    var pointer = 0

    fun execute() {
        while (pointer < instructions.size) {
            pointer += execute(instructions[pointer])
        }
    }

    fun execute(instruction: String): Int {
        val parts = instruction.split(" ")
        when (parts[0]) {
            "cpy" -> {
                val x = getValue(parts[1])
                val y = parts[2]
                registers[y] = x
            }
            "inc" -> {
                val x = getValue(parts[1])
                registers[parts[1]] = x + 1
            }
            "dec" -> {
                val x = getValue(parts[1])
                registers[parts[1]] = x - 1
            }
            "jnz" -> {
                val x = getValue(parts[1])
                if (x != 0) {
                    return getValue(parts[2])
                }
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