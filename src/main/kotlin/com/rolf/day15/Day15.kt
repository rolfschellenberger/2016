package com.rolf.day15

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day15().run()
}

class Day15 : Day() {
    override fun getDay(): Int {
        return 15
    }

    override fun solve1(lines: List<String>) {
        val discs = parseDiscs(lines)
        println(findFirstTime(discs))
    }

    override fun solve2(lines: List<String>) {
        val discs = parseDiscs(lines)
        discs.add(Disc(discs.size + 1, 11, 0))
        println(findFirstTime(discs))
    }

    private fun parseDiscs(lines: List<String>): MutableList<Disc> {
        val discs = mutableListOf<Disc>()
        for (line in lines) {
            discs.add(parseDisc(line))
        }
        return discs
    }

    private fun parseDisc(line: String): Disc {
        val parts = splitLine(line, " ")
        return Disc(
            parts[1].replace("#", "").toInt(),
            parts[3].toInt(),
            parts[11].replace(".", "").toInt()
        )
    }

    private fun findFirstTime(discs: MutableList<Disc>): Int {
        for (t in 0..10000000) {
            var allDisks = true
            for ((index, disk) in discs.withIndex()) {
                if (disk.getPosition(t + index) != 0) {
                    allDisks = false
                    break
                }
            }
            if (allDisks) {
                return t - 1
            }
        }
        return -1
    }
}

data class Disc(val id: Int, val positions: Int, val startingPosition: Int) {
    fun getPosition(time: Int): Int {
        return (startingPosition + time) % positions
    }
}
