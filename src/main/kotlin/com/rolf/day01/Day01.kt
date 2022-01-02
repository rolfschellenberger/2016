package com.rolf.day01

import com.rolf.Day
import com.rolf.util.MatrixInt
import com.rolf.util.Point
import com.rolf.util.splitLine

fun main() {
    Day01().run()
}

class Day01 : Day() {
    override fun getDay(): Int {
        return 1
    }

    override fun solve1(lines: List<String>) {
        val instructions = splitLine(lines[0], ", ")

        val grid = MatrixInt.buildDefault(1000, 1000, 0)
        var start = Point(500, 500)
        var location = start
        var firstDuplicate: Point? = null
        grid.set(start, 1)

        for (instruction in instructions) {
            val direction = instruction[0]
            val steps = instruction.substring(1, instruction.length).toInt()
            for (i in 0 until steps) {
                when (direction) {
                    'R' -> {
                        location = grid.getRight(location)!!
                        if (firstDuplicate == null && grid.get(location) > 0) {
                            println("Duplicate found at $location: ${location.distance(start)}")
                            firstDuplicate = location
                        }
                        grid.set(location, 1)
                    }
                    'L' -> {
                        location = grid.getLeft(location)!!
                        if (firstDuplicate == null && grid.get(location) > 0) {
                            println("Duplicate found at $location: ${location.distance(start)}")
                            firstDuplicate = location
                        }
                        grid.set(location, 1)
                    }
                }
            }

            // Rotate
            when (direction) {
                'R' -> {
                    grid.rotateRight()
                    grid.rotateRight()
                    grid.rotateRight()
                    start = start.rotateRight(grid.width(), grid.height(), -90.0)
                    location = location.rotateRight(grid.width(), grid.height(), -90.0)
                }
                'L' -> {
                    grid.rotateRight()
                    start = start.rotateRight(grid.width(), grid.height(), 90.0)
                    location = location.rotateRight(grid.width(), grid.height(), 90.0)
                }
            }
        }

        println(location.distance(start))
    }

    override fun solve2(lines: List<String>) {
    }
}
