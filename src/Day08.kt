fun main() {

    fun parse(input: List<String>): Pair<String, Map<String, Pair<String, String>>> {
        val commands = input.first().trim()
        return commands to input.drop(2).associate {
            it.substring(0..2) to Pair(
                it.substring(7..9),
                it.substring(12..14)
            )
        }
    }

    fun part1(input: List<String>): Int {
        val (commands, map) = parse(input)
        var state = "AAA"
        var i = 0
        do {
            val c = commands[i.mod(commands.length)]
            state = if (c == 'L') map[state]!!.first else map[state]!!.second
            i++
        } while (state != "ZZZ")
        return i
    }

    fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun part2(input: List<String>): Long {
        val (commands, map) = parse(input)
        val allGames = map.keys.filter { it.endsWith("A") }
        val finish = allGames.map { stateStart ->
            var state = stateStart
            var i = 0
            do {
                val c = commands[i.mod(commands.length)]
                state = if (c == 'L') map[state]!!.first else map[state]!!.second
                i++
            } while (!state.endsWith("Z"))
            i.toLong()
        }
        return finish.reduce { acc, i -> findLCM(acc, i) }
    }

    val testInput1 = readInput("Day08_test")
    val testInput2 = readInput("Day08_test2")
    val testInput3 = readInput("Day08_test3")
    check(part1(testInput1) == 2)
    check(part1(testInput2) == 6)
    check(part2(testInput3) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
