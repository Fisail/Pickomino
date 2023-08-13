package Controleurs.ControleursPlateau

import Modele.De
import Modele.Domino
import Modele.Jeux
import Vues.*
import iut.info1.pickomino.data.DICE
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.io.FileInputStream

class ControleurChoixDe(plateau: Plateau, plateau2j: Plateau2j, plateau3j: Plateau3j, plateau4j: Plateau4j, mainVue: MainVue, jeux: Jeux) : EventHandler<ActionEvent> {
    private val jeux: Jeux
    private val plateau: Plateau
    private val plateau2j: Plateau2j
    private val plateau3j: Plateau3j
    private val plateau4j: Plateau4j
    private val mainVue: MainVue

    init {
        this.jeux = jeux
        this.plateau = plateau
        this.plateau2j = plateau2j
        this.plateau3j = plateau3j
        this.plateau4j = plateau4j
        this.mainVue = mainVue
    }

    override fun handle(event: ActionEvent) {
        if (jeux.getDeSelec() == "6") {
            jeux.garderLeDe(De(DICE.worm))
        } else {
            jeux.garderLeDe(De(DICE.valueOf("d" + jeux.getDeSelec())))
        }

        jeux.listDesDesGardes()

        for (i in 0 until jeux.listDesDesGardes().size - plateau.oldNb) {
            plateau2j.afficherDesGardes(jeux.getDeSelec())
            plateau3j.afficherDesGardes(jeux.getDeSelec())
            plateau4j.afficherDesGardes(jeux.getDeSelec())

            if (jeux.getDeSelec().toInt() == 6) {
                plateau.totDes += 5
            } else {
                plateau.totDes += jeux.getDeSelec().toInt()
            }
            plateau2j.totalDe.text = "Valeur des dés : ${plateau.totDes}"
            plateau3j.totalDe.text = "Valeur des dés : ${plateau.totDes}"
            plateau4j.totalDe.text = "Valeur des dés : ${plateau.totDes}"
        }
        plateau.deImp += (jeux.getDeSelec()).toInt()
        plateau2j.deImp += (jeux.getDeSelec()).toInt()
        plateau3j.deImp += (jeux.getDeSelec()).toInt()
        plateau4j.deImp += (jeux.getDeSelec()).toInt()
        plateau.oldNb += jeux.listDesDesGardes().size-plateau.oldNb


        // effacer les dés sur le plateau
        plateau2j.clearDes()
        plateau3j.clearDes()
        plateau4j.clearDes()

        if ((plateau.totDes >= jeux.listDesDominosDisponible()[0].donneValuer()) && (6 in plateau.deImp)) {
            plateau2j.boutonVisible(plateau2j.selectPicko,true)
            plateau3j.boutonVisible(plateau3j.selectPicko,true)
            plateau4j.boutonVisible(plateau4j.selectPicko,true)

            // pickomino plus petit possible à prendre si picko déjà pris
            var stop = true
            if (plateau.totDes !in jeux.etatDuJeux().accessiblePickos()) {
                for (i in plateau.totDes downTo 21) {
                    if (i in jeux.etatDuJeux().accessiblePickos() && stop) {
                        plateau2j.pickominos.children[i-21].effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                        plateau3j.pickominos.children[i-21].effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                        plateau4j.pickominos.children[i-21].effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                        stop = false
                    }
                }
            }

            volPicko()

            // si jamais le total des dés est supérieur à 36
            if (plateau.totDes >= 36) {
                plateau2j.pickominos.children[15].effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
            } else {
                if (!plateau2j.pickominos.children[plateau.totDes-21].isDisable) {
                    plateau2j.pickominos.children[plateau.totDes-21].effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                }
            }
            if (plateau.totDes >= 36) {
                plateau3j.pickominos.children[15].effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
            } else {
                if (!plateau3j.pickominos.children[plateau.totDes - 21].isDisable) {
                    plateau3j.pickominos.children[plateau.totDes - 21].effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                }
            }
            if (plateau.totDes >= 36) {
                plateau4j.pickominos.children[15].effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
            } else {
                if (!plateau4j.pickominos.children[plateau.totDes - 21].isDisable) {
                    plateau4j.pickominos.children[plateau.totDes - 21].effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                }
            }
        }

        volPicko()

        plateau2j.boutonVisible(plateau2j.boutonConfirm,false)
        plateau3j.boutonVisible(plateau3j.boutonConfirm,false)
        plateau4j.boutonVisible(plateau4j.boutonConfirm,false)
        // si il n'y a plus de dés à lancer
        plateau2j.boutonLancer.isVisible = plateau2j.nbDes != 8
        plateau3j.boutonLancer.isVisible = plateau3j.nbDes != 8
        plateau4j.boutonLancer.isVisible = plateau4j.nbDes != 8
    }

