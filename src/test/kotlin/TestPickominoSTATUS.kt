import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.STATUS
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Classe contenant les tests concernant la classe STATUS de la librairie pickomino-lib
 */
class TestPickominoSTATUS {
    @Test
    fun TestSTATUS() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        assertEquals(STATUS.ROLL_DICE, connect.gameState(id, key).current.status, "Etape \"dés à lancer\" du tour courant")

        connect.choiceDices(id,key, listOf(DICE.d3,DICE.d3,DICE.d3,DICE.d3,DICE.d3,DICE.d3,DICE.d1))
        assertEquals(STATUS.KEEP_DICE,connect.gameState(id,key).current.status, "Etape \"dés à prendre\" du tour courant")
        connect.keepDices(id,key,DICE.d3)

        connect.choiceDices(id,key, listOf(DICE.worm))
        connect.keepDices(id,key,DICE.worm)

        assertEquals(STATUS.ROLL_DICE_OR_TAKE_PICKOMINO,connect.gameState(id, key).current.status,"Etape \"dés à lancer ou pickomino à prendre\" du tour courant")

        connect.choiceDices(id,key, listOf(DICE.d1))
        connect.keepDices(id,key,DICE.d1)

        assertEquals(STATUS.TAKE_PICKOMINO,connect.gameState(id,key).current.status, "Etape \"Pickomo à prendre\" du tour courant")
        connect.takePickomino(id,key,24)

        connect.choiceDices(id,key, listOf(DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d2))
        connect.keepDices(id,key,DICE.d1)
        connect.choiceDices(id,key, listOf(DICE.d1))

        for (i in 21..35) {
            connect.choiceDices(id,key, listOf(DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d1,DICE.d2))
            connect.keepDices(id,key,DICE.d1)
            connect.choiceDices(id,key, listOf(DICE.d1))
        }

        assertEquals(STATUS.GAME_FINISHED, connect.gameState(id,key).current.status, "Etape \"Partie terminée\"")
    }
}