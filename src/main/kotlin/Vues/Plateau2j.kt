package Vues

import Modele.Joueur
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.text.Font
import java.io.FileInputStream
import kotlin.random.Random

class Plateau2j() : Plateau() {
    val partieHaut : HBox
//    var joueurHaut : Label
    var joueurHaut2 : Label
    val pilePickoHaut : Label
    val volPickoHaut : Button

    init {
        // PARTIE HAUT :
        this.partieHaut = HBox()
        this.partieHaut.alignment = Pos.CENTER
        // joueur haut :
//        this.joueurHaut = Label()
        this.joueurHaut2 = Label("Joueur 2")
        this.joueurHaut2.style = "-fx-background-color: #31BED1; -fx-background-radius: 5px;"
        this.joueurHaut2.padding = Insets(10.0, 10.0, 10.0, 10.0)
        this.joueurHaut2.font = Font.font("Tahoma", 22.0)
        this.partieHaut.children.add(joueurHaut2)
        // pile pickomino joueur haut :
        this.pilePickoHaut = Label()
        pilePickoHaut.padding = Insets(0.0, 30.0, 0.0, 30.0)
        val inputPickoHaut = FileInputStream("images/pickominos/picko0.png")
        // pour petit ecran
//        val inputPickoHaut = FileInputStream("images/pickominos/petitpickos/picko0.png")
        val imagePickoHaut = Image(inputPickoHaut)
        this.pilePickoHaut.graphic = ImageView(imagePickoHaut)
        this.partieHaut.children.add(pilePickoHaut)
        // bouton vol pickomino
        this.volPickoHaut = Button("←     Voler ce pickomino !")
        this.volPickoHaut.isVisible = false
        this.volPickoHaut.padding = Insets(10.0, 20.0, 10.0, 20.0)
        this.volPickoHaut.style = "-fx-background-radius: 5px;"
        this.partieHaut.children.add(volPickoHaut)
        // ajout de la partie haut dans la vue
        this.top = this.partieHaut
    }
}