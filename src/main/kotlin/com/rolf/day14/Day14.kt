package com.rolf.day14

import com.rolf.Day
import com.rolf.util.md5

fun main() {
    Day14().run()
}

class Day14 : Day() {
    override fun getDay(): Int {
        return 14
    }

    override fun solve1(lines: List<String>) {
        println(findHashPosition(lines, false))
    }

    override fun solve2(lines: List<String>) {
        println(findHashPosition(lines, true))
    }

    private fun findHashPosition(lines: List<String>, secondPart: Boolean): Int {
        val prefix = lines.first()

        // Track threes from position to a list of characters (that have at least 3 occurrences on that position)
        // Keep the position of fives 1000 ahead of the three pointer
        // When we find a three, look for fives for every character
        // Remove fives at the beginning (sliding window) or use a range when searching for fives
        // Store fives per character and next the positions, so we can filter on the positions that are max 1000
        // more than the three position
        val fives: MutableMap<Char, MutableList<Int>> = mutableMapOf()

        var fivePointer = 0
        var threePointer = fivePointer - 1000
        var keysFound = 0
        while (true) {
            if (fivePointer >= 0) {
                val hash = hash("$prefix$fivePointer", if (secondPart) 2016 else 0)
                val duplicatesFive = getDuplicates(hash, 5)
                for (fiveChar in duplicatesFive) {
                    fives.computeIfAbsent(fiveChar) { mutableListOf() }
                    fives[fiveChar]?.add(fivePointer)
                }
            }
            if (threePointer >= 0) {
                val hash = hash("$prefix$threePointer", if (secondPart) 2016 else 0)
                val duplicatesThree = getDuplicates(hash, 3)

                var foundKey = false
                for (threeChar in duplicatesThree) {
                    val fivePositions = fives.getOrDefault(threeChar, mutableListOf())
                        .filter { it in threePointer + 1..threePointer + 1000 }
                    if (fivePositions.isNotEmpty()) {
                        foundKey = true
                    }
                    // Only first 3 chars are inspected
                    break
                }
                if (foundKey) {
                    keysFound++
                    if (keysFound >= 64) {
                        return threePointer
                    }
                }
            }

            fivePointer++
            threePointer++
        }
    }

    private fun getDuplicates(hash: String, minLength: Int): List<Char> {
        val result = mutableListOf<Char>()
        var lastChar: Char? = null
        var sequence = 0
        for (char in hash) {
            if (lastChar == null || lastChar == char) {
                sequence++
            } else {
                if (sequence >= minLength) {
                    result.add(lastChar)
                }
                sequence = 1
            }
            lastChar = char
        }
        if (lastChar != null && sequence >= minLength) {
            result.add(lastChar)
        }
        return result
    }

    private fun hash(input: String, repeat: Int): String {
        var hash = md5(input, uppercase = false)
        for (i in 0 until repeat) {
            hash = md5(hash, uppercase = false)
        }
        return hash
    }
}
