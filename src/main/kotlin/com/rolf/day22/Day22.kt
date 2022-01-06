package com.rolf.day22

import com.rolf.Day
import com.rolf.util.MatrixInt
import com.rolf.util.MatrixString
import com.rolf.util.Point
import com.rolf.util.splitLine

fun main() {
    Day22().run()
}

class Day22 : Day() {
    override fun solve1(lines: List<String>) {
        val nodes = parseNodes(lines)

        var pairs = 0
        for (node in nodes) {
            for (node2 in nodes) {
                if (node != node2) {
                    if (node.canStoreDataFrom(node2)) { //node.used in 1..node2.available) {
                        pairs++
                    }
                }
            }
        }
        println(pairs)
    }

    private fun parseNodes(lines: List<String>): List<Node> {
        val result = mutableListOf<Node>()
        for (i in 2 until lines.size) {
            val line = lines[i]
            result.add(parseNode(line))
        }
        return result
    }

    private fun parseNode(line: String): Node {
        // /dev/grid/node-x0-y0     93T   67T    26T   72%
        val (name, size, used, available) = splitLine(line, pattern = "\\s+".toPattern())
        val (_, x, y) = splitLine(name, "-")
        return Node(
            x.removePrefix("x").toInt(),
            y.removePrefix("y").toInt(),
            size.removeSuffix("T").toInt(),
            used.removeSuffix("T").toInt(),
            available.removeSuffix("T").toInt()
        )
    }

    override fun solve2(lines: List<String>) {
        val nodes = parseNodes(lines)

        // Find the locations where the data of 24,0 can go to to find the shortest path to 0,0
        val grid = MatrixString.buildDefault(39, 25, "#")
        val start = findNode(nodes, grid.topLeft())
        val end = findNode(nodes, grid.topRight())
        for (node in nodes) {
            for (node2 in nodes) {
                if (node != node2) {
                    if (node.canStoreDataFrom(node2)) {
                        grid.set(node.toPoint(), ".")
                        grid.set(node2.toPoint(), ".")
                    }
                }
            }
        }

        // Seems there is only 1 node that is empty
        var emptyNode: Node = start
        for (node in nodes) {
            if (node.used == 0) {
                emptyNode = node
            }
        }

        // Find shortest path from empty to right top
        val distanceGrid = MatrixInt.buildForShortestPath(grid, "#")
        var distance = distanceGrid.shortestPath(emptyNode.toPoint(), end.toPoint(), prioritizeByDistance = true)

        // Next we need to move width - 2 times the data to the left with 'loops':
        // down, left, left, up, right (5 moves)
        distance += 5 * (grid.width() - 2)
        println(distance)
    }

    private fun findNode(nodes: List<Node>, point: Point): Node {
        return nodes.first { it.x == point.x && it.y == point.y }
    }
}

data class Node(val x: Int, val y: Int, val size: Int, val used: Int, val available: Int) {
    fun canStoreDataFrom(node: Node): Boolean {
        return node.used in 1..available
    }

    fun toPoint(): Point {
        return Point(x, y)
    }
}
