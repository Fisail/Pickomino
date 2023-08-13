import iut.info1.pickomino.data.DICE
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * Classe contenant les tests concernant la classe DICE de la librairie pickomino-lib
 */
class TestPickominoDICE {

    /**
     * Test de l'attribu ordinal
     */
    @ParameterizedTest
    @MethodSource("DtTestDICEordinal")
    fun testDICEordinal(de : DICE, oracle : Int) {
        assertEquals(oracle, de.ordinal)
    }

    companion object{
        @JvmStatic
        fun DtTestDICEordinal() : Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(DICE.d1, 0),
                Arguments.of(DICE.d2, 1),
                Arguments.of(DICE.d3, 2),
                Arguments.of(DICE.d4, 3),
                Arguments.of(DICE.d5, 4),
                Arguments.of(DICE.worm, 5)
            )
        }
    }
}