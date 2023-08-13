package Vues

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import java.io.FileInputStream

class Accueil() :VBox() {
    private val titre : Label
    val boutonJouer : Button
    val boutonRegles : Button

    init {
        // propriétés de la VBox
        this.background = Background(BackgroundImage(Image(FileInputStream("images/fonds/fondAccueil.png")), BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, BackgroundPosition.CENTER, BackgroundSize.DEFAULT))
        this.alignment = Pos.CENTER
        this.spacing = 10.0
        // titre
        this.titre = Label()
        this.titre.graphic = ImageView(Image(FileInputStream("images/textes/titre.png")))
        this.titre.translateY = -240.0
//         pour petit ecran
//        this.titre.translateY = -160.0
        // bouton "Jouer"
        this.boutonJouer = Button("Jouer")
        this.boutonJouer.style = "  -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-cursor: hand;"
        this.boutonJouer.minHeight = 100.0
        this.boutonJouer.minWidth = 405.0
        this.boutonJouer.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 50.0)
        this.boutonJouer.translateY = 220.0
        // pour petit ecran
//        this.boutonJouer.translateY = 140.0
        // bouton "Regles"
        this.boutonRegles = Button("Règles du jeu")
        this.boutonRegles.style = " -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-cursor: hand;"
        this.boutonRegles.minHeight = 50.0
        this.boutonRegles.minWidth = 300.0
        this.boutonRegles.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 30.0)
        this.boutonRegles.translateY = 220.0
        // pour petit ecran
//        this.boutonRegles.translateY = 140.0
        // ajout des éléments dans la VBox
        this.children.addAll(titre, boutonJouer, boutonRegles)
    }
}