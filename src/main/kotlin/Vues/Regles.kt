package Vues

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import java.io.FileInputStream

class Regles() : VBox() {
    private val titre : Label
    private val titreDeroul : Label
    private val textDeroul: Label
    private val titrePerdre : Label
    private val textPerdre : Label
    private val titreFin : Label
    private val textFin : Label
    private val desPickos: HBox
    var boutonRetour : Button

    init {
        this.background = Background(BackgroundImage(Image(FileInputStream("images/fonds/fondAccueil.png")), BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, BackgroundPosition.CENTER, BackgroundSize.DEFAULT))
        // titre règles
        this.titre = Label()
        this.titre.graphic = ImageView(Image(FileInputStream("images/textes/regles.png")))
        // déroulement d'un tour
        // titre
        this.titreDeroul = Label("Déroulement d'un tour")
        this.titreDeroul.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 25.0)
        this.titreDeroul.style = "-fx-text-fill: black"
        // texte
        this.textDeroul = Label("Le jeu se déroule au tour par tour. À chaque tour, chaque joueur dispose de 8 dés à 6 faces où la face du 6 est" +
                "remplacé par un vers. Le joueur qui joue lance ses dés, et choisi une des valeur obtenue.\nIl garde tout les dés qui correspondent à " +
                "cette valeur. Il peut alors relancer ses dés restants et choisir une autre valeur (différente que celle(s) choisie(nt) précédemment). " +
                "Attention ! Le joueur doit garder au moins un vers dans tous ses dés.\n\n Si la somme de la valeur des dés gardés est supérieure à 20, " +
                "le joueur peut prendre un pickomino qui correspond cette somme (sachant que les vers vallent 5). Ce pickomino est placé devant le joueur " +
                "(pour former une pile avec les suivants).\n\n Si la somme correspond exactement à la valeur d'un pickomino au sommet de la pile d'un autre " +
                "joueur, alors il est possible de voler ce pickomino, et ainsi l'ajouter à sa pile.\n\n Si la somme correspond à un pickomino qui n'est pas " +
                "visible alors le joueur a la possibilité de prendre le premier pickomino visible inférieur à cette valeur (ex: j'ai obtenu 25 avec mes dés, " +
                "les pickominos 25 et 24 ne sont pas visibles alors\n je prends le pickomino 23).\n")
        this.textDeroul.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
        this.textDeroul.style = "-fx-text-fill: black ;  -fx-background-color : rgba(207,237,243, 0.7) ; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black;"
        this.textDeroul.padding = Insets(15.0)

