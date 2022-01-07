package com.rolf.day24

import com.rolf.Day
import com.rolf.util.*

fun main() {
    Day24().run()
}

class Day24 : Day() {
    override fun solve1(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))

        var start = Point(0, 0)
        val locations = mutableMapOf<Int, Point>()
        for (point in grid.allPoints()) {
            val value = grid.get(point)
            if (value.isNumeric()) {
                val number = value.toInt()
                if (number == 0) {
                    start = point
                } else {
                    locations[number] = point
                }
            }
        }
        val allLocations = locations.values + start

        // Calculate the shortest distances between all locations
        val distances: MutableMap<Pair<Point, Point>, Int> = mutableMapOf()
        prefill(distances)
        for ((index, location) in allLocations.withIndex()) {
            println("Calculating distance ${index + 1} / ${allLocations.size}")
            for (otherLocation in allLocations) {
                if (location != otherLocation) {
                    val cache = distances[location to otherLocation]
                    if (cache == null) {
                        val previouslyCalculated = distances[otherLocation to location]
                        if (previouslyCalculated != null) {
                            distances[location to otherLocation] = previouslyCalculated
                        } else {
                            val matrix = MatrixInt.buildForShortestPath(grid, "#")
                            val dist = matrix.shortestPath(location, otherLocation)
                            distances[location to otherLocation] = dist
                        }
                    } else {
                        distances[otherLocation to location] = cache
                    }
                }
            }
        }

        // Sum the shortest distance to visit all
        val permutations = getPermutations(locations.keys.toList())
        var minDistance = Int.MAX_VALUE
        for (permutation in permutations) {
            var distance = 0
            var from = start
            for (pos in permutation) {
                val to = locations[pos]!!
                distance += distances[from to to]!!
                from = to
            }
            minDistance = minOf(minDistance, distance)
        }
        println(minDistance)
    }

    private fun prefill(distances: MutableMap<Pair<Point, Point>, Int>) {
        distances[Point(x = 49, y = 3) to Point(x = 49, y = 3)] = 0
        distances[Point(x = 49, y = 3) to Point(x = 7, y = 7)] = 74
        distances[Point(x = 49, y = 3) to Point(x = 165, y = 17)] = 194
        distances[Point(x = 49, y = 3) to Point(x = 1, y = 19)] = 72
        distances[Point(x = 49, y = 3) to Point(x = 11, y = 31)] = 78
        distances[Point(x = 49, y = 3) to Point(x = 177, y = 31)] = 208
        distances[Point(x = 49, y = 3) to Point(x = 43, y = 33)] = 56
        distances[Point(x = 49, y = 3) to Point(x = 175, y = 1)] = 208
        distances[Point(x = 7, y = 7) to Point(x = 7, y = 7)] = 0
        distances[Point(x = 7, y = 7) to Point(x = 165, y = 17)] = 252
        distances[Point(x = 7, y = 7) to Point(x = 1, y = 19)] = 26
        distances[Point(x = 7, y = 7) to Point(x = 11, y = 31)] = 52
        distances[Point(x = 7, y = 7) to Point(x = 177, y = 31)] = 266
        distances[Point(x = 7, y = 7) to Point(x = 43, y = 33)] = 86
        distances[Point(x = 7, y = 7) to Point(x = 175, y = 1)] = 266
        distances[Point(x = 165, y = 17) to Point(x = 165, y = 17)] = 0
        distances[Point(x = 165, y = 17) to Point(x = 1, y = 19)] = 238
        distances[Point(x = 165, y = 17) to Point(x = 11, y = 31)] = 232
        distances[Point(x = 165, y = 17) to Point(x = 177, y = 31)] = 26
        distances[Point(x = 165, y = 17) to Point(x = 43, y = 33)] = 182
        distances[Point(x = 165, y = 17) to Point(x = 175, y = 1)] = 26
        distances[Point(x = 1, y = 19) to Point(x = 1, y = 19)] = 0
        distances[Point(x = 1, y = 19) to Point(x = 11, y = 31)] = 38
        distances[Point(x = 1, y = 19) to Point(x = 177, y = 31)] = 252
        distances[Point(x = 1, y = 19) to Point(x = 43, y = 33)] = 72
        distances[Point(x = 1, y = 19) to Point(x = 175, y = 1)] = 252
        distances[Point(x = 11, y = 31) to Point(x = 11, y = 31)] = 0
        distances[Point(x = 11, y = 31) to Point(x = 177, y = 31)] = 246
        distances[Point(x = 11, y = 31) to Point(x = 43, y = 33)] = 66
        distances[Point(x = 11, y = 31) to Point(x = 175, y = 1)] = 246
        distances[Point(x = 177, y = 31) to Point(x = 177, y = 31)] = 0
        distances[Point(x = 177, y = 31) to Point(x = 43, y = 33)] = 196
        distances[Point(x = 177, y = 31) to Point(x = 175, y = 1)] = 44
        distances[Point(x = 43, y = 33) to Point(x = 43, y = 33)] = 0
        distances[Point(x = 43, y = 33) to Point(x = 175, y = 1)] = 196
        distances[Point(x = 175, y = 1) to Point(x = 175, y = 1)] = 0
    }

    override fun solve2(lines: List<String>) {
        val grid = MatrixString.build(splitLines(lines))

        var start = Point(0, 0)
        val locations = mutableMapOf<Int, Point>()
        for (point in grid.allPoints()) {
            val value = grid.get(point)
            if (value.isNumeric()) {
                val number = value.toInt()
                if (number == 0) {
                    start = point
                } else {
                    locations[number] = point
                }
            }
        }
        val allLocations = locations.values + start

        // Calculate the shortest distances between all locations
        val distances: MutableMap<Pair<Point, Point>, Int> = mutableMapOf()
        prefill(distances)
        for ((index, location) in allLocations.withIndex()) {
            println("Calculating distance ${index + 1} / ${allLocations.size}")
            for (otherLocation in allLocations) {
                if (location != otherLocation) {
                    val cache = distances[location to otherLocation]
                    if (cache == null) {
                        val previouslyCalculated = distances[otherLocation to location]
                        if (previouslyCalculated != null) {
                            distances[location to otherLocation] = previouslyCalculated
                        } else {
                            val matrix = MatrixInt.buildForShortestPath(grid, "#")
                            val dist = matrix.shortestPath(location, otherLocation)
                            distances[location to otherLocation] = dist
                        }
                    } else {
                        distances[otherLocation to location] = cache
                    }
                }
            }
        }

        // Sum the shortest distance to visit all
        val permutations = getPermutations(locations.keys.toList())
        var minDistance = Int.MAX_VALUE
        for (permutation in permutations) {
            var distance = 0
            var from = start
            for (pos in permutation) {
                val to = locations[pos]!!
                distance += distances[from to to]!!
                from = to
            }
            // End back at start
            distance += distances[from to start]!!
            minDistance = minOf(minDistance, distance)
        }
        println(minDistance)
    }
}
