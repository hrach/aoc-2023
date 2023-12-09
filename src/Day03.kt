fun main() {
	fun part1(input: List<String>): Int {
		fun findSymbol(line: Int, num: IntRange): List<Char> {
			val length = input.first().length
			return listOfNotNull(
				input.getOrNull(line - 1)
					?.substring((num.first - 1).coerceAtLeast(0), (num.last + 2).coerceAtMost(length)),
				input[line].getOrNull(num.first - 1),
				input[line].getOrNull(num.last + 1),
				input.getOrNull(line + 1)
					?.substring((num.first - 1).coerceAtLeast(0), (num.last + 2).coerceAtMost(length)),
			).joinToString("").toList().filter { it != '.' }
		}

		val reg = """\d+""".toRegex()
		val allMatches = input.map { line ->
			reg.findAll(line)
		}
		val nums = allMatches.mapIndexed { lineIndex, matches ->
			matches.map { match ->
				match.value.toInt() to findSymbol(lineIndex, match.range)
			}
		}.flatMap { it }
		return nums.filter { it.second.isNotEmpty() }.sumOf { it.first }
	}

	fun part2(input: List<String>): Int {
		fun findSymbolOffset(line: Int, num: IntRange): String? {
			val x = (num.first - 1)..(num.last + 1)
			val y = (line - 1)..(line + 1)
			for (a in y) {
				for (b in x) {
					val char = input.getOrNull(a)?.getOrNull(b)
					if (char == '*') return "$a-$b"
				}
			}
			return null
		}

		val reg = """\d+""".toRegex()
		val allMatches = input.map { line ->
			reg.findAll(line)
		}
		val nums = allMatches.mapIndexed { lineIndex, matches ->
			matches.map { match ->
				match.value.toInt() to findSymbolOffset(lineIndex, match.range)
			}
		}.flatMap { it }
		return nums.filter { it.second != null }
			.groupBy { it.second!! }
			.values.map {
				if (it.size < 2) return@map 0
				it.fold(1) { acc, pair -> acc * pair.first }
			}
			.sum()
	}

	val testInput = readInput("Day03_test")
	check(part1(testInput) == 4361)
	check(part2(testInput) == 467835)

	val input = readInput("Day03")
	part1(input).println()
	part2(input).println()
}
