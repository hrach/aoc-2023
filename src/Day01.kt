fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            val digits = line.filter { it.isDigit() }
            val num = digits.first().toString() + digits.last()
            num.toInt()
        }

    fun part2(input: List<String>): Int {
        val words = mapOf(
            "1" to 1,
            "2" to 2,
            "3" to 3,
            "4" to 4,
            "5" to 5,
            "6" to 6,
            "7" to 7,
            "8" to 8,
            "9" to 9,
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )
        return input.sumOf { line ->
            val a = line.findAnyOf(words.keys)!!
            val b = line.findLastAnyOf(words.keys)!!
            (words[a.second].toString() + words[b.second].toString()).toInt()
        }.also(::println)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_test2")
    check(part1(testInput) == 142)
    check(part2(testInput2).also(::println) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