        // perdre un pickomino
        // titre
        this.titrePerdre = Label("Perdre un pickomino")
        this.titrePerdre.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 25.0)
        this.titrePerdre.style = "-fx-text-fill: black"
        // texte
        this.textPerdre = Label("Un joueur rate son tour si :\n" +
                "       - Il n'a pas obtenu de vers à la fin de ses lancés de dés\n" +
                "       - Il n'a obtenu que des valeurs déjà sélectionnées sur un lancé de dé\n" +
                "       - Il a lancé tous ses dés et ne peut pas récupérer de pickomino\n\n" +
                "Dans ces cas là, le joueur remet le dernier pickomino sa pile au centre (si il en a un), et le pickomino sur la table avec la plus grande " +
                "valeur est retourné. Si le pickomino remis au centre est celui qui a la plus grande valeur alors rien ne se passe.\n\n")
        this.textPerdre.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
        this.textPerdre.style = "-fx-text-fill: black ;  -fx-background-color : rgba(207,237,243, 0.7) ; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black;"
        this.textPerdre.padding = Insets(15.0)

        // fin de la partie
        // titre
        this.titreFin = Label("Fin de partie")
        this.titreFin.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 25.0)
        this.titreFin.style = "-fx-text-fill: black"
        // texte
        this.textFin = Label("Le jeu se termine une fois qu'il n'y a plus de pickomino au centre. " +
                "Les joueurs comptent le nombre de vers sur leurs pickominos, et celui qui a le plus de vers a gagné.\n\n")
        this.textFin.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
        this.textFin.style = "-fx-text-fill: black ;  -fx-background-color : rgba(207,237,243, 0.7) ; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black;"
        this.textFin.padding = Insets(15.0)

        // bouton retour
        this.boutonRetour = Button("Retour")
        this.boutonRetour.font = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 30.0)
        this.boutonRetour.style = "  -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black; -fx-cursor: hand;"
        this.boutonRetour.padding = Insets(15.0)
        this.boutonRetour.minHeight = 50.0
        this.boutonRetour.minWidth = 250.0
        this.boutonRetour.translateY = -18.0

        // dés et pickominos exemples
        this.desPickos = HBox()

        val de1 = Label()
        val inputde1 = FileInputStream("images/des/de6.png")
        val imagede1 = Image(inputde1)
        de1.graphic = ImageView(imagede1)
        val textde1 = Label("Dé normal")
        textde1.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
        textde1.style = "-fx-text-fill: black ;  -fx-background-color : rgba(207,237,243, 0.7) ; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black;"
        textde1.padding = Insets(5.0)

        val dev1 = VBox()
        dev1.children.addAll(de1,textde1)
        dev1.alignment = Pos.CENTER
        dev1.spacing = 10.0

        val de2 = Label()
        val inputde2 = FileInputStream("images/des/de6.png")
        val imagede2 = Image(inputde2)
        de2.graphic = ImageView(imagede2)
        de2.effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
        val textde2 = Label("Dé sélectionné")
        textde2.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
        textde2.style = "-fx-text-fill: black ;  -fx-background-color : rgba(207,237,243, 0.7) ; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black;"
        textde2.padding = Insets(5.0)

        val dev2 = VBox()
        dev2.children.addAll(de2,textde2)
        dev2.alignment = Pos.CENTER
        dev2.spacing = 10.0

        val de3 = Label()
        val inputde3 = FileInputStream("images/des/de6.png")
        val imagede3 = Image(inputde3)
        de3.graphic = ImageView(imagede3)
        de3.isDisable = true
        val textde3 = Label("Dé non sélectionnable")
        textde3.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
        textde3.style = "-fx-text-fill: black ;  -fx-background-color : rgba(207,237,243, 0.7) ; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black;"
        textde3.padding = Insets(5.0)

        val dev3 = VBox()
        dev3.children.addAll(de3,textde3)
        dev3.alignment = Pos.CENTER
        dev3.spacing = 10.0

        val picko1 = Label()
        val inputpicko1 = FileInputStream("images/pickominos/picko36.png")
        val imagepicko1 = Image(inputpicko1)
        picko1.graphic = ImageView(imagepicko1)
        val textPicko1 = Label("Pickomino normal")
        textPicko1.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
        textPicko1.style = "-fx-text-fill: black ;  -fx-background-color : rgba(207,237,243, 0.7) ; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black;"
        textPicko1.padding = Insets(5.0)

        val pickov1 = VBox()
        pickov1.children.addAll(picko1,textPicko1)
        pickov1.alignment = Pos.CENTER
        pickov1.spacing = 10.0

        val picko2 = Label()
        val inputpicko2 = FileInputStream("images/pickominos/picko36.png")
        val imagepicko2 = Image(inputpicko2)
        picko2.graphic = ImageView(imagepicko2)
        picko2.effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
        val textPicko2 = Label("Pickomino disponible")
        textPicko2.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
        textPicko2.style = "-fx-text-fill: black ;  -fx-background-color : rgba(207,237,243, 0.7) ; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black;"
        textPicko2.padding = Insets(5.0)

        val pickov2 = VBox()
        pickov2.children.addAll(picko2,textPicko2)
        pickov2.alignment = Pos.CENTER
        pickov2.spacing = 10.0

        val picko3 = Label()
        val inputpicko3 = FileInputStream("images/pickominos/picko36.png")
        val imagepicko3 = Image(inputpicko3)
        picko3.graphic = ImageView(imagepicko3)
        picko3.isDisable = true
        val textPicko3 = Label("Pickomino retourné")
        textPicko3.font = Font.font("Tahoma", FontPosture.REGULAR, 15.0)
        textPicko3.style = "-fx-text-fill: black ;  -fx-background-color : rgba(207,237,243, 0.7) ; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black;"
        textPicko3.padding = Insets(5.0)

        val pickov3 = VBox()
        pickov3.children.addAll(picko3,textPicko3)
        pickov3.alignment = Pos.CENTER
        pickov3.spacing = 10.0

        desPickos.children.addAll(dev1, dev2, dev3, pickov1, pickov2, pickov3)
        desPickos.alignment = Pos.CENTER
        desPickos.spacing = 25.0
        desPickos.padding = Insets(30.0,0.0,0.0,0.0)

        this.children.addAll(titre, titreDeroul, textDeroul, titrePerdre, textPerdre, titreFin, textFin, desPickos, boutonRetour)

        this.spacing = 15.0
        this.padding = Insets(0.0, 0.0, 0.0, 18.0)
    }
}