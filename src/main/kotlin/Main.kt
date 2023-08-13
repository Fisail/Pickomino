import Controleurs.ControleursAccueil.ControleurButtonJouer
import Controleurs.ControleursAccueil.ControleurButtonRegle
import Controleurs.ControleursChoixJoueurs.ControleurBouttonAjouterJoueurs
import Controleurs.ControleursChoixJoueurs.ControleurBouttonSupprimerJoueur
import Controleurs.ControleursChoixJoueurs.ControleurButtonCommencer
import Controleurs.ControleursChoixJoueurs.ControleurButtonRetour
import Controleurs.ControleursPlateau.*
import Controleurs.ControleursResultat.ControleurResultat
import Modele.Jeux
import Modele.Joueur
import Vues.*
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import javazoom.jl.player.Player
import java.io.FileInputStream

class MusicPlayer(path: String) : Runnable {

    private var musicPath: String = path
    private var player: Player? = null

    override fun run() {
        try {
            val fileInputStream = FileInputStream(musicPath)
            player = Player(fileInputStream)
            player?.play()
        } catch (e: Exception) {
            println(e.stackTrace)
        }
    }

    fun changeMusic(path: String) {
        stop()
        musicPath = path
        val thread = Thread(this)
        thread.start()
    }

    fun stop() {
        player?.close()
    }
}

class JeuPickomino : Application() {
    private var musicPlayer = MusicPlayer("musique/musiqueAccueil.mp3")
    override fun start(primaryStage: Stage) {
        val accueil = Accueil()
        val mainVue = MainVue(accueil)
        val regles = Regles()
        val choixJoueurs = ChoixJoueurs()
        val plateau4j = Plateau4j()
        val plateau3j = Plateau3j()
        val plateau2j = Plateau2j()
        val plateau = Plateau()
        val resultat = Resultat()

        val inputIcon = FileInputStream("images/icon.png")
        val icon = Image(inputIcon)

        val joueur = Joueur("")
        val jeux : Jeux = Jeux("172.26.82.76", "8080", false)

        mainVue.evenementButton(accueil.boutonJouer, ControleurButtonJouer(mainVue, choixJoueurs))
        mainVue.evenementButton(accueil.boutonRegles, ControleurButtonRegle(mainVue, regles))

        mainVue.evenementButton(regles.boutonRetour, Controleurs.ControleursRegles.ControleurBouttonRetour(mainVue, accueil))

        //Association des controleurs aux différents bouttons de la vue CHoixJoueurs.
        mainVue.evenementButton(choixJoueurs.bouttonRetour, ControleurButtonRetour(mainVue, accueil))
        mainVue.evenementButton(choixJoueurs.bouttonCommencer, ControleurButtonCommencer(mainVue,choixJoueurs,plateau, plateau4j,plateau3j,plateau2j,jeux,joueur))
        mainVue.evenementButton(choixJoueurs.bouttonAjouter, ControleurBouttonAjouterJoueurs(choixJoueurs))
        mainVue.evenementButton(choixJoueurs.bouttonSupprimer, ControleurBouttonSupprimerJoueur(choixJoueurs))

        //Association des controleurs aux boutton lancer.
        mainVue.evenementButton(plateau2j.boutonLancer, ControleurBouttonLancerDes(plateau, plateau2j, plateau3j, plateau4j, mainVue, resultat, jeux, musicPlayer))
        mainVue.evenementButton(plateau3j.boutonLancer, ControleurBouttonLancerDes(plateau, plateau2j, plateau3j, plateau4j, mainVue, resultat,  jeux, musicPlayer))
        mainVue.evenementButton(plateau4j.boutonLancer, ControleurBouttonLancerDes(plateau, plateau2j, plateau3j, plateau4j, mainVue, resultat,  jeux, musicPlayer))

        //Association des controleurs aux boutton confirmer.
        mainVue.evenementButton(plateau2j.boutonConfirm, ControleurChoixDe(plateau, plateau2j, plateau3j, plateau4j, mainVue, jeux))
        mainVue.evenementButton(plateau3j.boutonConfirm, ControleurChoixDe(plateau, plateau2j, plateau3j, plateau4j, mainVue, jeux))
        mainVue.evenementButton(plateau4j.boutonConfirm, ControleurChoixDe(plateau, plateau2j, plateau3j, plateau4j, mainVue, jeux))

        //Association des controleurs aux boutton selectPicko.
        mainVue.evenementButton(plateau2j.selectPicko, ControleurChoixPickomino(plateau, plateau2j, plateau3j, plateau4j, choixJoueurs, mainVue, resultat, jeux, musicPlayer))
        mainVue.evenementButton(plateau3j.selectPicko, ControleurChoixPickomino(plateau, plateau2j, plateau3j, plateau4j, choixJoueurs, mainVue, resultat, jeux, musicPlayer))
        mainVue.evenementButton(plateau4j.selectPicko, ControleurChoixPickomino(plateau, plateau2j, plateau3j, plateau4j, choixJoueurs, mainVue, resultat, jeux,musicPlayer))

        //Association des controleurs aux boutton listDes.
        mainVue.evenementSouris(plateau2j.listDes, ControleurSelectionDe(plateau2j, mainVue, jeux), plateau2j.plateauDes)
        mainVue.evenementSouris(plateau3j.listDes, ControleurSelectionDe(plateau3j, mainVue, jeux), plateau3j.plateauDes)
        mainVue.evenementSouris(plateau4j.listDes, ControleurSelectionDe(plateau4j, mainVue, jeux), plateau4j.plateauDes)

        //Association des controleurs aux boutton correspondant aux différents vols.
        mainVue.evenementButton(plateau2j.volPickoHaut, ControleurBoutonVolerPicko(plateau, plateau2j, plateau3j, plateau4j, mainVue, jeux))
        mainVue.evenementButton(plateau3j.volPickoGauche, ControleurBoutonVolerPicko(plateau, plateau2j, plateau3j, plateau4j, mainVue, jeux))
        mainVue.evenementButton(plateau3j.volPickoDroite, ControleurBoutonVolerPicko(plateau, plateau2j, plateau3j, plateau4j, mainVue, jeux))
        mainVue.evenementButton(plateau4j.volPickoHaut, ControleurBoutonVolerPicko(plateau, plateau2j, plateau3j, plateau4j, mainVue, jeux))
        mainVue.evenementButton(plateau4j.volPickoGauche, ControleurBoutonVolerPicko(plateau, plateau2j, plateau3j, plateau4j, mainVue, jeux))
        mainVue.evenementButton(plateau4j.volPickoDroite, ControleurBoutonVolerPicko(plateau, plateau2j, plateau3j, plateau4j, mainVue, jeux))

        mainVue.evenementButton(resultat.butonAccueil, ControleurResultat(mainVue, accueil))

        primaryStage.setOnCloseRequest { musicPlayer.stop() }
        primaryStage.title = "PICKOMINO - Jeu de société"
        primaryStage.icons.add(icon)
        val scene = Scene(mainVue)
        primaryStage.scene = scene
        primaryStage.isMaximized = true
        primaryStage.show()
    }
}
fun main() {
    // Lancement de l'application JavaFX
    Application.launch(JeuPickomino::class.java)
}