package com.rolf.day09

import com.rolf.Day

fun main() {
    Day09().run()
}

class Day09 : Day() {
    override fun getDay(): Int {
        return 9
    }

    override fun solve1(lines: List<String>) {
        val line = lines[0]
        val output = decompress(line)
        println(output.length)
    }

    private fun decompress(line: String): String {
        val output = StringBuilder()
        val sequence = mutableListOf<Char>()

        var i = 0
        while (i < line.length) {
            when (val char = line[i]) {
                '(' -> {
                    sequence.add(char)
                }
                ')' -> {
                    sequence.add(char)
                    val (length, repeat) = parseSequence(sequence)
                    val substring = line.substring(i + 1, i + length + 1).repeat(repeat)
                    output.append(substring)
                    i += length
                    sequence.clear()
                }
                else -> {
                    if (sequence.isNotEmpty()) {
                        sequence.add(char)
                    } else {
                        output.append(char)
                    }
                }
            }
            i++
        }
        return output.toString()
    }

    private fun parseSequence(sequence: List<Char>): List<Int> {
        val string = sequence.joinToString("")
        return string.replace("(", "")
            .replace(")", "")
            .split("x").map { it.toInt() }
    }

    override fun solve2(lines: List<String>) {
        val line = lines[0]
        val length = decompress2(line)
        println(length)
    }

    private fun decompress2(line: String): Long {
        val weights = LongArray(line.length) { 1L }
        var totalLength = 0L

        val sequence = mutableListOf<Char>()
        for ((index, char) in line.withIndex()) {
            when (char) {
                '(' -> {
                    sequence.add(char)
                }
                ')' -> {
                    sequence.add(char)
                    val (length, repeat) = parseSequence(sequence)
                    for (i in index + 1 until index + length + 1) {
                        weights[i] *= repeat.toLong()
                    }
                    sequence.clear()
                }
                else -> {
                    if (sequence.isEmpty()) {
                        totalLength += weights[index]
                    } else {
                        sequence.add(char)
                    }
                }
            }
        }
        return totalLength
    }
}
