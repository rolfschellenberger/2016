package com.rolf.day02

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.splitLines

fun main() {
    Day02().run()
}

class Day02 : Day() {
    override fun getDay(): Int {
        return 2
    }

    override fun solve1(lines: List<String>) {
        val instructions = splitLines(lines)

        val grid = MatrixString.buildDefault(3, 3, " ")
        var value = 1
        for (y in 0 until grid.height()) {
            for (x in 0 until grid.width()) {
                grid.set(x, y, value.toString())
                value++
            }
        }
        val start = Point(1, 1)
        println(findCode(grid, start, instructions))
    }

    override fun solve2(lines: List<String>) {
        val instructions = splitLines(lines)

        val grid = MatrixString.buildDefault(5, 5, " ")
        grid.set(2, 0, "1")
        grid.set(1, 1, "2")
        grid.set(2, 1, "3")
        grid.set(3, 1, "4")
        grid.set(0, 2, "5")
        grid.set(1, 2, "6")
        grid.set(2, 2, "7")
        grid.set(3, 2, "8")
        grid.set(4, 2, "9")
        grid.set(1, 3, "A")
        grid.set(2, 3, "B")
        grid.set(3, 3, "C")
        grid.set(2, 4, "D")
        println(grid)

        val start = Point(0, 2)
        println(findCode(grid, start, instructions))
    }

    private fun findCode(grid: MatrixString, start: Point, instructions: List<List<String>>): String {
        val code = mutableListOf<String>()
        var location = start

        for (line in instructions) {
            for (direction in line) {
                val newLocation = when (direction) {
                    "U" -> {
                        grid.getUp(location)
                    }
                    "D" -> {
                        grid.getDown(location)
                    }
                    "L" -> {
                        grid.getLeft(location)
                    }
                    "R" -> {
                        grid.getRight(location)
                    }
                    else -> throw Exception("Invalid direction found: $direction")
                }
                if (newLocation != null && grid.get(newLocation) != " ") {
                    location = newLocation
                }
            }
            code.add(grid.get(location))
        }
        return code.joinToString("")
    }
}
