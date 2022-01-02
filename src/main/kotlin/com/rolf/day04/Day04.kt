package com.rolf.day04

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day04().run()
}

class Day04 : Day() {
    override fun getDay(): Int {
        return 4
    }

    override fun solve1(lines: List<String>) {
        val input = parseLines(lines)
        var sum = 0L
        for (line in input) {
            val (name, sector, checksum) = line
            if (isValid(name, checksum)) {
                sum += sector.toInt()
            }
        }
        println(sum)
    }

    private fun parseLines(lines: List<String>): List<List<String>> {
        val result = mutableListOf<List<String>>()
        for (line in lines) {
            val (nameSector, checksum) = splitLine(line.replace("]", ""), "[")
            val nameAndSector = splitLine(nameSector, "-")
            val sector = nameAndSector.last()
            val name = nameAndSector.subList(0, nameAndSector.size - 1).joinToString("-")
            result.add(listOf(name, sector, checksum))
        }
        return result
    }

    private fun isValid(name: String, checksum: String): Boolean {
        val charWithCount = name
            .replace("-", "")
            .map { it }
            .groupBy { it }
            .map { (key, value) -> key to value.size }
        val groupedByCharCount = charWithCount
            .groupBy { it.second }
            .map { (key, value) ->
                key to value.map { it.first }
            }.toMap()
        val nameChecksum = calculateChecksum(groupedByCharCount)
        return nameChecksum == checksum
    }

    private fun calculateChecksum(groupedByCharCount: Map<Int, List<Char>>): String {
        var checksum = ""
        val keys = groupedByCharCount.keys.sorted().reversed().toMutableList()
        while (keys.isNotEmpty() && checksum.length < 5) {
            val key = keys.removeAt(0)
            val chars = groupedByCharCount[key]!!.sorted().joinToString("")
            checksum += chars
        }
        return checksum.substring(0, minOf(checksum.length, 5))
    }

    override fun solve2(lines: List<String>) {
        val input = parseLines(lines)
        for (line in input) {
            val (name, sector, checksum) = line
            if (isValid(name, checksum)) {
                val decrypted = decrypt(name, sector.toInt())
                if (decrypted.contains("north")) {
                    println(decrypted)
                    println(sector)
                }
            }
        }
    }

    private fun decrypt(encrypted: String, sector: Int): String {
        val decrypted = StringBuilder()
        for (word in encrypted.split("-")) {
            val newWord = StringBuilder()
            for (char in word) {
                val newChar = 'a' + (char.code + (sector % 26) - 'a'.code) % 26
                newWord.append(newChar)
            }
            decrypted.append(newWord).append(" ")
        }
        return decrypted.trim().toString()
    }
}
