package Controleurs.ControleursPlateau

import Modele.Jeux
import Vues.MainVue
import Vues.Plateau
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.effect.ColorAdjust
import javafx.scene.input.MouseEvent

class ControleurSelectionDe(vue : Plateau, mainVue: MainVue, jeux: Jeux) : EventHandler<MouseEvent> {
    private var vue: Plateau
    private var mainVue: MainVue
    private val jeux: Jeux

    init {
        this.vue = vue
        this.mainVue = mainVue
        this.jeux = jeux
    }


    override fun handle(event: MouseEvent) {
        val label = event.source as Label
        var indiceDeSelec = 0
        for (i in 0 until vue.listDes.size) {
            if (vue.listDes[i] == label) {
                indiceDeSelec = i
            }
        }
        // Enlève tous les effets de saturation ou autre sur l'ensemble des dés.
        for (i in 0 until vue.listDes.size) {
            vue.listDes[i].effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        }

        // Sature les dés choisis par un utilisateur qui ont la même valeur.
        for (i in vue.valDes.indices) {
            if (vue.valDes[i] == vue.valDes[indiceDeSelec]) {
                vue.listDes[i].effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                jeux.setDeSelec("${vue.valDes[i]}")
            }
        }
        vue.boutonConfirm.isVisible = true
    }
}