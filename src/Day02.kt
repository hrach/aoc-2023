fun main() {
    data class RGB(
        val red: Int,
        val green: Int,
        val blue: Int,
    )

    data class Game(
        val id: Int,
        val rounds: List<RGB>
    ) {
        fun max(): RGB = RGB(
            red = rounds.maxOf { it.red },
            green = rounds.maxOf { it.green },
            blue = rounds.maxOf { it.blue },
        )
    }

    fun parse(input: List<String>): List<Game> = input.map { line ->
        val (prefix, rounds) = line.split(':')
        Game(
            id = prefix.substring(5).trim().toInt(),
            rounds = rounds.split(';').map { round ->
                RGB(
                    red = "(\\d+) red".toRegex().find(round)?.groupValues?.get(1)?.toInt() ?: 0,
                    green = "(\\d+) green".toRegex().find(round)?.groupValues?.get(1)?.toInt() ?: 0,
                    blue = "(\\d+) blue".toRegex().find(round)?.groupValues?.get(1)?.toInt() ?: 0,
                )
            }
        )
    }.also(::println)

    fun validate1(game: Game): Boolean =
        game.rounds.all {
            it.red <= 12 && it.green <= 13 && it.blue <= 14
        }

    fun part1(input: List<String>): Int =
        parse(input).filter { validate1(it) }.sumOf { it.id }

    fun part2(input: List<String>): Int =
        parse(input).sumOf {
            val max = it.max()
            max.red * max.green * max.blue
        }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val testInput2 = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput2).also(::println) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
