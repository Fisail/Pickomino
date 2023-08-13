import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.STATUS
import iut.info1.pickomino.exceptions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Classe contenant les tests concernant la classe Connector de la librairie pickomino-lib
 */
class   TestPickominoLibConnector {

    // Test de la méthode newGame
    @Test
    fun testNewGamePasAssezDeJoueurs() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(1)
        assertEquals(-1, gamesRes.first)
        assertEquals(-1, gamesRes.second)
    }

    @Test
    fun testNewGameCorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        assertNotEquals(-1, gamesRes.first)
        assertNotEquals(-1, gamesRes.second)
    }

    @Test
    fun testNewGameTropDeJoueurs() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(8)
        assertEquals(-1, gamesRes.first)
        assertEquals(-1, gamesRes.second)
    }

    // Test de la méthode
    @Test
    fun testRollDicesCorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        assertEquals(8, connect.rollDices(id,key).size)
    }

    @Test
    fun testRollDicesIdIncorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val key = gamesRes.second

        assertThrows<UnknownIdException> { connect.rollDices(-1, key) }
    }

    @Test
    fun testRollDicesKeyIncorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first

        assertThrows<IncorrectKeyException> { connect.rollDices(id, -1) }
    }

    @Test
    fun testRollDicesStepIncorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        connect.rollDices(id, key)
        assertThrows<BadStepException> { connect.rollDices(id, key) }
    }

    @Test
    fun testRollDicesTourRate() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id,key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm))
        connect.keepDices(id,key,DICE.worm)
        connect.takePickomino(id,key,25)
        assertFalse(25 in connect.gameState(id, key).accessiblePickos())

        connect.choiceDices(id,key, listOf(DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1))
        connect.keepDices(id,key,DICE.d1)
        assertEquals(0,connect.gameState(id,key).current.player)

        connect.choiceDices(id,key, listOf(DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1))
        connect.keepDices(id,key,DICE.d1)
        assertTrue(25 in connect.gameState(id, key).accessiblePickos())
        assertFalse(36 in connect.gameState(id,key).accessiblePickos())
    }

    @Test
    fun testRollDicesTourRateGradPickominoRemisEnJeu() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id,key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm))
        connect.keepDices(id,key,DICE.worm)
        connect.takePickomino(id,key,36)
        assertFalse(36 in connect.gameState(id, key).accessiblePickos())

        connect.choiceDices(id,key, listOf(DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1))
        connect.keepDices(id,key,DICE.d1)
        assertEquals(0,connect.gameState(id,key).current.player)

        connect.choiceDices(id,key, listOf(DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1))
        connect.keepDices(id,key,DICE.d1)
        assertTrue(36 in connect.gameState(id, key).accessiblePickos())
    }

    // Test de la méthode keepDices
    @Test
    fun testKeepDicesCorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.d1, DICE.worm))
        assertTrue(connect.keepDices(id, key, DICE.d1))
    }

    @Test
    fun testKeepDicesIdIncorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.d1, DICE.worm))
        assertThrows<UnknownIdException> { connect.keepDices(-1, key, DICE.d1) }
    }

    @Test
    fun testKeepDicesKeyIncorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.d1, DICE.worm))
        assertThrows<IncorrectKeyException> { connect.keepDices(id, -1, DICE.d1) }
    }

    @Test
    fun testKeepDicesStepIncorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        assertThrows<BadStepException> { connect.keepDices(id, key, DICE.worm) }
    }

    @Test
    fun testKeepDicesDesNonObtenu() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.d1, DICE.d4))
        assertThrows<DiceNotInRollException> { connect.keepDices(id, key, DICE.worm) }
    }

    @Test
    fun testKeepDicesDesDejaPris() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.d1))
        connect.keepDices(id, key, DICE.d1)

        connect.choiceDices(id, key, listOf(DICE.d1, DICE.worm, DICE.d3))
        assertThrows<DiceAlreadyKeptException> { connect.keepDices(id, key, DICE.d1) }
    }

    // Tests concernant la fonction TakePickomino
    @Test
    fun testTakePickominoIdIncorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(4)
        val key = gamesRes.second
        assertThrows<UnknownIdException> { connect.takePickomino(-1, key, 22) }
    }

    @Test
    fun testTakePickominoKeyIncorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(3)
        val id = gamesRes.first
        assertThrows<IncorrectKeyException> { connect.takePickomino(id, -1, 22) }
    }

    @Test
    fun testTakePickominoInexistant() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second
        assertFalse { connect.takePickomino(id, key, 150) }
        assertFalse { connect.takePickomino(id, key, 2) }

    }


    @Test
    fun testTakePickominoPasBonneEtape() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)

        val id = gamesRes.first
        val key = gamesRes.second
        STATUS.KEEP_DICE
        assertThrows<BadStepException> { connect.takePickomino(id, key, 23) }
    }


    @Test
    fun testTakePickominoDisponiblePlateau() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)

        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.d5, DICE.d5, DICE.d5, DICE.d2, DICE.worm))
        connect.keepDices(id,key, DICE.d5)
        connect.choiceDices(id, key, listOf(DICE.d2, DICE.worm))
        connect.keepDices(id,key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d2))
        connect.keepDices(id,key, DICE.d2)

        assertTrue() { 21 in connect.gameState(id,key).accessiblePickos() }


    }


        @Test
        fun testTakePickominoVolPickomino() {
            val connect = Connector.factory("172.26.82.76", "8080", true)
            val gamesRes = connect.newGame(2)

            val id = gamesRes.first
            val key = gamesRes.second

            connect.choiceDices(id, key, listOf(DICE.d5, DICE.d5, DICE.d5, DICE.d2, DICE.worm))
            connect.keepDices(id,key, DICE.d5)
            connect.choiceDices(id, key, listOf(DICE.d2, DICE.worm))
            connect.keepDices(id,key, DICE.worm)
            connect.choiceDices(id, key, listOf(DICE.d2))
            connect.keepDices(id,key, DICE.d2)

            connect.takePickomino(id, key, 22)



            connect.choiceDices(id, key, listOf(DICE.d5, DICE.d5, DICE.d5, DICE.d2, DICE.worm))
            connect.keepDices(id,key, DICE.d5)
            connect.choiceDices(id, key, listOf(DICE.d2, DICE.worm))
            connect.keepDices(id,key, DICE.worm)
            connect.choiceDices(id, key, listOf(DICE.d2))
            connect.keepDices(id,key, DICE.d2)

            assertTrue(){22 in connect.gameState(id,key).pickosStackTops()}
        }


        @Test
        fun testTakePickominoPasDeVersDe() {
            val connect = Connector.factory("172.26.82.76", "8080", true)
            val gamesRes = connect.newGame(2)

            val id = gamesRes.first
            val key = gamesRes.second

            connect.choiceDices(id, key, listOf(DICE.d5, DICE.d4, DICE.d2, DICE.d2, DICE.d5))
            assertFalse { connect.takePickomino(id, key, 18) }

        }

        // Test de la méthode gameState
        @Test
        fun testGameStateIdIncorrect() {
            val connect = Connector.factory("172.26.82.76", "8080", true)
            val gamesRes = connect.newGame(4)
            val key = gamesRes.second
            assertThrows<UnknownIdException> { connect.gameState(-1, key) }
        }

        @Test
        fun testGameStateKeyIncorrect() {
            val connect = Connector.factory("172.26.82.76", "8080", true)
            val gamesRes = connect.newGame(3)
            val id = gamesRes.first
            assertThrows<IncorrectKeyException> { connect.gameState(id, -1) }
        }

        @Test
        fun testGameStateRenvoieStatus() {
            val connect = Connector.factory("172.26.82.76", "8080", true)
            val gamesRes = connect.newGame(3)
            val id = gamesRes.first
            val key = gamesRes.second

            connect.choiceDices(id, key, listOf(DICE.d5, DICE.d4, DICE.d2, DICE.worm, DICE.worm, DICE.d3))

            assertEquals(STATUS.KEEP_DICE, connect.gameState(id, key).current.status)
        }


        //Test concernant la fonction choiceDices
        @Test
        fun testChoiceDiceIdIncorrect() {
            val connect = Connector.factory("172.26.82.76", "8080", true)
            val gamesRes = connect.newGame(2)

            val key = gamesRes.second
            assertThrows<UnknownIdException> { connect.choiceDices(-1, key, listOf(DICE.d1)) }
        }

        @Test
        fun testChoiceDiceKeyIncorrect() {
            val connect = Connector.factory("172.26.82.76", "8080", true)
            val gamesRes = connect.newGame(2)

            val id = gamesRes.first
            assertThrows<IncorrectKeyException> { connect.choiceDices(id, -1, listOf(DICE.d1)) }
        }

        @Test
        fun testChoiceDiceMauvaiseEtape() {
            val connect = Connector.factory("172.26.82.76", "8080", true)
            val gamesRes = connect.newGame(2)

            val id = gamesRes.first
            val key = gamesRes.second
            connect.choiceDices(id, key, listOf(DICE.d3, DICE.d3, DICE.d3, DICE.d3, DICE.d3, DICE.d3, DICE.d3, DICE.d3))
            assertThrows<BadStepException> { connect.choiceDices(id, key, listOf(DICE.d1)) }
        }

        @Test
        fun testChoiceDiceCorrect() {
            val connect = Connector.factory("172.26.82.76", "8080", true)
            val gamesRes = connect.newGame(2)

            val id = gamesRes.first
            val key = gamesRes.second

            val lancerDes =
                connect.choiceDices(
                    id,
                    key,
                    listOf(DICE.d3, DICE.d3, DICE.d3, DICE.d3, DICE.d3, DICE.d3, DICE.d3, DICE.d3)
                )
            assertEquals(connect.gameState(id, key).current.rolls, lancerDes)
        }

    // Test de la méthodfinalScore
    @Test
    fun testFinalScoreCorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        var pickomino = 36
        while (connect.gameState(id,key).current.status != STATUS.GAME_FINISHED) {
            connect.choiceDices(id,key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,))
            connect.keepDices(id,key,DICE.worm)
            connect.takePickomino(id, key, pickomino)
            pickomino -= 1

            connect.choiceDices(id,key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,))
            connect.keepDices(id,key,DICE.worm)
            connect.takePickomino(id, key, pickomino)
            pickomino -= 1
        }
        assertDoesNotThrow { connect.finalScore(id, key) }
        assertEquals(listOf(20, 20), connect.finalScore(id, key))
    }

    @Test
    fun testFinalScoreIdIncorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        assertThrows<UnknownIdException> { connect.finalScore(-1,key) }
    }

    @Test
    fun testFinalScoreKeyIncorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        assertThrows<IncorrectKeyException> { connect.finalScore(id,-1) }
    }

    @Test
    fun testFinalScoreStepIncorrect() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        assertThrows<BadStepException> { connect.finalScore(id,key) }
    }
}












