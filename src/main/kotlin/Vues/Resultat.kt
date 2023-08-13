package Vues

import Modele.Joueur
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.io.FileInputStream

class Resultat : GridPane() {

    private val titre : Label
    private val resultat : VBox
    val butonAccueil : Button
    private val borderPaneBuoutton : BorderPane


    init {
        this.background = Background(BackgroundImage(Image(FileInputStream("images/fonds/fondAccueil.png")), BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, BackgroundPosition.CENTER, BackgroundSize.DEFAULT))

        this.padding = Insets(100.0,0.0,0.0,0.0)

        titre = Label()
        this.titre.graphic = ImageView(Image(FileInputStream("images/textes/resultats.png")))
        titre.translateX = 80.0

//        titre.padding = Insets(50.0)
        this.add(titre,1,0)
        titre.alignment = Pos.CENTER

        this.resultat = VBox()
        resultat.padding = Insets(25.0)
        resultat.style = "-fx-text-fill: black ;  -fx-background-color : rgba(207,237,243, 0.7) ; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black;"
        this.add(resultat,1,1)

        this.butonAccueil = Button("Retourner à l'accueil")
        this.butonAccueil.style = "  -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-cursor: hand;"
        this.butonAccueil.minHeight = 50.0
        this.butonAccueil.minWidth = 200.0

        this.borderPaneBuoutton = BorderPane()
        this.add(borderPaneBuoutton,2,3)
        borderPaneBuoutton.right = butonAccueil

        this.borderPaneBuoutton.padding = Insets(0.0,30.0,30.0,0.0)
        this.borderPaneBuoutton.maxHeight = Double.MAX_VALUE

        var leftContraint = ColumnConstraints()
        leftContraint.percentWidth = 25.0
        this.columnConstraints.add(0,leftContraint)

        var centreContraint = ColumnConstraints()
        centreContraint.percentWidth = 50.0
        this.columnConstraints.add(1,centreContraint)

        var rightContraint = ColumnConstraints()
        rightContraint.percentWidth = 25.0
        this.columnConstraints.add(2,rightContraint)

        val r0 = RowConstraints(200.0,100.0, 200.0)
        this.rowConstraints.add(0,r0)
        val r1 = RowConstraints(400.0,100.0, 500.0)
        this.rowConstraints.add(1,r1)
        val r2 = RowConstraints(100.0,1000.0, Double.MAX_VALUE)
        this.rowConstraints.add(2,r2)
    }

    /**
     * Permet d'affihcer les résulttats de chaque joueur
     * @param joueurs : List<Joueur> : la liste des joueurs de la partie
     * @param resultat : List<Int> : la liste des poitns de chaque joueur
     */
    fun afficherLesResultats(joueurs : List<Joueur>, resultat : List<Int>) {
        val resultatTrie = trierLesListes(joueurs,resultat)
        var joueursTriee = resultatTrie.first
        var resultatTriee = resultatTrie.second

        // Atttribution des médailles et des rangs
        var rang = 1
        var points = resultat[0]
        var nb = 0
        for (i in 0 until resultatTriee.size) {
            var place = HBox()
            if (points != resultatTriee[i]) {
                rang += nb
                points = resultatTriee[i]
                nb = 1
            } else {
                nb += 1
            }
            if (rang in 1..3) {
                var medaille = ImageView(Image(FileInputStream("images/Résultat/medaille${rang}.png")))
                medaille.setFitHeight(60.0)
                medaille.setFitWidth(60.0)
                medaille.style = "-fx-padding: 100 0 0 5;"
                place.children.add(medaille)
            }
            val labelJoueur = Label()
            if (resultatTriee[i] != 0) {
                labelJoueur.text = "${joueursTriee[i].donneNom()} : ${resultatTriee[i]} points"
            } else {
                labelJoueur.text = "${joueursTriee[i].donneNom()} : ${resultatTriee[i]} point"
            }
            labelJoueur.font = Font.font(40.0)
            labelJoueur.padding = Insets(20.0)
            place.children.add(labelJoueur)
            place.alignment = Pos.CENTER
            this.resultat.children.add(place)
        }
    }

    /**
     * Trier la liste des résultats en changeant le positionnement des joueurs
     * @param joueurs : List<Joueurs> : la liste des joueurs
     * @param resultat : List<Int> : la liste des résultats
     * @return tuple :  Pair<MutableList<Joueur>, MutableList<Int>> : les deux liste respectivement
     */
    fun trierLesListes(joueurs : List<Joueur>, resultat : List<Int>) : Pair<MutableList<Joueur>, MutableList<Int>> {
        var resultatTriee = resultat.toMutableList()
        var joueursTriee = joueurs.toMutableList()
        for (i in 0 until resultatTriee.size) {
            var max = i
            for (j in i until resultatTriee.size) {
                if (resultatTriee[j] > resultatTriee[max]) {
                    max = j
                }
            }
            var maxRes = resultatTriee[max]
            resultatTriee[max] = resultatTriee[i]
            resultatTriee[i] = maxRes

            var maxJoueur = joueursTriee[max]
            joueursTriee[max] = joueursTriee[i]
            joueursTriee[i] = maxJoueur
        }

        return Pair(joueursTriee,resultatTriee)
    }
}