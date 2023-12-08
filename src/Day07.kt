fun main() {
    data class Hand(
        val cards: String
    )

    class Part1Comparator : Comparator<Pair<Hand, Int>> {
        private fun score(cards: String): Int {
            val counts = cards.groupingBy { it }.eachCount().values.sortedDescending()
            return when {
                counts[0] == 5 -> 7
                counts[0] == 4 -> 6
                counts[0] == 3 && counts[1] == 2 -> 5
                counts[0] == 3 && counts[1] == 1 -> 4
                counts[0] == 2 && counts[1] == 2 -> 3
                counts[0] == 2 -> 2
                else -> 1
            }
        }

        private fun letterScore(c: Char): Int = when (c) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 11
            'T' -> 10
            else -> c.digitToInt()
        }

        override fun compare(one: Pair<Hand, Int>, two: Pair<Hand, Int>): Int {
            val score = score(one.first.cards)
            val otherScore = score(two.first.cards)
            if (score != otherScore) return score.compareTo(otherScore)
            for (i in 0..4) {
                val a = letterScore(one.first.cards[i])
                val b = letterScore(two.first.cards[i])
                if (a != b) return a.compareTo(b)
            }
            error("shouldn't happen")
        }
    }

    class Part2Comparator : Comparator<Pair<Hand, Int>> {
        private fun score(cards: String): Int {
            val counts = cards.replace("J", "").groupingBy { it }.eachCount().values.sortedDescending().toMutableList()
            if (counts.isEmpty()) counts.add(0)
            counts[0] += cards.count { it == 'J' }
            return when {
                counts[0] == 5 -> 7
                counts[0] == 4 -> 6
                counts[0] == 3 && counts[1] == 2 -> 5
                counts[0] == 3 && counts[1] == 1 -> 4
                counts[0] == 2 && counts[1] == 2 -> 3
                counts[0] == 2 -> 2
                else -> 1
            }
        }

        private fun letterScore(c: Char): Int = when (c) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 1
            'T' -> 10
            else -> c.digitToInt()
        }

        override fun compare(one: Pair<Hand, Int>, two: Pair<Hand, Int>): Int {
            val score = score(one.first.cards)
            val otherScore = score(two.first.cards)
            if (score != otherScore) return score.compareTo(otherScore)
            for (i in 0..4) {
                val a = letterScore(one.first.cards[i])
                val b = letterScore(two.first.cards[i])
                if (a != b) return a.compareTo(b)
            }
            error("shouldn't happen")
        }
    }

    fun parse(input: List<String>): List<Pair<Hand, Int>> =
        input.map {
            val (chars, bid) = it.split(' ')
            Hand(chars) to bid.toInt()
        }

    fun part1(input: List<String>): Int {
        val data = parse(input).sortedWith(Part1Comparator())
        return data.mapIndexed { index, pair ->
            (index + 1) * pair.second
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val data = parse(input).sortedWith(Part2Comparator())
        return data.mapIndexed { index, pair ->
            (index + 1) * pair.second
        }.sum()
    }

    val testInput1 = readInput("Day07_test")
    check(part1(testInput1) == 6440)
    check(part2(testInput1) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
