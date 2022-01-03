package com.rolf.day11

import com.rolf.Day
import com.rolf.util.getCombinations

fun main() {
    Day11().run()
}

class Day11 : Day() {
    override fun getDay(): Int {
        return 11
    }

    override fun solve1(lines: List<String>) {
        val floor1 = Floor(mutableSetOf(1, -1, 2, 3))
        val floor2 = Floor(mutableSetOf(-2, -3))
        val floor3 = Floor(mutableSetOf(4, -4, 5, -5))
        val floor4 = Floor(mutableSetOf())
        val building = Building(listOf(floor1, floor2, floor3, floor4))

        println(building)
        println(findMoves(building))
    }

    override fun solve2(lines: List<String>) {
        val floor1 = Floor(mutableSetOf(1, -1, 2, 3, -6, 6, -7, 7))
        val floor2 = Floor(mutableSetOf(-2, -3))
        val floor3 = Floor(mutableSetOf(4, -4, 5, -5))
        val floor4 = Floor(mutableSetOf())
        val building = Building(listOf(floor1, floor2, floor3, floor4))

        println(findMoves(building))
    }

    private fun findMoves(building: Building, count: Int = 0): Int {
        val moves = building.getPossibleMoves()

        for (move in moves) {
            if (move.allOnTopFloor()) {
                return count + 1
            }
            val result = findMoves(move, count + 1)
            if (result > 0) {
                return result
            }
        }
        return 0
    }
}

class Building(private val floors: List<Floor>) {
    private var elevator = 0

    private fun isFloorOk(): Boolean {
        val floor = floors[elevator]

        // Chip (-) must be with only other chips or be with its generator
        if (floor.hasOnlyChips()) return true
        return floor.allChipsHaveGenerators()
    }

    fun allOnTopFloor(): Boolean {
        val objectCount = floors.map { it.objects.size }.sum()
        val topFloorObjectCount = floors[3].objects.size
        return objectCount == topFloorObjectCount
    }

    fun getPossibleMoves(): List<Building> {

        // Lowest floor or floor below is empty: move 2 items up
        return if (elevator == 0 || floors[elevator - 1].objects.isEmpty()) {
            getMoves(elevator, elevator + 1, 2)
        }
        // Otherwise move 1 item down
        else {
            getMoves(elevator, elevator - 1, 1)
        }
    }

    private fun getMoves(fromFloor: Int, toFloor: Int, itemsToMove: Int? = null): List<Building> {
        val result = mutableListOf<Building>()
        val combinations =
            getCombinations(floors[fromFloor].objects.toList())
                .filter { if (itemsToMove == null) it.size <= 2 else it.size == itemsToMove }
        for (combination in combinations) {
            val copy = copy()
            val from = copy.floors[fromFloor]
            val to = copy.floors[toFloor]
            for (toMove in combination) {
                from.move(toMove, to)
            }
            copy.elevator = toFloor
            if (copy.isFloorOk()) {
                result.add(copy)
            }
        }
        return result
    }

    private fun copy(): Building {
        return Building(floors.map { it.copy() })
    }

    override fun toString(): String {
        return "Building(floors=$floors, elevator=$elevator)"
    }
}

class Floor(val objects: MutableSet<Int>) {
    fun move(obj: Int, to: Floor) {
        objects.remove(obj)
        to.objects.add(obj)
    }

    private fun getChips(): List<Int> {
        return objects.filter { it < 0 }
    }

    private fun getGenerators(): List<Int> {
        return objects.filter { it > 0 }
    }

    private fun hasGenerator(chip: Int): Boolean {
        return objects.contains(-chip)
    }

    fun hasOnlyChips(): Boolean {
        return getGenerators().isEmpty()
    }

    fun allChipsHaveGenerators(): Boolean {
        for (chip in getChips()) {
            if (!hasGenerator(chip)) {
                return false
            }
        }
        return true
    }

    fun copy(): Floor {
        return Floor(objects.toMutableSet())
    }

    override fun toString(): String {
        return "Floor(objects=$objects)"
    }
}
