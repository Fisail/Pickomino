package Controleurs.ControleursPlateau

import Modele.Domino
import Modele.Jeux
import MusicPlayer
import Vues.*
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.io.FileInputStream

class ControleurChoixPickomino(plateau: Plateau, plateau2j: Plateau2j, plateau3j: Plateau3j, plateau4j: Plateau4j, choixJoueurs: ChoixJoueurs, mainVue: MainVue, resultat: Resultat, jeux: Jeux, private val musicPlayer: MusicPlayer) : EventHandler<ActionEvent> {
    private val jeux: Jeux
    private val plateau: Plateau
    private val plateau2j: Plateau2j
    private val plateau3j: Plateau3j
    private val plateau4j: Plateau4j
    private val choixJoueurs: ChoixJoueurs
    private val mainVue: MainVue
    private val resultat: Resultat
    private var finDePartie: Boolean

    init {
        this.jeux = jeux
        this.plateau = plateau
        this.plateau2j = plateau2j
        this.plateau3j = plateau3j
        this.plateau4j = plateau4j
        this.choixJoueurs = choixJoueurs
        this.mainVue = mainVue
        this.resultat = resultat
        this.finDePartie = true
    }

    override fun handle(event: ActionEvent) {
        plateau2j.pilePickoHaut.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        plateau3j.pilePickoGauche.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        plateau3j.pilePickoDroite.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        plateau4j.pilePickoHaut.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        plateau4j.pilePickoGauche.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        plateau4j.pilePickoDroite.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)

