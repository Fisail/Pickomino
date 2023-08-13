package Controleurs.ControleursChoixJoueurs

import Vues.Accueil
import Vues.MainVue
import javafx.event.ActionEvent
import javafx.event.EventHandler

class ControleurButtonRetour(mainVue: MainVue, accueil : Accueil) : EventHandler<ActionEvent> {
    private var mainVue: MainVue
    private var accueil : Accueil

    init {
        this.mainVue = mainVue
        this.accueil = accueil
    }

    override fun handle(event: ActionEvent) {
        mainVue.updateVue(accueil)
    }


}