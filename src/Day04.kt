import kotlin.math.pow

fun main() {
	fun part1(input: List<String>): Int {
		return input.sumOf { line ->
			val (winning, having) = line.substringAfter(':').split(" | ")
				.map { it.trim().split("\\s+".toRegex()).map { it.toInt() } }
			val same = winning.intersect(having).count()
			if (same == 0) 0 else 2.0.pow(same - 1).toInt()
		}
	}

	fun part2(input: List<String>): Int {
		val wins = input.map { line ->
			val (winning, having) = line.substringAfter(':').split(" | ")
				.map { it.trim().split("\\s+".toRegex()).map { it.toInt() } }
			winning.intersect(having).count()
		}
		val counts = List(input.size) { 1 }.toMutableList()
		for (i in input.indices) {
			for (y in 1..wins[i]) {
				counts[i + y] += counts[i]
			}
		}
		return counts.sum()
	}

	val testInput = readInput("Day04_test")
	check(part1(testInput) == 13)
	check(part2(testInput) == 30)

	val input = readInput("Day04")
	part1(input).println()
	part2(input).println()
}
