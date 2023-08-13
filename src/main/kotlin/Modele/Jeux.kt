package Modele

import Vues.ChoixJoueurs
import io.ktor.network.tls.*
import iut.info1.pickomino.Connector
import iut.info1.pickomino.data.DICE
import iut.info1.pickomino.data.Game

class Jeux(adresse : String, port : String, debug : Boolean) {
    private var id : Int
    private var cle : Int
    val connect : Connector
    private var joueurs : List<Joueur>
    private val dominos : MutableList<Domino>
    private var valDeSelec: String

    init {
        this.connect = Connector.factory(adresse,port,debug)
        println("Parties actives sur le serveur : ${connect.listOfGameIds()}")
        this.joueurs = mutableListOf()
        this.id = -1
        this.cle = -1
        this.dominos = mutableListOf<Domino>()
        for (i in 21 until 36) {
            dominos.add(Domino(i))
        }
        this.valDeSelec = ""
    }

    /**
     * Créer Une partie sur le serveur avec les joueurs passés en paramètre
     * @param joueurs: List<Joueur>
     * Et mettre à jour l'id et la clé du jeux
     */
    fun creerLaPartie(joueurs: List<Joueur>) {
        this.joueurs = joueurs
        val resultatConnexion = connect.newGame(this.joueurs.size)
        this.id = resultatConnexion.first
        this.cle = resultatConnexion.second
    }

    /**
     * Renvoie l'identifiant de la partie si elle est crée et -1 sinon
     * @return id : Int
     */
    fun donneId() : Int {
        return this.id
    }

    /**
     * Renvoie la clé de la partie si elle est créer et -1 sinon
     */
    fun donneCle() : Int {
        return this.cle
    }

    /**
     * Renvoie le joueur courant, celui qui joue actuellement
     * @return joueur : Joueur
     */
    fun donneJoueurCourant() : Joueur {
        var stat = connect.gameState(id,cle)
        return joueurs[stat.current.player]
    }

    /**
     * Renvoie la liste des joueur des joueurs de la partie
     * @return joueurs : List<Joueur>
     */
    fun donneJoueurs() : List<Joueur> {
        return joueurs
    }

    /**
     * Renvoie l'état du jeu actuel
     * @return etat : Game
     */
    fun etatDuJeux() : Game {
        return connect.gameState(id,cle)
    }

    /**
     * Revoie le score final de la partie, classé l'ordre de la liste des joueurs
     * @return List<Int>
     */
    fun scoreFinal() : List<Int> {
        return connect.finalScore(id,cle)
    }

    /**
     * Renvoie la liste contenant les identifiants des parties sur le serveur
     * @return List<Int>
     */
    fun listeDesPartiesLancees() : List<Int> {
        return connect.listOfGameIds()
    }

    /**
     * Faire un lancé de dés
     * @return resultat : MutableList<De>
     */
    fun lancerLesDes() : MutableList<De> {
        var resultatDeLancer = connect.rollDices(id,cle)
        var resultat = mutableListOf<De>()
        for (de in resultatDeLancer) {
            resultat.add(De(de))
        }
        return resultat
    }

    /**
     * Garder le domino pour le joueur courant
     * @param domino : Domino
     * @return estGardé : Boolean
     */
    fun prendreLeDomino(domino: Domino) : Boolean {
        this.dominos.remove(domino)
        return connect.takePickomino(id,cle,domino.donneValuer())
    }

    /**
     * Garder le Dé pour le joueur courant
     * @param de : De
     * @return estGardé : Boolean
     */
    fun garderLeDe(de: De) : Boolean {
        return connect.keepDices(id,cle,de.donneFaceActuel())
    }

    /**
     * Permet en mode debug, de choisir le résultat d'un lancé de dés
     * @param lesDes : List<De>
     */

    fun getDeSelec() : String{
        return this.valDeSelec
    }

    fun setDeSelec(de: String) {
        this.valDeSelec = de
    }

    fun choisirLesDesDeLaLance(lesDes : List<DICE>) : List<DICE> {
        return connect.choiceDices(id,cle,lesDes)
    }

    /**
     * Renvoie la liste des dominos disponibles dans la partie encore
     * @return listDomino : MutableList<Domino>
     */
    fun listDesDominosDisponible() : MutableList<Domino> {
        var resultat = connect.gameState(id,cle).accessiblePickos()
        var listDomino = mutableListOf<Domino>()
        for (valeur in resultat) {
            listDomino.add(Domino(valeur))
        }
        return listDomino
    }

    /**
     * Renvoie la sommet de la pile de chaque joueur sous la forme d'une liste. l'ordre est selon l'identifiant du joueur.
     * @return sommets : MutableList<Domino>
     */
    fun sommetPileDesJoueurs() : MutableList<Domino> {
        var resultat = connect.gameState(id,cle).pickosStackTops()
        var sommets = mutableListOf<Domino>()
        for (valeur in resultat) {
            sommets.add(Domino(valeur))
        }
        return sommets
    }

    /**
     * Renvoie la liste des dés gardé par le joueur courant
     * @return listDesDes : MutableList<De>
     */
    fun listDesDesGardes() : MutableList<De> {
        var resultat = connect.gameState(id,cle).current.keptDices
        var listDesDes = mutableListOf<De>()
        for (dice in resultat) {
            listDesDes.add(De(dice))
        }
        return listDesDes
    }
}