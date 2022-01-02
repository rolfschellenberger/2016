package com.rolf.day05

import com.rolf.Day
import com.rolf.util.isNumeric
import com.rolf.util.md5

fun main() {
    Day05().run()
}

class Day05 : Day() {
    override fun getDay(): Int {
        return 5
    }

    override fun solve1(lines: List<String>) {
        val input = lines.first()
        var counter = 0
        var password = ""
        while (password.length < 8) {
            val hash = md5("$input$counter")
            if (hash.startsWith("00000")) {
                password += hash[5]
            }
            counter++
        }
        println(password)
    }

    override fun solve2(lines: List<String>) {
        val input = lines.first()
        var counter = 0
        val password = Array(8) { "_" }
        while (password.count { it != "_" } < 8) {
            val hash = md5("$input$counter")
            if (hash.startsWith("00000")) {
                val indexString = hash[5].toString()
                if (indexString.isNumeric()) {
                    val index = hash[5].toString().toInt()
                    if (index < password.size && password[index] == "_") {
                        password[index] = hash[6].toString()
                        println(password.joinToString(""))
                    }
                }
            }
            counter++
        }
        println(password.joinToString(""))
    }
}
