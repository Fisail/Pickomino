import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.STATUS
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Classe contenant les tests concernant la classe Game de la librairie pickomino-lib
 */
class TestPickominoLibGame {


    //Test concernant la fonction accessiblePickos
    @Test
    fun testAccessiblePickosRate(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)

        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4))
        connect.keepDices(id, key, DICE.d4)
        connect.choiceDices(id, key, listOf(DICE.worm))
        connect.keepDices(id, key, DICE.worm)
        connect.takePickomino(id,key,21)  //joueur 1 prend le pickomino 21
        assertTrue(21 !in connect.gameState(id,key).accessiblePickos())

        connect.choiceDices(id,key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm)) //joueur 2
        connect.keepDices(id,key,DICE.worm)
        connect.takePickomino(id,key,25)

        connect.choiceDices(id,key, listOf(DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))  //joueur 1 rate son tour
        connect.keepDices(id,key,DICE.d1)

        assertTrue(21 in connect.gameState(id,key).accessiblePickos())
        assertTrue(36 !in connect.gameState(id,key).accessiblePickos())
    }

    @Test
    fun testAccessiblePickosRatePlusGrand(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)

        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm))
        connect.keepDices(id, key, DICE.worm)
        connect.choiceDices(id, key, listOf(DICE.d1))
        connect.keepDices(id, key, DICE.d1)
        connect.takePickomino(id,key,36)  //joueur 1 prend le pickomino 21
        assertTrue(36 !in connect.gameState(id,key).accessiblePickos())

        connect.choiceDices(id,key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm)) //joueur 2
        connect.keepDices(id,key,DICE.worm)
        connect.takePickomino(id,key,25)

        connect.choiceDices(id,key, listOf(DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1))  //joueur 1 rate son tour
        connect.keepDices(id,key,DICE.d1)

        assertTrue(36 in connect.gameState(id,key).accessiblePickos())
        assertTrue(35 in connect.gameState(id,key).accessiblePickos())
    }

    @Test
    fun testAccessiblePickosRetire(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)

        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4))
        connect.keepDices(id, key, DICE.d4)
        connect.choiceDices(id, key, listOf(DICE.worm))
        connect.keepDices(id, key, DICE.worm)
        connect.takePickomino(id,key,21)  //joueur 1 prend le pickomino 21
        assertTrue(21 !in connect.gameState(id,key).accessiblePickos())
    }

    //Test concernant la fonction pickosStackTops
    @Test
    fun testPickoStackTopsCorrect(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)

        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4))
        connect.keepDices(id, key, DICE.d4)
        connect.choiceDices(id, key, listOf(DICE.worm))
        connect.keepDices(id, key, DICE.worm)
        connect.takePickomino(id,key,21)  //joueur 1 prend le pickomino 21

        connect.choiceDices(id,key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm)) //joueur 2
        connect.keepDices(id,key,DICE.worm)
        connect.takePickomino(id,key,25) //joueur 2 prend le pickomino 25

        assertEquals(21,connect.gameState(id,key).pickosStackTops()[0])
        assertEquals(25,connect.gameState(id,key).pickosStackTops()[1])
    }
    @Test
    fun testPickoStackTopsVol(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)

        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4))
        connect.keepDices(id, key, DICE.d4)
        connect.choiceDices(id, key, listOf(DICE.worm))
        connect.keepDices(id, key, DICE.worm)
        connect.takePickomino(id,key,21)

        connect.choiceDices(id,key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm))
        connect.keepDices(id,key,DICE.worm)
        connect.takePickomino(id,key,25)

        assertEquals(21,connect.gameState(id,key).pickosStackTops()[0])
        assertEquals(25,connect.gameState(id,key).pickosStackTops()[1])

        connect.choiceDices(id,key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm))
        connect.keepDices(id,key,DICE.worm)
        connect.takePickomino(id,key,25) //joueur 1 vole le pickomino 25 à joueur 1

        assertEquals(25,connect.gameState(id,key).pickosStackTops()[0])
        assertEquals(0,connect.gameState(id,key).pickosStackTops()[1])
    }

    @Test
    fun testPickoStackTopsRate(){
        val connect = Connector.factory("172.26.82.76", "8080", true)
        val gamesRes = connect.newGame(2)

        val id = gamesRes.first
        val key = gamesRes.second

        connect.choiceDices(id, key, listOf(DICE.d4, DICE.d4, DICE.d4, DICE.d4))
        connect.keepDices(id, key, DICE.d4)
        connect.choiceDices(id, key, listOf(DICE.worm))
        connect.keepDices(id, key, DICE.worm)
        connect.takePickomino(id,key,21)

        connect.choiceDices(id,key, listOf(DICE.worm, DICE.worm, DICE.worm, DICE.worm, DICE.worm))
        connect.keepDices(id,key,DICE.worm)
        connect.takePickomino(id,key,25)

        assertEquals(21,connect.gameState(id,key).pickosStackTops()[0])

        connect.choiceDices(id,key, listOf(DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1, DICE.d1)) //joueur 2
        connect.keepDices(id,key,DICE.d1)

        assertEquals(0, connect.gameState(id,key).pickosStackTops()[0])

    }
}