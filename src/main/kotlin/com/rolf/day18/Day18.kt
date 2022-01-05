package com.rolf.day18

import com.rolf.Day

fun main() {
    Day18().run()
}

class Day18 : Day() {
    override fun getDay(): Int {
        return 18
    }

    override fun solve1(lines: List<String>) {
        val input = lines.first()
        val safeTileTotal = countSafeTiles(input, 40)
        println(safeTileTotal)
    }

    override fun solve2(lines: List<String>) {
        val input = lines.first()
        val safeTileTotal = countSafeTiles(input, 400000)
        println(safeTileTotal)
    }

    private fun countSafeTiles(input: String, iterations: Int): Int {
        var array = IntArray(input.length + 2) { 0 }
        for ((index, char) in input.withIndex()) {
            when (char) {
                '.' -> array[index + 1] = 0
                '^' -> array[index + 1] = 1
            }
        }

        var safeTileTotal = safeTiles(array)
        for (i in 2..iterations) {
            array = nextStep(array)
            safeTileTotal += safeTiles(array)
        }
        return safeTileTotal
    }

    private fun safeTiles(array: IntArray): Int {
        return array.map { it }.count { it == 0 } - 2
    }

    private fun nextStep(array: IntArray): IntArray {
        val new = array.clone()
        for (i in 1 until array.size - 1) {
            val left = array[i - 1]
            val right = array[i + 1]
            new[i] = left.xor(right)
        }
        return new
    }
}
