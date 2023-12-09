fun main() {
	fun generate(maxTime: Int, record: Long): List<Long> = (1..<maxTime).map { time ->
		(maxTime - time) * time.toLong()
	}.filter { it > record }

	fun part1(input: Map<Int, Int>): Int {
		return input.map { (maxTime, record) -> generate(maxTime, record.toLong()) }.fold(1) { acc, ints -> acc * ints.count() }
	}

	fun part2(input: Map<Int, Long>): Long {
		return input.map { (maxTime, record) -> generate(maxTime, record) }.fold(1L) { acc, ints -> acc * ints.count() }
	}

	val testInput = mapOf(7 to 9, 15 to 40, 30 to 200)
	val testInput2 = mapOf(71530 to 940200)
	check(part1(testInput) == 288)
	check(part1(testInput2) == 71503)

	val input = mapOf(63 to 411, 78 to 1274, 94 to 2047, 68 to 1035)
	val input2 = mapOf(63789468 to 411127420471035)
	part1(input).println()
	part2(input2).println()
}
