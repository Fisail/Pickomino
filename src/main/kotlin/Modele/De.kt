package Modele

import iut.info1.pickomino.data.DICE
import javafx.scene.image.Image

class De(dice: DICE) {
    private var valeur : Int
    private var image : String
    private var face : DICE
    var marque : Boolean
    private val lesFaces : List<DICE>

    init {
        this.valeur = dice.ordinal+1
        this.face = dice
        this.image = "images/des/de${dice.ordinal+1}.png"
        this.marque = false
        this.lesFaces = listOf(DICE.d1,DICE.d2,DICE.d3,DICE.d4,DICE.d5,DICE.worm)
    }

    /**
     * Renvoie la valeur de la face actuel
     * @return face : DICE
     */
    fun donneFaceActuel() : DICE {
        return this.face
    }

    /**
     * Revoie le chemin de l'image dans le projet
     * @return chemin : String
     */
    fun donneImage() : String {
        return this.image
    }

    /**
     * Change la face actuel du dé
     * @param face : String
     */
    fun changeLaFace(face : DICE) {
        this.face = face
        this.valeur = face.ordinal+1
        this.image = "images/dés/de/de${face.ordinal+1}.png"
    }
}