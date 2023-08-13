package Controleurs.ControleursChoixJoueurs

import Vues.ChoixJoueurs
import javafx.event.ActionEvent
import javafx.event.EventHandler

class ControleurBouttonAjouterJoueurs(vue: ChoixJoueurs) : EventHandler<ActionEvent> {
    private var vue: ChoixJoueurs

    init {
        this.vue = vue
    }

    /*
    * Quand on ajoute un joueur, cela passe à 3 joueurs et donc on rentre dans le if qui permet d'afficher le textField
    * du 3ème joueur. Par la même occasion, le boutton supprimer joueur n'est plus désactivé.
    * Quand il y a 4 joueurs, on ajoute le textfield du 4ème joueur mais nous désactivons le boutton ajouter joueur
    * */
    override fun handle(event: ActionEvent) {
        vue.compteur ++
        vue.nbJoueurs ++
        if (vue.compteur == 3){
            vue.partieJoueursOrigine.children.add(vue.partieJoueurs3Label)
            vue.bouttonSupprimer.isDisable = false
        } else if (vue.compteur == 4){
            vue.partieJoueursOrigine.children.add(vue.partieJoueurs4Label)
            vue.bouttonAjouter.isDisable = true
            vue.bouttonSupprimer.isDisable = false
        } else {
            vue.bouttonAjouter.isDisable = true
        }
    }
}