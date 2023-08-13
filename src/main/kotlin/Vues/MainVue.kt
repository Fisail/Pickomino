package Vues

import MusicPlayer
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane

class MainVue(accueil: Accueil) : BorderPane() {

    init {
        this.center = accueil
    }

    fun evenementButton(Boutton : Button, eventListener: EventHandler<ActionEvent>){
        Boutton.onAction = eventListener
    }

    fun evenementSouris(labels: MutableList<Label>, eventHandler: EventHandler<MouseEvent>, grid: GridPane) {
        for (label in labels) {
            grid.children.forEach { it.onMouseClicked = eventHandler }
        }
    }

    fun alertRateDe() {
        val alert = Alert(AlertType.WARNING)
        alert.title = "Raté !"
        alert.headerText = "Vous ne pouvez prendre aucun dé déjà mis de côté"
        alert.contentText = "Votre dernier pickomino est remis sur le plateau et le pickomino avec la valeur la plus élevée est enlevée du jeu."
        alert.showAndWait()
    }
    fun alertRateWorm() {
        val alert = Alert(AlertType.WARNING)
        alert.title = "Raté !"
        alert.headerText = "Vous n'avez aucun ver dans vos dés"
        alert.contentText = "Votre dernier pickomino est remis sur le plateau et le pickomino avec la valeur la plus élevée est enlevée du jeu."
        alert.showAndWait()
    }
    fun alertRate21() {
        val alert = Alert(AlertType.WARNING)
        alert.title = "Raté !"
        alert.headerText = "Le total de vos dé n'est pas nécessaire pour prendre un pickomino"
        alert.contentText = "Votre dernier pickomino est remis sur le plateau et le pickomino avec la valeur la plus élevée est enlevée du jeu."
        alert.showAndWait()
    }

    fun alertChgmtJoueur2(plateau2j: Plateau2j) {
        val alert = Alert(AlertType.INFORMATION)
        alert.title = "Tour suivant"
        alert.headerText = null
        alert.contentText = "${plateau2j.joueurHaut2.text}, à toi de jouer !"
        alert.showAndWait()
    }
    fun alertChgmtJoueur3(plateau3j: Plateau3j) {
        val alert = Alert(AlertType.INFORMATION)
        alert.title = "Tour suivant"
        alert.headerText = null
        alert.contentText = "C'est à ${plateau3j.joueurGauche.text} de jouer"
        alert.showAndWait()
    }
    fun alertChgmtJoueur4(plateau4j: Plateau4j) {
        val alert = Alert(AlertType.INFORMATION)
        alert.title = "Tour suivant"
        alert.headerText = null
        alert.contentText = "C'est à ${plateau4j.joueurGauche.text} de jouer"
        alert.showAndWait()
    }

    fun alertFinJeu(musicPlayer: MusicPlayer) {
        val alert = Alert(AlertType.NONE)
        alert.title = "Fin de la partie"
        alert.headerText = null
        alert.contentText = "Tous les pickominos ont été pris !"
        val bouton = ButtonType("Voir les résultats", ButtonBar.ButtonData.OK_DONE)
        alert.buttonTypes.setAll(bouton)
        alert.showAndWait().ifPresent { result ->
            if (result.buttonData == ButtonBar.ButtonData.OK_DONE) {
                musicPlayer.changeMusic("musique/musiqueVictoire.mp3")
            }
        }
    }


    fun updateVue(vue: Node) {
        this.center = vue
    }
}