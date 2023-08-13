import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Classe contenant les tests concernant la classe State de la librairie pickomino-lib
 */
class TestPickominoLibState {
    @Test
    fun testStateRolls() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        Assertions.assertEquals(
            listOf<DICE>(),
            connect.gameState(id, key).current.rolls,
            "Au début la liste doit être vide."
        )

        var res = connect.choiceDices(id,key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.d5))
        Assertions.assertEquals(
            res,
            connect.gameState(id, key).current.rolls,
            "Après un lancé, la liste doit représenter les dés obtenus."
        )

        connect.keepDices(id,key, DICE.worm)
        connect.takePickomino(id,key,25)
        Assertions.assertEquals(
            listOf<DICE>(),
            connect.gameState(id, key).current.rolls,
            "Après qu'un joueur choisi de garder des dés, la liste doit se réinitialiser."
        )

        connect.choiceDices(id,key, listOf(DICE.d1))
        connect.keepDices(id,key, DICE.d1)
        connect.choiceDices(id,key, listOf(DICE.d1))
        Assertions.assertEquals(
            listOf<DICE>(),
            connect.gameState(id, key).current.rolls,
            "Suite à un tour raté, la liste doit se réinitialiser."
        )
    }

    @Test
    fun testStateKeptDices() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)
        val id = gamesRes.first
        val key = gamesRes.second

        Assertions.assertEquals(
            listOf<DICE>(),
            connect.gameState(id, key).current.keptDices,
            "Au début du tour la liste est vide"
        )

        connect.choiceDices(id,key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d1,DICE.d5,DICE.d5))
        connect.keepDices(id,key,DICE.worm)
        Assertions.assertEquals(
            listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm),
            connect.gameState(id, key).current.keptDices,
            "Après qu'un joueur à choisi ses dés, la liste doit se mettre à jour."
        )

        connect.takePickomino(id,key,25)
        Assertions.assertEquals(
            listOf<DICE>(),
            connect.gameState(id, key).current.keptDices,
            "A la fin du tour, la liste doit se réinitialiser."
        )
    }

    @Test
    fun testPlayerId() {
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(3)
        val id = gamesRes.first
        val key = gamesRes.second

        Assertions.assertEquals(
            0,
            connect.gameState(id, key).current.player,
            "Au début de la partie, c'est joueur avec l'id = 0 qui doit commencer."
        )

        connect.choiceDices(id,key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.worm,DICE.d1,DICE.d5,DICE.d5))
        connect.keepDices(id,key,DICE.worm)
        connect.takePickomino(id,key,25)

        Assertions.assertEquals(
            1,
            connect.gameState(id, key).current.player,
            "Après avoir choisi un pickomino, c'est au joueur suivant de jouer."
        )


        connect.choiceDices(id,key, listOf(DICE.worm,DICE.worm,DICE.worm,DICE.d1,DICE.d5,DICE.d5))
        connect.keepDices(id,key,DICE.worm)
        connect.choiceDices(id,key, listOf(DICE.worm,DICE.worm,DICE.worm))

        Assertions.assertEquals(
            2,
            connect.gameState(id, key).current.player,
            "Après un tour raté, c'est au joueur suivant de joueur."
        )
    }
}