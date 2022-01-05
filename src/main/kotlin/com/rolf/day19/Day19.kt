package com.rolf.day19

import com.rolf.Day

fun main() {
    Day19().run()
}

class Day19 : Day() {
    override fun getDay(): Int {
        return 19
    }

    override fun solve1(lines: List<String>) {
        val input = lines.first().toInt()

        val nodes = mutableListOf<Node>()
        for (elf in 0 until input) {
            nodes.add(Node(elf, 1))
        }
        for ((index, node) in nodes.withIndex()) {
            node.previous = if (index == 0) nodes.last() else nodes[index - 1]
            node.next = if (index == nodes.lastIndex) nodes.first() else nodes[index + 1]
        }

        var node = nodes.first()
        while (true) {
            // Remove from table
            if (node.presents == 0) {
                node.previous?.next = node.next
                node.next?.previous = node.previous
            }
            // Get present from next node
            else {
                node.presents = node.next!!.presents
                node.next?.presents = 0
            }
            if (node == node.next) {
                println(node.index + 1)
                return
            }
            node = node.next!!
        }
    }

    override fun solve2(lines: List<String>) {
        val input = lines.first().toInt()
        val left = ArrayDeque((1..input / 2).toList())
        val right = ArrayDeque(((input / 2) + 1..input).toList())

        while (left.size + right.size > 1) {
            if (left.size > right.size) left.removeLast()
            else right.removeFirst()

            right.addLast(left.removeFirst())
            left.addLast(right.removeFirst())
        }

        println(left.firstOrNull() ?: right.first())
    }
}

class Node(val index: Int, var presents: Int) {
    var previous: Node? = null
    var next: Node? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        if (index != other.index) return false

        return true
    }

    override fun hashCode(): Int {
        return index
    }

    override fun toString(): String {
        return "Node(index=$index, presents=$presents)"
    }
}
