package ejektaflex.bountiful.config

import ejektaflex.bountiful.api.config.IBountifulConfig
import ejektaflex.bountiful.api.ext.clampTo
import java.io.File
import kotlin.math.max



data class ConfigFile(val folder: File) : KConfig(folder, "bountiful.cfg"), IBountifulConfig {

    override var maxBountiesPerBoard: Int = 12
    override var boardAddFrequency: Long = 40L
    override var boardLifespan: Int = 72000
    override var timeMultiplier: Double = 28.0
    override var cashInAtBountyBoard: Boolean = true
    override var rarityChance: Double = 0.27
    var bountyAmountMax = 2
    var bountyAmountMin = 1
    override var bountyTimeMin = 6000

    override val bountyAmountRange: IntRange
        get() = bountyAmountMin..bountyAmountMax

    override fun load() {

        maxBountiesPerBoard = config.get(
                CATEGORY_BOARD,
                "Max Bounties Per Board At A Time",
                17,
                "How many bounties should be on a bounty board at a given time. (Max: 27, Default: 17)"
        ).int.clampTo(1..27)

        boardAddFrequency = max(config.get(
                CATEGORY_BOARD,
                "New Bounty Frequency",
                2400,
                "How often, in ticks, new bounty should show up on the bounty board. (Min: 20, Default: 2400)"
        ).int, 20).toLong().clampTo(20L..Long.MAX_VALUE)

        boardLifespan = config.get(
                CATEGORY_BOARD,
                "Bounty on Board Lifespan",
                72000,
                "How long bounties stay on the board, at max (Bounties will be removed prematurely if board hits max bounties). (Default: 72000)"
        ).int.clampTo(10..Int.MAX_VALUE)


        // Bounty

        timeMultiplier = config.get(
                CATEGORY_BOUNTY,
                "Bounty Expiry Time Multiplier",
                28.0,
                "A general multiplier for how long you get to complete a bounty, based on bounty worth. (Default: 28.0)"
        ).double.clampTo(0.0, Double.MAX_VALUE)

        cashInAtBountyBoard = config.get(
                CATEGORY_BOUNTY,
                "Cash In At Bounty Board?",
                true,
                "By default (true), the user must cash in the bounty by right clicking on the bounty board. If false, you can right click with the item in hand."
        ).boolean

        rarityChance = config.get(
                CATEGORY_BOUNTY,
                "Rarity Increase Chance",
                0.27,
                "The chance, per level, for a bounty to increase in rarity (Default: 0.27)"
        ).double.clampTo(0.0, 1.0)

        bountyAmountMax = config.get(
                CATEGORY_BOUNTY,
                "Bounty Items Max",
                2,
                "The maximum number of items that a bounty could ask for (Default: 2)"
        ).int.clampTo(1..64)

        bountyAmountMin = config.get(
                CATEGORY_BOUNTY,
                "Bounty Items Min",
                1,
                "The minimum number of items that a bounty could ask for (Default: 1)"
        ).int.clampTo(1..64)

        bountyTimeMin = config.get(
                CATEGORY_BOUNTY,
                "Minimum Bounty Time",
                6000,
                "The minimum time, in ticks, that a bounty can take to complete (Default: 4800)"
        ).int.clampTo(10..Int.MAX_VALUE)


    }

    companion object {
        private const val CATEGORY_BOARD = "board"
        private const val CATEGORY_BOUNTY = "bounty"
    }

}
