package com.rolf.day03

import com.rolf.Day
import com.rolf.util.splitLines

fun main() {
    Day03().run()
}

class Day03 : Day() {
    override fun getDay(): Int {
        return 3
    }

    override fun solve1(lines: List<String>) {
        val triangles = splitLines(lines, pattern = "\\s+".toPattern())

        val validCount = triangles.map {
            it.filter { it.isNotEmpty() }
                .map { it.toInt() }
                .sorted()
        }
            .filter { it[0] + it[1] > it[2] }
            .count()
        println(validCount)
    }

    override fun solve2(lines: List<String>) {
        val triangles = splitLines(lines, pattern = "\\s+".toPattern())
        val cleanedTriangles = triangles.map {
            it.filter { it.isNotEmpty() }
                .map { it.toInt() }
        }

        var validCount = 0
        for (i in cleanedTriangles.indices step 3) {
            val one = cleanedTriangles[i]
            val two = cleanedTriangles[i + 1]
            val three = cleanedTriangles[i + 2]
            for (j in 0..2) {
                if (isValid(listOf(one[j], two[j], three[j]))) {
                    validCount++
                }
            }
        }
        println(validCount)
    }

    private fun isValid(list: List<Int>): Boolean {
        val sortedList = list.sorted()
        return sortedList[0] + sortedList[1] > sortedList[2]
    }
}
