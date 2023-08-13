package Modele

var idExterne : Int = 0

val couleurs : Array<String> = arrayOf("", "#9555C8", "#31BED1", "#E68936", "ABD60B")

class Joueur(nom : String) {
    private var nom : String
    private val id : Int
    private val couleur : String

    init {
        this.nom = nom
        this.id = idExterne
        idExterne += 1
        this.couleur = couleurs[id]
    }

    /**
     * Renvoie l'identifiant du joueur
     * @return id : Int
     */
    fun donneId() : Int {
        return this.id
    }

    /**
     * Renvoie le nom du joueur
     * @return nom : String
     */
    fun donneNom() : String {
        return this.nom
    }

    /**
     * Renvoie la couleur du joueur
     * @return couleur : String
     */
    fun donneCouleur() : String {
        return this.couleur
    }
}