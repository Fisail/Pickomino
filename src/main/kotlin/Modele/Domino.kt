package Modele

import javafx.scene.image.Image
import java.lang.Exception

class Domino(valeur: Int) {
    private val valeur : Int
    private var nombreDeVers : Int
    private val image : String

    init {
        this.valeur = valeur
        if (valeur in 21..24) {
            this.nombreDeVers = 1
        } else if (valeur in 25..28) {
            this.nombreDeVers = 2
        } else if (valeur in 29..32) {
            this.nombreDeVers = 3
        } else if (valeur in 33..36) {
            this.nombreDeVers = 4
        } else {
            this.nombreDeVers = 0
        }
        this.image = "images/pickominos/picko${valeur}.png"
    }

    /**
     * Renvoie la valeur du domino
     * @return valeur : Int
     */
    fun donneValuer() : Int {
        return this.valeur
    }

    /**
     * Renvoie le nombre de vers présent sur le domino
     * @return nombreDeVers : Int
     */
    fun donneNombreDeVers() : Int {
        return this.nombreDeVers
    }

    /**
     * Retourner le Domino
     */
    fun retourneLeDomino() {
        TODO()
    }
}