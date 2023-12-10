fun main() {
	@Suppress("SelfAssignment", "ReplaceWithOperatorAssignment")
	fun findLength(data: List<List<Char>>, ofrom: Char, ox: Int, oy: Int): Pair<Int, Set<Pair<Int, Int>>> {
		var x = ox
		var y = oy
		var from = ofrom
		val points = mutableSetOf(x to y)
		var l = 1
		while (true) {
			when (val char: Char = data[y][x]) {
				'|' -> when (from) {
					'S' -> { from = 'S'; x = x; y = y - 1 }
					'N' -> { from = 'N'; x = x; y = y + 1 }
					else -> error(from)
				}
				'-' -> when (from) {
					'W' -> { from = 'W'; x = x + 1; y = y }
					'E' -> { from = 'E'; x = x - 1; y = y }
					else -> error(from)
				}
				'L' -> when (from) {
					'N' -> { from = 'W'; x = x + 1; y = y }
					'E' -> { from = 'S'; x = x; y = y - 1 }
					else -> error(from)
				}
				'J' -> when (from) {
					'N' -> { from = 'E'; x = x - 1; y = y }
					'W' -> { from = 'S'; x = x; y = y - 1 }
					else -> error(from)
				}
				'7' -> when (from) {
					'W' -> { from = 'N'; x = x; y = y + 1 }
					'S' -> { from = 'E'; x = x - 1; y = y }
					else -> error(from)
				}
				'F' -> when (from) {
					'E' -> { from = 'N'; x = x; y = y + 1 }
					'S' -> { from = 'W'; x = x + 1; y = y }
					else -> error(from)
				}
				'S' -> {
					points.add(x to y)
					return l to points
				}
				else -> error(char)
			}
			points.add(x to y)
			l += 1
		}
	}

	fun part1(input: List<String>, sChar: Char): Int {
		val data = input.map { it.toList() }
		val s = input.joinToString("").indexOf('S')
		val len = input.first().length
		val sX = s.mod(len)
		val sY = (s - sX) / len

		return when (sChar) {
			'F' -> findLength(data, 'W', ox = sX + 1, oy = sY).first / 2
			'J' -> findLength(data, 'S', ox = sX, oy = sY - 1).first / 2
			else -> error(sChar)
		}
	}

	fun isInside(x: Int, y: Int, path: Set<Pair<Int, Int>>, data: List<List<Char>>, sChar: Char): Boolean {
		var inside = false
		var enterChar: Char? = null
		for (i in 0..x) {
			val r = (i to y) in path
			val c = data[y][i]
			if (r) {
				when (c) {
					'|' -> inside = !inside
					'-' -> {} // no-op
					else -> {
						if (enterChar == null) {
							enterChar = if (c == 'S') sChar else c
						} else {
							if ((enterChar == 'F' && c == '7') || (enterChar == 'L' && c == 'J')) {
								// leave block
								enterChar = null
							} else {
								inside = !inside
								enterChar = null
							}
						}
					}
				}
			}
		}
		return inside
	}

	fun part2(input: List<String>, sChar: Char): Int {
		val data = input.map { it.toList() }
		val s = input.joinToString("").indexOf('S')
		val len = input.first().length
		val sX = s.mod(len)
		val sY = (s - sX) / len
		val path = when (sChar) {
			'F' -> findLength(data, 'W', ox = sX + 1, oy = sY).second
			'J' -> findLength(data, 'S', ox = sX, oy = sY - 1).second
			'7' -> findLength(data, 'N', ox = sX, oy = sY + 1).second
			else -> error(sChar)
		}

		var t = 0
		input.forEachIndexed { y, line ->
			line.forEachIndexed { x, c ->
				if ((x to y) !in path && isInside(x, y, path, data, sChar)) t += 1
			}
		}
		return t
	}

	val testInput1 = readInput("Day10_test")
	val testInput2 = readInput("Day10_test2")
	val testInput3 = readInput("Day10_test3")
	val testInput4 = readInput("Day10_test4")
	check(part1(testInput1, 'F') == 8)
	check(part2(testInput2, 'F') == 4)
	check(part2(testInput3, 'F') == 8)
	check(part2(testInput4, '7') == 10)

	val input = readInput("Day10")
	part1(input, 'J').println()
	part2(input, 'J').println()
}
