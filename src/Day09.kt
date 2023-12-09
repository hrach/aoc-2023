fun main() {
	fun sums(data: List<Int>): List<List<Int>> = buildList {
		add(data)
		while (!last().all { it == 0 }) {
			add(last().zipWithNext { a, b -> b - a })
		}
	}

	fun nextNumber(sums: List<List<Int>>): Int =
		sums.map { it.last() }.reversed().reduce { acc, i -> acc + i }

    fun nextNumber2(sums: List<List<Int>>): Int =
        sums.map { it.first() }.reversed().reduce { acc, i -> i - acc }

	fun part1(input: List<String>): Int {
		return input.map {
			it.split(' ').map { it.toInt() }
		}.sumOf { row ->
			val sums = sums(row)
			nextNumber(sums)
		}
	}

	fun part2(input: List<String>): Int {
        return input.map {
            it.split(' ').map { it.toInt() }
        }.sumOf { row ->
            val sums = sums(row)
            nextNumber2(sums)
        }
	}

	val testInput1 = readInput("Day09_test")
	check(part1(testInput1) == 114)
	check(part2(testInput1) == 2)

	val input = readInput("Day09")
	part1(input).println()
	part2(input).println()
}
