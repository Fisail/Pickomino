package Controleurs.ControleursAccueil

import Vues.ChoixJoueurs
import Vues.MainVue
import javafx.event.ActionEvent
import javafx.event.EventHandler

class ControleurButtonJouer(mainVue : MainVue, choixJoueurs: ChoixJoueurs) : EventHandler<ActionEvent> {
    private var mainVue: MainVue
    private var choixJoueurs : ChoixJoueurs

    init {
        this.mainVue = mainVue
        this.choixJoueurs= choixJoueurs
    }

    override fun handle(event: ActionEvent) {
        mainVue.updateVue(choixJoueurs)
    }
}