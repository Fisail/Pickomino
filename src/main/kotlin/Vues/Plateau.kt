package Vues

import Modele.De
import Modele.Jeux
import Modele.Joueur
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.io.FileInputStream
import kotlin.random.Random

open class Plateau() : BorderPane() {
    var joueurs : List<Joueur>
    val partieBas : HBox
    var joueurBas : Label
    val pilePickoBas : Label
    val desGardes : HBox
    val bruitage : CheckBox

    val partieCentre : VBox
    val separationPickos : VBox
    val pickominos : HBox
    val plateauDes : GridPane
    var valDes : MutableList<Int>
    var listDes : MutableList<Label>
    var totalDe : Label
    var totDes: Int
    var oldNb: Int
    var taille : Int
    var dernierDe: Int
    var nbDes: Int
    var same: Boolean
    var same2: Boolean
    var deImp : MutableList<Int>
    val selectPicko : Button
    val boutonsDes : VBox
    val boutonLancer : Button
    val boutonConfirm : Button

    val lancerDes : HBox

    var bruitageOn : Boolean

    init {
        this.bruitage = CheckBox("Bruitage")
        this.bruitage.isSelected = true
        this.bruitageOn = true
        this.bruitage.font = Font.font("Tahoma", 15.0)
        this.bruitage.translateX = 50.0
        bruitage.style = "-fx-text-fill: black ;  -fx-background-color : rgba(207,237,243, 0.7) ;  -fx-background-radius: 10px;"
        bruitage.padding = Insets(10.0)
        this.joueurs = listOf()
        this.background = Background(BackgroundImage(Image(FileInputStream("images/fonds/fondPlateau.png")), BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, BackgroundPosition.CENTER, BackgroundSize.DEFAULT))
        // PARTIE BAS :
        this.partieBas = HBox()
        this.partieBas.alignment = Pos.CENTER
        // désactiver si petit écran
        this.partieBas.spacing = 10.0
        // joueur bas :
        this.joueurBas = Label()
        this.joueurBas.style = "-fx-background-color: #9555C8; -fx-border-radius: 10px; -fx-background-radius: 10px;"
        joueurBas.padding = Insets(15.0)

        this.joueurBas.font = Font.font("Tahoma", FontWeight.BOLD, 40.0)
        this.partieBas.children.add(joueurBas)
        // pile pickomino joueur bas :
        this.pilePickoBas = Label()
        this.pilePickoBas.padding = Insets(0.0, 20.0, 0.0, 20.0)
        val inputPickoBas = FileInputStream("images/pickominos/picko0.png")
        // pour petit ecran
//        val inputPickoBas = FileInputStream("images/pickominos/petitpickos/picko0.png")
        val imagePickoBas = Image(inputPickoBas)
        this.pilePickoBas.graphic = ImageView(imagePickoBas)
        this.pilePickoBas.graphic.rotate = 0.0
        this.partieBas.children.add(pilePickoBas)

        // partie dés
        this.desGardes = HBox()
        this.desGardes.spacing = 10.0
        this.desGardes.translateY = 45.0
        this.totDes = 0
        this.taille = 8
        this.dernierDe = 0
        this.nbDes = 0
        this.oldNb = 0
        this.same = false
        this.same2 = false
        this.deImp = mutableListOf()
        // création dés vides
        for (i in 1 until 9) {
            val de = Label()
            de.graphic = ImageView(Image(FileInputStream("images/des/de.png")))
            this.desGardes.children.add(de)
        }
        this.partieBas.children.add(desGardes)
        // total des dés
        this.totalDe = Label("Valeur des dés : $totDes")
        totalDe.style = "-fx-background-color: white; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: black;"

        totalDe.padding = Insets(8.0, 18.0, 8.0, 18.0)
        totalDe.font = Font.font("Tahoma", 25.0)
        totalDe.translateX = 20.0
        this.partieBas.children.addAll(totalDe, bruitage)
        // ajout de la partie bas dans la vue
        this.bottom = this.partieBas

        // PARTIE CENTRALE
        this.partieCentre = VBox()
        // désactiver si petit écran
        this.partieCentre.spacing = 50.0
        this.separationPickos = VBox()
        this.separationPickos.alignment = Pos.CENTER
        this.pickominos = HBox()
        this.selectPicko = Button("Choisir ce pickomino")
        this.selectPicko.isVisible = false
        this.selectPicko.style = "-fx-padding: 15px 40px; -fx-background-radius: 10px;"
        this.selectPicko.translateY = -10.0
        this.pickominos.maxWidth = Double.MAX_VALUE
        this.pickominos.alignment = Pos.CENTER
        this.pickominos.style = "-fx-padding: 50px, 0, 0, 0"
        // pour petit écran
//        pickominos.style = "-fx-padding: 20px, 0, 0, 0px"

        // pickominos sur la table
        for (i in 21 until (37)) {
            val picko = Label()
            val inputPicko = FileInputStream("images/pickominos/picko${i}.png")
            // pour petit ecran
//            val inputPicko = FileInputStream("images/pickominos/petitpickos/picko${i}.png")
            val imagePicko = Image(inputPicko)
            picko.graphic = ImageView(imagePicko)
            pickominos.children.add(picko)
        }
        this.separationPickos.children.addAll(pickominos, selectPicko)
        this.partieCentre.children.add(separationPickos)
        this.lancerDes = HBox()
        this.lancerDes.alignment = Pos.CENTER

        // boutons des dés
        this.boutonsDes = VBox()
        this.boutonsDes.alignment = Pos.CENTER
        this.boutonLancer = Button("Lancer les dés")
        this.boutonLancer.style = "-fx-padding: 10px 20px; -fx-background-radius: 7px;"
        this.boutonLancer.translateX = 80.0
        this.boutonLancer.isVisible = true
        this.boutonConfirm = Button("Confirmer le choix des dés")
        this.boutonConfirm.style = "-fx-padding: 10px 20px; -fx-background-radius: 7px;"
        this.boutonConfirm.translateX = 80.0
        this.boutonConfirm.isVisible = false
        this.boutonsDes.children.addAll(boutonLancer, boutonConfirm)

        // dés lancés
        this.plateauDes = GridPane()
        this.valDes = mutableListOf()
        this.listDes = mutableListOf()

        this.plateauDes.minWidth = 330.0
        this.plateauDes.maxWidth = 330.0
        this.plateauDes.minHeight = 330.0
        this.plateauDes.maxHeight = 330.0
        this.plateauDes.background = Background(BackgroundImage(Image(FileInputStream("images/fonds/fondDes.png")), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize(60.0, 60.0, false, false, true, false)))
        this.partieCentre.children.add(lancerDes)
        this.lancerDes.children.add(plateauDes)
        this.lancerDes.children.add(boutonsDes)
        this.center = this.partieCentre
    }

