package com.rolf.day21

import com.rolf.Day
import com.rolf.util.getPermutations
import com.rolf.util.groupLines
import com.rolf.util.splitLine

fun main() {
    Day21().run()
}

class Day21 : Day() {
    override fun solve1(lines: List<String>) {
        val groups = groupLines(lines, "")
        val input = groups[0].first()
        val structure = runStructure(input, groups[1])
        println(structure)
    }

    override fun solve2(lines: List<String>) {
        val groups = groupLines(lines, "")
        val input = "fbgdceah"

        var found = false
        fun onNext(option: List<Char>) {
            if (!found) {
                val structure = runStructure(option.joinToString(""), groups[1])
                if (structure.toString() == input) {
                    found = true
                    println(option.joinToString(""))
                }
            }
        }

        getPermutations(input.toList(), ::onNext)
    }

    private fun runStructure(input: String, lines: List<String>): Structure {
        val structure = Structure(input)
        for (line in lines) {
            val parts = splitLine(line, " ")
            when {
                line.startsWith("swap position") -> {
                    structure.swap(parts[2].toInt(), parts[5].toInt())
                }
                line.startsWith("swap letter") -> {
                    structure.swap(parts[2].first(), parts[5].first())
                }
                line.startsWith("rotate left") -> {
                    structure.rotateLeft(parts[2].toInt())
                }
                line.startsWith("rotate right") -> {
                    structure.rotateRight(parts[2].toInt())
                }
                line.startsWith("rotate based on position of letter") -> {
                    structure.rotate(parts[6].first())
                }
                line.startsWith("reverse positions") -> {
                    structure.reverse(parts[2].toInt()..parts[4].toInt())
                }
                line.startsWith("move position") -> {
                    structure.move(parts[2].toInt(), parts[5].toInt())
                }
            }
        }
        return structure
    }
}

class Structure(input: String) {
    val map: MutableMap<Char, Int> = input
        .mapIndexed { index, c -> c to index }
        .toMap()
        .toSortedMap(compareBy<Char> { it })
    val size: Int = map.size

    fun swap(posA: Int, posB: Int) {
        val a = getChar(posA)
        val b = getChar(posB)
        val aValue = a.value
        a.setValue(b.value)
        b.setValue(aValue)
    }

    fun swap(charA: Char, charB: Char) {
        val posA = map[charA]!!
        map[charA] = map[charB]!!
        map[charB] = posA
    }

    fun reverse(range: IntRange) {
        for (pair in map) {
            if (pair.value in range) {
                val pos = range.first - pair.value + range.last
                pair.setValue(pos)
            }
        }
    }

    fun rotateLeft(steps: Int) {
        rotateRight(size - steps)
    }

    fun rotateRight(steps: Int) {
        for (pair in map) {
            pair.setValue((pair.value + steps) % size)
        }
    }

    fun move(from: Int, to: Int) {
        val low = minOf(from, to)
        val high = maxOf(from, to)
        val increment = if (from < to) -1 else 1
        for (pair in map) {
            if (pair.value == from) {
                pair.setValue(to)
            } else if (pair.value in low..high) {
                pair.setValue(pair.value + increment)
            }
        }
    }

    fun rotate(char: Char) {
        val pos = map[char]!!
        val count = 1 + pos + (if (pos >= 4) 1 else 0)
        rotateRight(count)
    }

    private fun getChar(position: Int): MutableMap.MutableEntry<Char, Int> {
        for (pair in map) {
            if (pair.value == position) {
                return pair
            }
        }
        throw Exception("Position $position not found")
    }

    override fun toString(): String {
        return map.toList().sortedBy { (_, value) -> value }.toMap().keys.joinToString("")
    }
}
