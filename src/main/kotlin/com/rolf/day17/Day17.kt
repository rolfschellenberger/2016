package com.rolf.day17

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.md5

fun main() {
    Day17().run()
}

class Day17 : Day() {
    override fun getDay(): Int {
        return 17
    }

    override fun solve1(lines: List<String>) {
        val passcode = lines.first()
        val grid = MatrixString.buildDefault(4, 4, ".")
        val position = Point(0, 0)
        val end = Point(3, 3)
        var shortestPath: String? = null
        val paths = findPaths(grid, position, passcode).toMutableList()
        while (paths.isNotEmpty()) {
            val path = paths.removeAt(0)
            if (shortestPath != null && path.second.length >= shortestPath.length) {
                continue
            }
            if (path.first == end) {
                if (shortestPath == null || path.second.length < shortestPath.length) {
                    shortestPath = path.second
                }
            }
            paths.addAll(findPaths(grid, path.first, path.second))
        }
        println(shortestPath?.removePrefix(passcode))
    }

    override fun solve2(lines: List<String>) {
        val passcode = lines.first()
        val grid = MatrixString.buildDefault(4, 4, ".")
        val position = Point(0, 0)
        val end = Point(3, 3)
        var longestLength = 0
        val paths = findPaths(grid, position, passcode).toMutableList()
        while (paths.isNotEmpty()) {
            val path = paths.removeAt(0)
            if (path.first == end) {
                val p = path.second.removePrefix(passcode)
                if (p.length > longestLength) {
                    longestLength = p.length
                }
            } else {
                paths.addAll(findPaths(grid, path.first, path.second))
            }
        }
        println(longestLength)
    }

    private fun findPaths(
        grid: MatrixString,
        position: Point,
        passcode: String
    ): List<Pair<Point, String>> {
        val result = mutableListOf<Pair<Point?, String>>()
        val hash = md5(passcode, false)
        if (isOpen(hash[0])) {
            result.add(grid.getUp(position) to passcode + "U")
        }
        if (isOpen(hash[1])) {
            result.add(grid.getDown(position) to passcode + "D")
        }
        if (isOpen(hash[2])) {
            result.add(grid.getLeft(position) to passcode + "L")
        }
        if (isOpen(hash[3])) {
            result.add(grid.getRight(position) to passcode + "R")
        }
        return result.filter { it.first != null }.map { it.first!! to it.second }
    }

    private fun isOpen(c: Char): Boolean {
        return c in 'b'..'f'
    }
}
