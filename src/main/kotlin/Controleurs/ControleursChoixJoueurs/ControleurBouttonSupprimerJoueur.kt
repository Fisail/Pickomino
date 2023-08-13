package Controleurs.ControleursChoixJoueurs

import Vues.ChoixJoueurs
import javafx.event.ActionEvent
import javafx.event.EventHandler

class ControleurBouttonSupprimerJoueur(vue: ChoixJoueurs) : EventHandler<ActionEvent> {
    private var vue: ChoixJoueurs

    init {
        this.vue = vue
    }

    /*
     * Quand il y a 3 joueurs ou plus, le boutton devient actif et supprime le textfield du dernier joueur sinon le boutton est désactivé.
     */
    override fun handle(event: ActionEvent) {
        vue.compteur --
        vue.nbJoueurs --
        if (vue.compteur == 2) {
            vue.partieJoueursOrigine.children.remove(vue.partieJoueurs3Label)
            vue.bouttonSupprimer.isDisable = true
            vue.bouttonAjouter.isDisable = false
        } else if (vue.compteur == 3) {
            vue.partieJoueursOrigine.children.remove(vue.partieJoueurs4Label)
            vue.bouttonAjouter.isDisable = false
        } else {
            vue.bouttonSupprimer.isDisable = true
        }
    }
}