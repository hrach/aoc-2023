import kotlin.math.absoluteValue

fun main() {
	fun expand(map: List<Pair<Long, Long>>, times: Long): List<Pair<Long, Long>> {
		val setX = map.map { it.first }.toSet()
		val setY = map.map { it.second }.toSet()

		return map.map { (x, y) ->
			val emptyCountX = (0..<x).count { it !in setX }
			val emptyCountY = (0..<y).count { it !in setY }
			(x + emptyCountX * (times - 1)) to (y + emptyCountY * (times - 1))
		}
	}

	fun len(a: Pair<Long, Long>, b: Pair<Long, Long>): Long {
		return (a.first - b.first).absoluteValue +
			(a.second - b.second).absoluteValue
	}

	fun parse(input: List<String>): List<Pair<Long, Long>> = buildList {
		input.forEachIndexed { y, s ->
			s.forEachIndexed { x, c ->
				if (c == '#') add(x.toLong() to y.toLong())
			}
		}
	}

	fun calc(map: List<Pair<Long, Long>>): Long {
		val distances = buildList {
			for (a in 0..map.lastIndex) {
				for (b in (a + 1)..map.lastIndex) {
					val aa = map[a]
					val bb = map[b]
					add(len(aa, bb))
				}
			}
		}
		return distances.sum()
	}

	fun part1(input: List<String>, times: Long): Long {
		val map = expand(parse(input), times = times)
		return calc(map)
	}

	val testInput1 = readInput("Day11_test")
	check(part1(testInput1, times = 2) == 374L)
	check(part1(testInput1, times = 10).also(::println) == 1030L)
	check(part1(testInput1, times = 100).also(::println) == 8410L)

	val input = readInput("Day11")
	part1(input, times = 1).println()
	part1(input, times = 1_000_000).println()
}