    // Active le boutton "volé pickomino" qui permet de voler le pickomino d'une personne si son pickomono au top de la pile est égal à la somme de ses dés.
    fun volPicko() {
        if (6 in plateau.deImp) {
            // pour 2 joueurs
            if (jeux.donneJoueurs().size == 2) {
                var id2 = jeux.donneJoueurCourant().donneId()-1
                if (id2 == 0) {
                    id2 = 1
                } else if (id2 == 1) {
                    id2 = 0
                }
                if (plateau.totDes == jeux.etatDuJeux().pickosStackTops()[id2]) {
                    plateau2j.pilePickoHaut.effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                    plateau2j.boutonVisible(plateau2j.volPickoHaut,true)
                }
                // pour 3 joueurs
            } else if (jeux.donneJoueurs().size == 3) {
                var id2 = jeux.donneJoueurCourant().donneId() - 1
                if (id2 == 0) {
                    id2 = 1
                } else if (id2 == 1) {
                    id2 = 2
                } else if (id2 == 2) {
                    id2 = 0
                }
                if (plateau.totDes == jeux.etatDuJeux().pickosStackTops()[id2]) {
                    plateau3j.pilePickoGauche.effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                    plateau3j.boutonVisible(plateau3j.volPickoGauche,true)
                }
                var id3 = jeux.donneJoueurCourant().donneId() - 1
                if (id3 == 0) {
                    id3 = 2
                } else if (id3 == 1) {
                    id3 = 0
                } else if (id3 == 2) {
                    id3 = 1
                }
                if (plateau.totDes == jeux.etatDuJeux().pickosStackTops()[id3]) {
                    plateau3j.pilePickoDroite.effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                    plateau3j.boutonVisible(plateau3j.volPickoDroite,true)
                }
                // pour 4 joueurs
            } else if (jeux.donneJoueurs().size == 4) {
                var id2 = jeux.donneJoueurCourant().donneId() - 1
                if (id2 == 0) {
                    id2 = 1
                } else if (id2 == 1) {
                    id2 = 2
                } else if (id2 == 2) {
                    id2 = 3
                } else if (id2 == 3) {
                    id2 = 0
                }
                if (plateau.totDes == jeux.etatDuJeux().pickosStackTops()[id2]) {
                    plateau4j.pilePickoGauche.effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                    plateau4j.boutonVisible(plateau4j.volPickoGauche,true)
                }
                var id3 = jeux.donneJoueurCourant().donneId() - 1
                if (id3 == 0) {
                    id3 = 2
                } else if (id3 == 1) {
                    id3 = 3
                } else if (id3 == 2) {
                    id3 = 0
                } else if (id3 == 3) {
                    id3 = 1
                }
                if (plateau.totDes == jeux.etatDuJeux().pickosStackTops()[id3]) {
                    plateau4j.pilePickoHaut.effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                    plateau4j.boutonVisible(plateau4j.volPickoHaut,true)
                }
                var id4 = jeux.donneJoueurCourant().donneId() - 1
                if (id4 == 0) {
                    id4 = 3
                } else if (id4 == 1) {
                    id4 = 0
                } else if (id4 == 2) {
                    id4 = 1
                } else if (id4 == 3) {
                    id4 = 2
                }
                if (plateau.totDes == jeux.etatDuJeux().pickosStackTops()[id4]) {
                    plateau4j.pilePickoDroite.effect = ColorAdjust(0.9, 0.15, 0.0, 0.0)
                    plateau4j.boutonVisible(plateau4j.volPickoDroite,true)
                }
            }
        }
    }
}