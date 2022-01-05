package com.rolf.day16

import com.rolf.Day
import com.rolf.util.splitLine

fun main() {
    Day16().run()
}

class Day16 : Day() {
    override fun getDay(): Int {
        return 16
    }

    override fun solve1(lines: List<String>) {
        val input = lines.first()
        val length = 272
        val data = generateData(input, length)
        val checksum = generateChecksum(data)
        println(checksum)
    }

    override fun solve2(lines: List<String>) {
        val input = lines.first()
        val length = 35651584
        val data = generateData(input, length)
        val checksum = generateChecksum(data)
        println(checksum)
    }

    private fun generateData(input: String, length: Int): String {
        var result = input
        while (result.length < length) {
            val reversed = result.reversed()
            val inverted = reversed
                .replace("0", "t")
                .replace("1", "0")
                .replace("t", "1")
            result = "${result}0${inverted}"
        }
        return result.substring(0, length)
    }

    private fun generateChecksum(data: String): String {
        var checksum = makeChecksum(data)
        while (checksum.length % 2 == 0) {
            checksum = makeChecksum(checksum)
        }
        return checksum
    }

    private fun makeChecksum(data: String): String {
        val parts = splitLine(data, chunkSize = 2)
        val result = StringBuilder()
        for (part in parts) {
            when (part) {
                "00", "11" -> result.append("1")
                else -> result.append("0")
            }
        }
        return result.toString()
    }
}