        if (plateau.totDes >= 21) {
            if (plateau.totDes > 36) {
                plateau.totDes = 36
            }
            var stop = true
            if ((plateau.totDes !in jeux.etatDuJeux().accessiblePickos()) || (plateau.totDes == jeux.etatDuJeux().pickosStackTops()[jeux.donneJoueurCourant().donneId() - 1])
            ) {
                for (i in plateau.totDes downTo 21) {
                    if (i in jeux.etatDuJeux().accessiblePickos() && stop) {
                        stop = false
                        if (i !in jeux.etatDuJeux().pickosStackTops()) {
                            jeux.prendreLeDomino(Domino(i))
                            plateau2j.pilePickoBas.graphic =
                                ImageView(Image(FileInputStream("images/pickominos/picko${i}.png")))
                            plateau3j.pilePickoBas.graphic =
                                ImageView(Image(FileInputStream("images/pickominos/picko${i}.png")))
                            plateau4j.pilePickoBas.graphic =
                                ImageView(Image(FileInputStream("images/pickominos/picko${i}.png")))

                            plateau2j.pickominos.children[i - 21].isVisible = false
                            plateau3j.pickominos.children[i - 21].isVisible = false
                            plateau4j.pickominos.children[i - 21].isVisible = false
                        }
                    }
                }
            } else {
                jeux.prendreLeDomino(Domino(plateau.totDes))
                plateau2j.pilePickoBas.graphic =
                    ImageView(Image(FileInputStream("images/pickominos/picko${plateau.totDes}.png")))
                plateau3j.pilePickoBas.graphic =
                    ImageView(Image(FileInputStream("images/pickominos/picko${plateau.totDes}.png")))
                plateau4j.pilePickoBas.graphic =
                    ImageView(Image(FileInputStream("images/pickominos/picko${plateau.totDes}.png")))

                plateau2j.pickominos.children[plateau.totDes - 21].isVisible = false
                plateau3j.pickominos.children[plateau.totDes - 21].isVisible = false
                plateau4j.pickominos.children[plateau.totDes - 21].isVisible = false
            }

            if (jeux.listDesDominosDisponible().size == 0) {
                mainVue.alertFinJeu(musicPlayer)
                resultat.afficherLesResultats(jeux.donneJoueurs(), jeux.scoreFinal())
                mainVue.updateVue(resultat)
                finDePartie = false
            }

            if (finDePartie) {
                plateau2j.boutonVisible(plateau2j.selectPicko,false)
                plateau3j.boutonVisible(plateau3j.selectPicko,false)
                plateau4j.boutonVisible(plateau4j.selectPicko,false)

                plateau2j.boutonVisible(plateau2j.volPickoHaut,false)
                plateau3j.boutonVisible(plateau3j.volPickoGauche,false)
                plateau3j.boutonVisible(plateau3j.volPickoDroite,false)
                plateau4j.boutonVisible(plateau4j.volPickoHaut,false)
                plateau4j.boutonVisible(plateau4j.volPickoGauche,false)
                plateau4j.boutonVisible(plateau4j.volPickoDroite,false)

                if (jeux.donneJoueurs().size == 2) {
                    mainVue.alertChgmtJoueur2(plateau2j)
                } else if (jeux.donneJoueurs().size == 3) {
                    mainVue.alertChgmtJoueur3(plateau3j)
                } else if (jeux.donneJoueurs().size == 4) {
                    mainVue.alertChgmtJoueur4(plateau4j)
                }

                plateau2j.boutonVisible(plateau2j.boutonLancer,true)
                plateau3j.boutonVisible(plateau3j.boutonLancer,true)
                plateau4j.boutonVisible(plateau4j.boutonLancer,true)

                for (i in 0 until 8) {
                    val de = Label()
                    de.graphic = ImageView(Image(FileInputStream("images/des/de.png")))
                    plateau2j.desGardes.children[i] = de.graphic
                }
                for (i in 0 until 8) {
                    val de = Label()
                    de.graphic = ImageView(Image(FileInputStream("images/des/de.png")))
                    plateau3j.desGardes.children[i] = de.graphic
                }
                for (i in 0 until 8) {
                    val de = Label()
                    de.graphic = ImageView(Image(FileInputStream("images/des/de.png")))
                    plateau4j.desGardes.children[i] = de.graphic
                }

                //Réinitialisation du plateau
                plateau2j.deImp = mutableListOf<Int>()
                plateau3j.deImp = mutableListOf<Int>()
                plateau4j.deImp = mutableListOf<Int>()
                plateau.deImp = mutableListOf()
                plateau.oldNb = 0
                plateau.totDes = 0
                plateau2j.totalDe.text = "Valeur des dés : 0"
                plateau3j.totalDe.text = "Valeur des dés : 0"
                plateau4j.totalDe.text = "Valeur des dés : 0"
                plateau2j.taille = 8
                plateau3j.taille = 8
                plateau4j.taille = 8
                plateau2j.dernierDe = 0
                plateau3j.dernierDe = 0
                plateau4j.dernierDe = 0
                plateau2j.nbDes = 0
                plateau3j.nbDes = 0
                plateau4j.nbDes = 0

                // tourner les joueurs selon leur nombre dans une partie. Pour cela, on change l'identifiant du joueur
                // en lui mettant celui du joueur où celui-ci va prendre sa place.

                if (jeux.donneJoueurs().size == 2) {
                    plateau2j.joueurBas.text = plateau.joueurs[jeux.donneJoueurCourant().donneId()-1].donneNom()
                    plateau2j.joueurBas.style = "-fx-background-color: ${plateau.joueurs[jeux.donneJoueurCourant().donneId()-1].donneCouleur()}; -fx-background-radius: 5px;"
                    plateau2j.pilePickoBas.graphic = ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[jeux.donneJoueurCourant().donneId()-1].donneValuer())}.png")))

                    var id2 = jeux.donneJoueurCourant().donneId()-1
                    if (id2 == 0) {
                        id2 = 1
                    } else if (id2 == 1) {
                        id2 = 0
                    }

                    plateau2j.joueurHaut2.text = plateau.joueurs[id2].donneNom()
                    plateau2j.joueurHaut2.style = "-fx-background-color: ${plateau.joueurs[id2].donneCouleur()}; -fx-background-radius: 5px;"
                    plateau2j.pilePickoHaut.graphic = ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id2].donneValuer())}.png")))
                } else if (jeux.donneJoueurs().size == 3) {
                    plateau3j.joueurBas.text = plateau.joueurs[jeux.donneJoueurCourant().donneId()-1].donneNom()
                    plateau3j.joueurBas.style = "-fx-background-color: ${plateau.joueurs[jeux.donneJoueurCourant().donneId()-1].donneCouleur()}; -fx-background-radius: 5px;"
                    plateau3j.pilePickoBas.graphic = ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[jeux.donneJoueurCourant().donneId()-1].donneValuer())}.png")))

                    var id2 = jeux.donneJoueurCourant().donneId()-1
                    if (id2 == 0) {
                        id2 = 1
                    } else if (id2 == 1) {
                        id2 = 2
                    } else if (id2 == 2) {
                        id2 = 0
                    }

                    plateau3j.joueurGauche.text = plateau.joueurs[id2].donneNom()
                    plateau3j.joueurGauche.style = "-fx-background-color: ${plateau.joueurs[id2].donneCouleur()}; -fx-background-radius: 5px;"
                    plateau3j.pilePickoGauche.graphic = ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id2].donneValuer())}.png")))

                    var id3 = jeux.donneJoueurCourant().donneId()-1
                    if (id3 == 0) {
                        id3 = 2
                    } else if (id3 == 1) {
                        id3 = 0
                    } else if (id3 == 2) {
                        id3 = 1
                    }

                    plateau3j.joueurDroite.text = plateau.joueurs[id3].donneNom()
                    plateau3j.joueurDroite.style = "-fx-background-color: ${plateau.joueurs[id3].donneCouleur()}; -fx-background-radius: 5px;"
                    plateau3j.pilePickoDroite.graphic = ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id3].donneValuer())}.png")))
                } else if (jeux.donneJoueurs().size == 4) {
                    plateau4j.joueurBas.text = plateau.joueurs[jeux.donneJoueurCourant().donneId()-1].donneNom()
                    plateau4j.joueurBas.style = "-fx-background-color: ${plateau.joueurs[jeux.donneJoueurCourant().donneId()-1].donneCouleur()}; -fx-background-radius: 5px;"
                    plateau4j.pilePickoBas.graphic = ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[jeux.donneJoueurCourant().donneId()-1].donneValuer())}.png")))

                    var id2 = jeux.donneJoueurCourant().donneId()-1
                    if (id2 == 0) {
                        id2 = 1
                    } else if (id2 == 1) {
                        id2 = 2
                    } else if (id2 == 2) {
                        id2 = 3
                    } else if (id2 == 3) {
                        id2 = 0
                    }

                    plateau4j.joueurGauche.text = plateau.joueurs[id2].donneNom()
                    plateau4j.joueurGauche.style = "-fx-background-color: ${plateau.joueurs[id2].donneCouleur()}; -fx-background-radius: 5px;"
                    plateau4j.pilePickoGauche.graphic = ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id2].donneValuer())}.png")))

                    var id3 = jeux.donneJoueurCourant().donneId()-1
                    if (id3 == 0) {
                        id3 = 2
                    } else if (id3 == 1) {
                        id3 = 3
                    } else if (id3 == 2) {
                        id3 = 0
                    } else if (id3 == 3) {
                        id3 = 1
                    }

                    plateau4j.joueurHaut.text = plateau.joueurs[id3].donneNom()
                    plateau4j.joueurHaut.style = "-fx-background-color: ${plateau.joueurs[id3].donneCouleur()}; -fx-background-radius: 5px;"
                    plateau4j.pilePickoHaut.graphic = ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id3].donneValuer())}.png")))

                    var id4 = jeux.donneJoueurCourant().donneId()-1
                    if (id4 == 0) {
                        id4 = 3
                    } else if (id4 == 1) {
                        id4 = 0
                    } else if (id4 == 2) {
                        id4 = 1
                    } else if (id4 == 3) {
                        id4 = 2
                    }

                    plateau4j.joueurDroite.text = plateau.joueurs[id4].donneNom()
                    plateau4j.joueurDroite.style = "-fx-background-color: ${plateau.joueurs[id4].donneCouleur()}; -fx-background-radius: 5px;"
                    plateau4j.pilePickoDroite.graphic = ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id4].donneValuer())}.png")))
                }
            }
        }
    }
}