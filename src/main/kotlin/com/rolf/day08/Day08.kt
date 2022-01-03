package com.rolf.day08

import com.rolf.Day
import com.rolf.util.MatrixInt
import com.rolf.util.Point
import com.rolf.util.joinSideBySide
import com.rolf.util.splitLine

fun main() {
    Day08().run()
}

class Day08 : Day() {
    override fun getDay(): Int {
        return 8
    }

    override fun solve1(lines: List<String>) {
        val grid = runScreenProgram(lines)
        println(grid.count(1))
    }

    private fun runScreenProgram(lines: List<String>): MatrixInt {
        val grid = MatrixInt.buildDefault(50, 6, 0)
        for (line in lines) {
            val param = splitLine(line, pattern = "[^\\d+]".toPattern()).filter { it.isNotEmpty() }.map { it.toInt() }
            when {
                line.contains("rect") -> {
                    rect(grid, param)
                }
                line.contains("rotate row") -> {
                    rotateRow(grid, param)
                }
                line.contains("rotate column") -> {
                    rotateColumn(grid, param)
                }
            }
        }
        return grid
    }

    private fun rect(grid: MatrixInt, param: List<Int>) {
        for (y in 0 until param[1]) {
            for (x in 0 until param[0]) {
                grid.set(x, y, 1)
            }
        }
    }

    private fun rotateRow(grid: MatrixInt, param: List<Int>) {
        val y = param[0]
        val shift = param[1]
        val row = grid.getRow(y)
        for (x in 0 until grid.width()) {
            val newX = (x + shift) % grid.width()
            grid.set(newX, y, row[x])
        }
    }

    private fun rotateColumn(grid: MatrixInt, param: List<Int>) {
        val x = param[0]
        val shift = param[1]
        val column = grid.getColumn(x)
        for (y in 0 until grid.height()) {
            val newY = (y + shift) % grid.height()
            grid.set(x, newY, column[y])
        }
    }

    override fun solve2(lines: List<String>) {
        val grid = runScreenProgram(lines)
        val letters = mutableListOf<String>()
        for (s in 0 until grid.width() step 5) {
            val copy = grid.copy()
            copy.cutOut(Point(s, 0), Point(s + 4, grid.height() - 1))
            val letter = copy.toString().replace("0", " ").replace("1", "#")
            letters.add(letter)
        }
        println(joinSideBySide(letters))
    }
}
