package com.rolf.day10

import com.rolf.Day

fun main() {
    Day10().run()
}

class Day10 : Day() {
    override fun getDay(): Int {
        return 10
    }

    override fun solve1(lines: List<String>) {
        val network = buildNetwork(lines)
        println(network.getBotWithValues(17 to 61)?.name)
    }

    override fun solve2(lines: List<String>) {
        val network = buildNetwork(lines)
        val values = network.getOrCreate("output 0").values +
                network.getOrCreate("output 1").values +
                network.getOrCreate("output 2").values
        println(values.reduce(Int::times))
    }

    private fun buildNetwork(lines: List<String>): Network {
        val network = Network()
        for (line in lines) {
            when {
                // bot 75 gives low to bot 145 and high to bot 95
                line.startsWith("bot") -> {
                    val from = line.substring(0, line.indexOf(" gives"))
                    val low = line.substring(line.indexOf("low to ") + 7, line.indexOf(" and high"))
                    val high = line.substring(line.indexOf("high to ") + 8, line.length)
                    network.getOrCreate(from).low = network.getOrCreate(low)
                    network.getOrCreate(from).high = network.getOrCreate(high)
                }
                // value 3 goes to bot 1
                line.startsWith("value") -> {
                    val value = line.substring(6, line.indexOf(" goes to "))
                    val to = line.substring(line.indexOf("goes to ") + 8, line.length)
                    network.getOrCreate(to).values.add(value.toInt())
                }
            }
        }

        while (true) {
            val bots = network.findWithTwoValues()
            if (bots.isEmpty()) break
            for (bot in bots) {
                bot.pushValues()
            }
        }
        return network
    }
}

class Network {
    val map: MutableMap<String, Bot> = mutableMapOf()

    fun add(bot: Bot) {
        map[bot.name] = bot
    }

    fun get(name: String): Bot? {
        return map[name]
    }

    fun getOrCreate(name: String): Bot {
        map.computeIfAbsent(name) { Bot(name) }
        return map[name]!!
    }

    fun findWithTwoValues(): List<Bot> {
        return map.values.filter { it.values.size == 2 }
    }

    fun getBotWithValues(pair: Pair<Int, Int>): Bot? {
        for (bot in map.values) {
            if (bot.hasProcessed(pair)) {
                return bot
            }
        }
        return null
    }
}

data class Bot(val name: String) {
    val values: MutableList<Int> = mutableListOf()
    var low: Bot? = null
    var high: Bot? = null
    private val processed: MutableList<Pair<Int, Int>> = mutableListOf()

    override fun toString(): String {
        return "Bot(name='$name', values=$values, low='${low?.name}', high='${high?.name}', processed=$processed)"
    }

    fun pushValues() {
        values.sort()
        if (values.size == 2 && low != null && high != null) {
            processed.add(values.first() to values.last())
            low?.values?.add(values.first())
            high?.values?.add(values.last())
            values.clear()
        }
    }

    fun hasProcessed(pair: Pair<Int, Int>): Boolean {
        return processed.contains(pair) || processed.contains(pair.second to pair.first)
    }
}
