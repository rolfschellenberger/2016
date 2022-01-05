package com.rolf.day20

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day20().run()
}

class Day20 : Day() {
    override fun solve1(lines: List<String>) {
        val sortedRanges = parseRanges(lines)

        var pointer = -1L
        for (range in sortedRanges) {
            if (pointer + 1 < range.first) {
                println(pointer + 1)
                return
            }
            pointer = maxOf(pointer, range.last)
        }
    }

    override fun solve2(lines: List<String>) {
        val sortedRanges = parseRanges(lines)

        var pointer = -1L
        var allowed = 0L
        for (range in sortedRanges) {
            if (pointer + 1 < range.first) {
                allowed += range.first - pointer - 1
            }
            pointer = maxOf(pointer, range.last)
        }
        allowed += 4294967295L - pointer
        println(allowed)
    }

    private fun parseRanges(lines: List<String>): List<LongRange> {
        return lines.map { splitLine(it, "-") }
            .map { it.first().toLong()..it.last().toLong() }
            .sortedBy { it.first }
    }
}
