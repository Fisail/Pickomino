package Vues

import Modele.Joueur
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import java.io.FileInputStream
import kotlin.math.absoluteValue

class ChoixJoueurs() : BorderPane() {
    private var partieTitre: BorderPane
    private var partieJoueurs : VBox
    var partieJoueursOrigine : VBox
    var partieJoueurs1Label : HBox
    var choixNomJoueur1 : TextField
    var partieJoueurs2Label : HBox
    var choixNomJoueur2 : TextField
    var partieJoueurs3Label : HBox
    var choixNomJoueur3 : TextField
    var partieJoueurs4Label : HBox
    var choixNomJoueur4 : TextField
    val bouttonAjouter : Button
    val bouttonSupprimer : Button
    var compteur : Int
    private var bouttons: BorderPane
    val bouttonRetour : Button
    val bouttonCommencer : Button
    var nbJoueurs : Int
    var tabJoueurs : MutableList<String>

    init {
        // propriétés du BorderPane
        this.background = Background(BackgroundImage(Image(FileInputStream("images/fonds/fondAccueil.png")), BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, BackgroundPosition.CENTER, BackgroundSize.DEFAULT))
        // initialisation des variables
        this.tabJoueurs = mutableListOf()
        this.nbJoueurs = 2
        this.compteur = 2
        // titre
        this.partieTitre = BorderPane()
        val titre = Label()
        titre.graphic = ImageView(Image(FileInputStream("images/textes/choix_joueurs.png")))
        titre.translateY = 20.0
        titre.translateX = 40.0
        this.partieTitre.left = titre
        // bouttons
        this.bouttons = BorderPane()
        // boutton "retour"
        this.bouttonRetour = Button("Retour")
        this.bouttonRetour.minHeight = 50.0
        this.bouttonRetour.minWidth = 250.0
        this.bouttonRetour.font = Font.font("sans_serif", FontWeight.BOLD, FontPosture.REGULAR, 30.0)
        this.bouttonRetour.padding = Insets(15.0)
        this.bouttonRetour.style = "  -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-cursor: hand;"

        setMargin(bouttonRetour, Insets(20.0))
        this.bouttons.left = bouttonRetour
        // boutton "commencer"
        this.bouttonCommencer = Button("Commencer ")
        this.bouttonCommencer.minHeight = 50.0
        this.bouttonCommencer.minWidth = 250.0
        this.bouttonCommencer.font = Font.font("sans_serif", FontWeight.BOLD, FontPosture.REGULAR, 30.0)
        this.bouttonCommencer.padding = Insets(15.0)
        this.bouttonCommencer.style ="  -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-cursor: hand;"

        setMargin(bouttonCommencer, Insets(20.0))
        this.bouttons.right = bouttonCommencer
        // partie joeurs
        this.partieJoueurs = VBox()
        // joueur 1
        this.partieJoueurs1Label = HBox()
        val labelJoueur1 = Label("Joueur 1 :")
        labelJoueur1.font = Font.font("sans_serif", FontWeight.BOLD, FontPosture.REGULAR, 45.0)

        val choixNomJoueur1 = TextField()
        this.choixNomJoueur1 = choixNomJoueur1
        this.choixNomJoueur1.minHeight = 40.0
        this.choixNomJoueur1.minWidth = 120.0
        this.choixNomJoueur1.font = Font.font("sans_serif", FontPosture.REGULAR, 20.0)
        this.partieJoueurs1Label.spacing = 10.0
        this.partieJoueurs1Label.alignment = Pos.CENTER
        this.partieJoueurs1Label.children.addAll(labelJoueur1, choixNomJoueur1)
        partieJoueurs1Label.style = "-fx-background-color: #9555C8; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-border-width: 2;"
        partieJoueurs1Label.maxWidth = 580.0
        partieJoueurs1Label.padding = Insets(15.0)

        // joueur 2
        this.partieJoueurs2Label = HBox()
        val labelJoueur2 = Label("Joueur 2 :")
        labelJoueur2.font = Font.font("sans_serif", FontWeight.BOLD, FontPosture.REGULAR, 45.0)
        val choixNomJoueur2 = TextField()
        this.choixNomJoueur2 = choixNomJoueur2
        this.choixNomJoueur2.minHeight = 40.0
        this.choixNomJoueur2.minWidth = 120.0
        this.choixNomJoueur2.font = Font.font("sans_serif", FontPosture.REGULAR, 20.0)
        this.partieJoueurs2Label.spacing = 10.0
        this.partieJoueurs2Label.alignment = Pos.CENTER
        this.partieJoueurs2Label.children.addAll(labelJoueur2, choixNomJoueur2)
        partieJoueurs2Label.style = "-fx-background-color: #31BED1; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-border-width: 2;"
        partieJoueurs2Label.maxWidth = 580.0
        partieJoueurs2Label.padding = Insets(15.0)

        // joueur 3
        this.partieJoueurs3Label = HBox()
        val labelJoueur3 = Label("Joueur 3 :")
        labelJoueur3.font = Font.font("sans_serif", FontWeight.BOLD, FontPosture.REGULAR, 45.0)
        val choixNomJoueur3 = TextField()
        this.choixNomJoueur3 = choixNomJoueur3
        this.choixNomJoueur3.minHeight = 40.0
        this.choixNomJoueur3.minWidth = 120.0
        this.choixNomJoueur3.font = Font.font("sans_serif", FontPosture.REGULAR, 20.0)
        this.partieJoueurs3Label.spacing = 10.0
        this.partieJoueurs3Label.alignment = Pos.CENTER
        this.partieJoueurs3Label.children.addAll(labelJoueur3, choixNomJoueur3)
        partieJoueurs3Label.style = "-fx-background-color: #E68936; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-border-width: 2;"
        partieJoueurs3Label.maxWidth = 580.0
        partieJoueurs3Label.padding = Insets(15.0)



        // joueur 4
        this.partieJoueurs4Label = HBox()
        val labelJoueur4 = Label("Joueur 4 :")
        labelJoueur4.font = Font.font("sans_serif", FontWeight.BOLD, FontPosture.REGULAR, 45.0)
        val choixNomJoueur4 = TextField()
        this.choixNomJoueur4 = choixNomJoueur4
        this.choixNomJoueur4.minHeight = 40.0
        this.choixNomJoueur4.minWidth = 120.0
        this.choixNomJoueur4.font = Font.font("sans_serif", FontPosture.REGULAR, 20.0)
        this.partieJoueurs4Label.spacing = 10.0
        this.partieJoueurs4Label.alignment = Pos.CENTER
        this.partieJoueurs4Label.children.addAll(labelJoueur4, choixNomJoueur4)
        partieJoueurs4Label.style = "-fx-background-color: #ABD60B; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-border-width: 2;"
        partieJoueurs4Label.maxWidth = 580.0
        partieJoueurs4Label.padding = Insets(15.0)


        // boutton ajouter
        this.bouttonAjouter = Button("Ajouter un joueur")

        this.bouttonAjouter.minHeight = 50.0
        this.bouttonAjouter.minWidth = 300.0
        this.bouttonAjouter.font =  Font.font("sans_serif", FontWeight.BOLD, FontPosture.REGULAR, 20.0)
        this.bouttonAjouter.style = "  -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-cursor: hand;"

        // boutton supprimer
        this.bouttonSupprimer = Button("Supprimer un joueur")

        this.bouttonSupprimer.minHeight = 50.0
        this.bouttonSupprimer.minWidth = 300.0
        this.bouttonSupprimer.font =  Font.font("sans_serif", FontWeight.BOLD, FontPosture.REGULAR, 20.0)
        this.bouttonSupprimer.isDisable = true
        this.bouttonSupprimer.style = "  -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-cursor: hand;"

        // 2 joueurs qu'on voit au début
        this.partieJoueursOrigine = VBox()
        this.partieJoueursOrigine.children.addAll(partieJoueurs1Label, partieJoueurs2Label)
        this.partieJoueursOrigine.alignment = Pos.CENTER
        this.partieJoueursOrigine.spacing = 20.0
        // pour petit ecran
//        this.partieJoueursOrigine.spacing = 5.0
        // partie joueurs totale
        this.partieJoueurs.children.addAll(partieJoueursOrigine, bouttonAjouter, bouttonSupprimer)
        this.partieJoueurs.alignment = Pos.CENTER
        this.partieJoueurs.spacing = 50.0
        // ajout des parties dans le BorderPane
        this.top = partieTitre
        this.center = partieJoueurs
        this.bottom = bouttons



    }

    fun retourneListeJoueurs(): MutableList<String> {
        for (i in 0 until tabJoueurs.size){
            if (tabJoueurs[i] == ""){
                tabJoueurs[i] = "Joueur ${i+1}"
            }
        }
        return tabJoueurs
    }

    fun retourneJoueurs() : MutableList<Joueur> {
        val joueurs = mutableListOf<Joueur>()
        for (i in 0 until nbJoueurs) {
            joueurs.add(Joueur(retourneListeJoueurs()[i]))
        }
        return joueurs
    }

    fun ajouterNomJoueur(index : Int, nom : String){
        tabJoueurs.add(index, nom)
    }


    var nb = 1
    fun changementNomJoueur(nom: TextField) {
        val pseudo = nom.text
        if (nom.length > 15) {
            nom.text = ""
            for (i in 0 until 15) {
                nom.text += (pseudo)[i]
            }
        } else if (nom.length == 0) {
            nom.text = "Joueur ${nb}"
            nb += 1
        }
    }
}