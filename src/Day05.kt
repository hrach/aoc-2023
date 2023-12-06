fun main() {
	data class Input(
		val map: List<List<Pair<LongRange, LongRange>>>,
	)

	fun parse(input: List<String>): Input {
		val maps = mutableListOf<List<Pair<LongRange, LongRange>>>()
		var map = mutableListOf<Pair<LongRange, LongRange>>()
		for (line in input.drop(2)) {
			if (line.contains("map")) {
				if (map.isNotEmpty()) maps.add(map)
				map = mutableListOf()
				continue
			} else if (line.isEmpty()) {
				continue
			} else {
				val (d, s, l) = line.split(' ').map { it.toLong() }
				map.add(s..<(s + l) to d..<(d + l))
			}
		}
		maps.add(map)
		return Input(maps)
	}

	fun map(data: Input, seed: Long): Long {
		var num = seed
		for (map in data.map) {
			mappingMap@ for ((from, to) in map) {
				if (num in from) {
					val pos = num - from.first
					num = to.first + pos
					break@mappingMap
				}
			}
		}
		return num
	}

	fun map2(data: Input, seed: LongRange): Long {
		var num: List<LongRange> = listOf(seed)
		for (map in data.map) {
			val newNum = mutableListOf<LongRange>()
			val todo = num.toMutableList()
			while (todo.isNotEmpty()) {
				val range= todo.removeFirst()
				var mapped = false
				mappingMap@for ((from, to) in map) {
					if (range.first in from && range.last in from) {
						// <1, 10> -> <101, 110>
						// range: <4, 8>
						// pos = 4 - 1 => 3
						// len = 8 - 4 => 4
						// add-> 101+3=104..101+3+4=108
						val pos = range.first - from.first
						val len = range.last - range.first
						newNum.add(to.first + pos..(to.first + pos + len))
						mapped = true
						break@mappingMap
					} else if (range.first in from) {
						// <1, 10> -> <101, 110>
						// range: <4, 12>
						// pos = 4 - 1 => 3
						// len = 10 - 4 => 6
						// add-> 101+3=104..101+3+6=110
						//    -> 4+6+1=11..12
						val pos = range.first - from.first
						val len = from.last - range.first
						newNum.add(to.first + pos..(to.first + pos + len))
						todo.add(range.first + len + 1..range.last)
						mapped = true
						break@mappingMap
					} else if (range.last in from) {
						// <1, 10> -> <101, 110>
						// range: <0, 4>
						// len = 4 - 1 => 3
						// add-> 101..101+3=104
						//    -> 0..<1
						val len = range.last - from.first
						newNum.add(to.first..(to.first + len))
						todo.add(range.first..<from.first)
						mapped = true
						break@mappingMap
					} else {
						// no-op
					}
				}
				if (!mapped) newNum.add(range)
				num = newNum
			}
		}
		return num.minOf { it.first }
	}

	fun part1(input: List<String>): Long {
		val seeds = input.first().substring(7).split(' ').map { it.toLong() }
		val data = parse(input)
		val seed = seeds.minOf { seed -> map(data, seed) }
		return seed
	}

	fun part2(input: List<String>): Long {
		val seeds = input.first().substring(7).split(' ').chunked(2)
			.map { (a, b) -> a.toLong()..<(a.toLong() + b.toLong()) }
		val data = parse(input)
		val seed = seeds.minOf { seed -> map2(data, seed) }
		return seed
	}

	val testInput = readInput("Day05_test")
	check(part1(testInput) == 35L)
	check(part2(testInput) == 46L)

	val input = readInput("Day05")
	part1(input).println()
	part2(input).println()
}
