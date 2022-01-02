package com.rolf.day06

import com.rolf.Day
import com.rolf.util.MatrixString
import com.rolf.util.splitLines

fun main() {
    Day06().run()
}

class Day06 : Day() {
    override fun getDay(): Int {
        return 6
    }

    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val word = mutableListOf<String>()
        for (x in 0 until grid.width()) {
            val column = grid.getColumn(x)
            val grouped = column.groupingBy { it }.eachCount()
                .map { (key, value) -> value to key }
                .toMap()
                .toSortedMap()
            val letter = grouped[grouped.lastKey()]!!
            word.add(letter)
        }
        println(word.joinToString(""))
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))
        val word = mutableListOf<String>()
        for (x in 0 until grid.width()) {
            val column = grid.getColumn(x)
            val grouped = column.groupingBy { it }.eachCount()
                .map { (key, value) -> value to key }
                .toMap()
                .toSortedMap()
            val letter = grouped[grouped.firstKey()]!!
            word.add(letter)
        }
        println(word.joinToString(""))
    }
}
