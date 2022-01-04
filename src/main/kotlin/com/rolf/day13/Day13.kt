package com.rolf.day13

import com.rolf.Day
import com.rolf.util.Binary
import com.rolf.util.MatrixInt
import com.rolf.util.MatrixString
import com.rolf.util.Point

fun main() {
    Day13().run()
}

class Day13 : Day() {
    override fun getDay(): Int {
        return 13
    }

    var favoriteNumber: Long = 0

    override fun solve1(lines: List<String>) {
        val shortestGrid = buildDistanceGrid(lines)
        val distance = shortestGrid.shortestPath(Point(1, 1), Point(31, 39))
        println(distance)
    }

    private fun buildDistanceGrid(lines: List<String>): MatrixInt {
        favoriteNumber = lines[0].toLong()

        val grid = MatrixString.buildDefault(100, 100, "#")
        for (point in grid.allPoints()) {
            if (isOpen(point.x, point.y)) {
                grid.set(point, ".")
            }
        }
        val shortestGrid = MatrixInt.buildForShortestPath(grid, "#")
        return shortestGrid
    }

    private fun isOpen(x: Int, y: Int): Boolean {
        val ones = Binary(value(x, y)).toString().count { it == '1' }
        return ones % 2 == 0
    }

    private fun value(x: Int, y: Int): Long {
        return x * x + 3 * x + 2 * x * y + y + y * y + favoriteNumber
    }

    override fun solve2(lines: List<String>) {
        val shortestGrid = buildDistanceGrid(lines)
        val distance = shortestGrid.shortestPath(Point(1, 1), Point(31, 39))
        println(shortestGrid.allElements().filter { it in 0..50 }.count())
    }
}
