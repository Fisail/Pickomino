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

class Plateau3j() : Plateau() {
    val partieGauche : VBox
    var joueurGauche : Label
    val pilePickoGauche : Label
    val volPickoGauche : Button


    val partieDroite : VBox
    var joueurDroite : Label
    val pilePickoDroite : Label
    val volPickoDroite : Button


    init {
        // PARTIE GAUCHE :
        this.partieGauche = VBox()
        this.partieGauche.alignment = Pos.CENTER
        this.joueurGauche = Label("Joueur 2")
        this.joueurGauche.style = "-fx-background-color: #31BED1; -fx-background-radius: 5px;"
        this.joueurGauche.padding = Insets(10.0, 10.0, 10.0, 10.0)
        this.joueurGauche.translateX = 20.0
        this.joueurGauche.font = Font.font("Tahoma", 22.0)
        this.partieGauche.children.add(joueurGauche)
        // pile pickomino joueur gauche :
        this.pilePickoGauche = Label()
        this.pilePickoGauche.padding = Insets(30.0, 0.0, 0.0, 35.0)
        val inputPickoGauche = FileInputStream("images/pickominos/picko0.png")
        // pour petit ecran
//        val inputPickoGauche = FileInputStream("images/pickominos/petitpickos/picko0.png")
        val imagePickoGauche = Image(inputPickoGauche)
        this.pilePickoGauche.graphic = ImageView(imagePickoGauche)
        this.partieGauche.children.add(pilePickoGauche)
        // bouton vol pickomino
        this.volPickoGauche = Button("↖     Voler ce pickomino !")
        this.volPickoGauche.isVisible = false
        this.volPickoGauche.padding = Insets(10.0, 20.0, 10.0, 20.0)
        this.volPickoGauche.style = "-fx-background-radius: 5px;"
        this.partieGauche.children.add(volPickoGauche)
        this.left = this.partieGauche


        // PARTIE DROITE :
        this.partieDroite = VBox()
        this.partieDroite.minWidth = 200.0
        this.partieDroite.alignment = Pos.CENTER
        // joueur droite :
        this.joueurDroite = Label("Joueur 3")
        this.joueurDroite.style = "-fx-background-color: #ABD60B; -fx-background-radius: 5px;"
        this.joueurDroite.padding = Insets(10.0, 10.0, 10.0, 10.0)
        this.joueurDroite.translateX = -20.0
        this.joueurDroite.font = Font.font("Tahoma", 22.0)
        this.partieDroite.children.add(joueurDroite)
        // pile pickomino joueur droite :
        this.pilePickoDroite = Label()
        this.pilePickoDroite.padding = Insets(30.0, 35.0, 0.0, 0.0)
        val inputPickoDroite = FileInputStream("images/pickominos/picko0.png")
        // pour petit ecran
//        val inputPickoDroite = FileInputStream("images/pickominos/petitpickos/picko0.png")
        val imagePickoDroite = Image(inputPickoDroite)
        this.pilePickoDroite.graphic = ImageView(imagePickoDroite)
        this.partieDroite.children.add(pilePickoDroite)
        // bouton vol pickomino
        this.volPickoDroite = Button("Voler ce pickomino !    ↗")
        this.volPickoDroite.isVisible = false
        this.volPickoDroite.padding = Insets(10.0, 20.0, 10.0, 20.0)
        this.volPickoDroite.style = "-fx-background-radius: 5px;"
        this.partieDroite.children.add(volPickoDroite)
        // ajout de la partie droite dans la vue
        this.right = this.partieDroite
    }
}