package Controleurs.ControleursAccueil

import Vues.MainVue
import Vues.Regles
import javafx.event.ActionEvent
import javafx.event.EventHandler


class ControleurButtonRegle(mainVue : MainVue, regles: Regles) : EventHandler<ActionEvent> {
    private var mainVue: MainVue
    private var regles : Regles

    init {
        this.mainVue = mainVue
        this.regles = regles
    }

    override fun handle(event: ActionEvent) {
        mainVue.updateVue(regles)
    }
}