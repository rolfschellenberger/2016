package com.rolf.day07

import com.rolf.Day

fun main() {
    Day07().run()
}

class Day07 : Day() {
    override fun getDay(): Int {
        return 7
    }

    override fun solve1(lines: List<String>) {
        println(lines.filter { supportsTLS(it) }.count())
    }

    private fun supportsTLS(ip: String): Boolean {
        val (ipList, hyperList) = splitToParts(ip)

        return ipList.filter { containsAbba(it) }.count() > 0 &&
                hyperList.filter { containsAbba(it) }.count() == 0
    }

    private fun splitToParts(ip: String): List<List<String>> {
        val ipList = mutableListOf<String>()
        val hyperList = mutableListOf<String>()

        val sequence = mutableListOf<Char>()
        for (c in ip) {
            when (c) {
                '[' -> {
                    ipList.add(sequence.joinToString(""))
                    sequence.clear()
                }
                ']' -> {
                    hyperList.add(sequence.joinToString(""))
                    sequence.clear()
                }
                else -> sequence.add(c)
            }
        }
        ipList.add(sequence.joinToString(""))
        return listOf(ipList, hyperList)
    }

    private fun containsAbba(sequence: String): Boolean {
        for (i in 0 until sequence.length - 3) {
            val a1 = sequence[i]
            val b1 = sequence[i + 1]
            val b2 = sequence[i + 2]
            val a2 = sequence[i + 3]

            if (a1 == a2 && b1 == b2 && a1 != b1) {
                return true
            }
        }
        return false
    }

    override fun solve2(lines: List<String>) {
        println(lines.filter { supportsSSL(it) }.count())
    }

    private fun supportsSSL(ip: String): Boolean {
        val (ipList, hyperList) = splitToParts(ip)

        for (ipPart in ipList) {
            val abas = getAbas(ipPart)
            for (aba in abas) {
                val hyperWithBab = hyperList.map { it.contains(inverse(aba)) }
                    .filter { it }
                    .count() > 0
                if (hyperWithBab) {
                    return true
                }
            }
        }
        return false
    }

    private fun inverse(aba: String): String {
        return "${aba[1]}${aba[0]}${aba[1]}"
    }

    private fun getAbas(sequence: String): List<String> {
        val result = mutableListOf<String>()
        for (i in 0 until sequence.length - 2) {
            val a1 = sequence[i]
            val b1 = sequence[i + 1]
            val a2 = sequence[i + 2]

            if (a1 == a2 && a1 != b1) {
                result.add("$a1$b1$a2")
            }
        }
        return result
    }
}