    fun afficherDesLances(listOfDe: MutableList<De>, deImp: MutableList<Int>) {
        var posDe: Pair<Int, Int>
        var desPrecedents: Array<Pair<Int, Int>> = arrayOf()
        for (i in 0 until taille) {
            // import des images des dés
            val deLances = Label()
            listDes += deLances
            valDes += listOfDe[i].donneFaceActuel().ordinal + 1
            val inputDeLances = FileInputStream(listOfDe[i].donneImage())
            if (valDes[valDes.size-1] in deImp){
                deLances.isDisable = true
            }
            val imageDeLances = Image(inputDeLances)
            deLances.graphic = ImageView(imageDeLances)
            // paramèters pour mettre les dés de manière plus "naturelle"
            deLances.padding = Insets(12.0, 12.0, 12.0, 12.0)
            deLances.translateX = 15.0 + Random.nextDouble(-10.0, 10.0)
            deLances.translateY = 15.0 + Random.nextDouble(-10.0, 10.0)
            deLances.rotate = Random.nextDouble(-180.0, 180.0)
            posDe = Pair(Random.nextInt(0, 4), Random.nextInt(0, 4))
            while (posDe in desPrecedents) {
                posDe = Pair(Random.nextInt(0, 4), Random.nextInt(0, 4))
            }
            plateauDes.add(deLances, posDe.first, posDe.second)
            desPrecedents += posDe
        }
    }

    fun clearDes(){
        for (i in 0 until listDes.size) {
            plateauDes.children[i].isVisible = false
        }
    }

    fun afficherDesGardes(deSelec: String) {
        val de = Label()
        val nomDe = "de$deSelec.png"

        val inputDe = FileInputStream("images/des/$nomDe")
        val imageDe = Image(inputDe)
        de.graphic = ImageView(imageDe)

        this.desGardes.children[dernierDe] = de.graphic
        dernierDe += 1
        taille -= 1
        nbDes += 1
    }

    fun afficherLesJoueursPlateau(joueurs : List<Joueur>) {
        this.joueurBas = Label(joueurs[0].donneNom())
        this.joueurBas.style = "-fx-background-color: ${joueurs[0].donneCouleur()}; "
    }

    fun boutonVisible(bouton : Button, boolean: Boolean){
        bouton.isVisible = boolean
    }
}