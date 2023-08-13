package Controleurs.ControleursPlateau

import Modele.De
import Modele.Jeux
import Modele.idExterne
import MusicPlayer
import Vues.*
import io.ktor.http.*
import io.ktor.utils.io.*
import iut.info1.pickomino.data.DICE
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import kotlinx.coroutines.processNextEventInCurrentThread
import java.io.FileInputStream

class ControleurBouttonLancerDes(plateau: Plateau, plateau2j: Plateau2j, plateau3j: Plateau3j, plateau4j: Plateau4j, mainVue: MainVue, resultat: Resultat, jeux: Jeux,private val musicPlayer: MusicPlayer) : EventHandler<ActionEvent> {
    private val jeux: Jeux
    private val plateau: Plateau
    private val plateau2j: Plateau2j
    private val plateau3j: Plateau3j
    private val plateau4j: Plateau4j
    private var resultat: Resultat
    private val mainVue: MainVue
    private var pickoTop: Int
    private var pickoPerdu: Int
    private var dernierPicko: Int
    private var inc : Int
    private var finDePartie: Boolean

    init {
        this.jeux = jeux
        this.plateau = plateau
        this.plateau2j = plateau2j
        this.plateau3j = plateau3j
        this.plateau4j = plateau4j
        this.resultat = resultat
        this.mainVue = mainVue
        this.pickoTop = 0
        this.pickoPerdu = 0
        this.dernierPicko = 0
        this.inc = 15
        this.finDePartie = false
    }

    override fun handle(event: ActionEvent) {
        if (jeux.donneJoueurs().size == 2) {
            plateau.bruitageOn = plateau2j.bruitage.isSelected
        } else if (jeux.donneJoueurs().size == 3) {
            plateau.bruitageOn = plateau3j.bruitage.isSelected
        } else if (jeux.donneJoueurs().size == 4) {
            plateau.bruitageOn = plateau4j.bruitage.isSelected
        }

        this.pickoPerdu = jeux.sommetPileDesJoueurs()[jeux.donneJoueurCourant().donneId()-1].donneValuer()
        this.pickoTop = jeux.etatDuJeux().pickosStackTops()[jeux.donneJoueurCourant().donneId()-1]
        this.dernierPicko = jeux.listDesDominosDisponible()[jeux.listDesDominosDisponible().size-1].donneValuer()

        for (i in 0 until 16) {
            plateau2j.pickominos.children[i].effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
            plateau3j.pickominos.children[i].effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
            plateau4j.pickominos.children[i].effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        }
        plateau2j.pilePickoHaut.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        plateau3j.pilePickoGauche.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        plateau3j.pilePickoDroite.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        plateau4j.pilePickoHaut.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        plateau4j.pilePickoGauche.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        plateau4j.pilePickoDroite.effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)

        // DEBUG FALSE
        val desLances = jeux.lancerLesDes()
        if (plateau.bruitageOn) {
            plateau.bruitage.isSelected = false
            musicPlayer.changeMusic("musique/bruitageDes.mp3")
        }

        plateau2j.afficherDesLances(desLances,plateau3j.deImp)
        plateau3j.afficherDesLances(desLances,plateau3j.deImp)
        plateau4j.afficherDesLances(desLances,plateau4j.deImp)

        // DEBUG TRUE
//        val deLances = jeux.choisirLesDesDeLaLance(listOf((DICE.d1),(DICE.d1),(DICE.d1),(DICE.d2),(DICE.d2),(DICE.d4),(DICE.d4),(DICE.worm)))
//        val listdesd = mutableListOf<De>()
//        for (de in deLances) {
//            listdesd += De(de)
//        }
//        plateau2j.afficherDesLances(listdesd, plateau2j.deImp)


        mainVue.evenementSouris(plateau2j.listDes, ControleurSelectionDe(plateau2j, mainVue, jeux), plateau2j.plateauDes)
        mainVue.evenementSouris(plateau3j.listDes, ControleurSelectionDe(plateau3j, mainVue, jeux), plateau3j.plateauDes)
        mainVue.evenementSouris(plateau4j.listDes, ControleurSelectionDe(plateau4j, mainVue, jeux), plateau4j.plateauDes)

        plateau2j.boutonVisible(plateau2j.boutonLancer,false)
        plateau3j.boutonVisible(plateau3j.boutonLancer,false)
        plateau4j.boutonVisible(plateau4j.boutonLancer,false)

        plateau2j.boutonVisible(plateau2j.selectPicko,false)
        plateau3j.boutonVisible(plateau3j.selectPicko,false)
        plateau4j.boutonVisible(plateau4j.selectPicko,false)

        plateau2j.boutonVisible(plateau2j.volPickoHaut,false)
        plateau3j.boutonVisible(plateau3j.volPickoDroite,false)
        plateau3j.boutonVisible(plateau3j.volPickoGauche,false)
        plateau4j.boutonVisible(plateau4j.volPickoHaut,false)
        plateau4j.boutonVisible(plateau4j.volPickoDroite,false)
        plateau4j.boutonVisible(plateau4j.volPickoGauche,false)

        if (plateau.totDes >= 21) {
            plateau2j.pickominos.children[plateau.totDes-21].effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
            plateau3j.pickominos.children[plateau.totDes-21].effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
            plateau4j.pickominos.children[plateau.totDes-21].effect = ColorAdjust(0.0, 0.0, 0.0, 0.0)
        }

        // Cas où aucun dé ne peut être pris car déjà pris
        var nbVisible = 0
        var nbDisable = 0
        for (i in plateau2j.plateauDes.children) {
            if (i.isVisible) {
                nbVisible ++
                if (i.isDisable) {
                    nbDisable ++
                }
            }
        }
        if (nbDisable == nbVisible) {
            mainVue.alertRateDe()
            if (jeux.listDesDominosDisponible().size == 0) {
                mainVue.alertFinJeu(musicPlayer)
                resultat.afficherLesResultats(jeux.donneJoueurs(), jeux.scoreFinal())
                mainVue.updateVue(resultat)
                finDePartie = true
            }
            if (!finDePartie) {
                if (jeux.donneJoueurs().size == 2) {
                    mainVue.alertChgmtJoueur2(plateau2j)
                } else if (jeux.donneJoueurs().size == 3) {
                    mainVue.alertChgmtJoueur3(plateau3j)
                } else if (jeux.donneJoueurs().size == 4) {
                    mainVue.alertChgmtJoueur4(plateau4j)
                }
                resetPlateau()
            }
        }

        val desGardes = mutableListOf<Int>()
        for (i in 0 until jeux.listDesDesGardes().size) {
            desGardes += jeux.listDesDesGardes()[i].donneFaceActuel().ordinal+1
        }
        val desPlateau = mutableListOf<Int>()
        val rolls = jeux.etatDuJeux().current.rolls
        for (de in rolls) {
            desPlateau += de.ordinal+1
        }
        if (desPlateau.size != 0) {
            // cas ou on n'a pas de vers dans les dés
            val de1 = desPlateau[0]
            var same = false
            for (i in 0 until desPlateau.size) {
                same = desPlateau[i] == de1
                if (!same) {
                    break
                }
            }
            if (de1 == 6) {
                same = false
            }
            if (same && (6 !in desGardes)) {
                if (desPlateau[0] == 6) {
                    jeux.garderLeDe(De(DICE.valueOf("worm")))
                } else {
                    jeux.garderLeDe(De(DICE.valueOf("d${desPlateau[0]}")))
                }
                mainVue.alertRateWorm()
                if (jeux.listDesDominosDisponible().size == 0) {
                    mainVue.alertFinJeu(musicPlayer)
                    resultat.afficherLesResultats(jeux.donneJoueurs(), jeux.scoreFinal())
                    mainVue.updateVue(resultat)
                    finDePartie = true
                }
                if (!finDePartie) {
                    if (jeux.donneJoueurs().size == 2) {
                        mainVue.alertChgmtJoueur2(plateau2j)
                    } else if (jeux.donneJoueurs().size == 3) {
                        mainVue.alertChgmtJoueur3(plateau3j)
                    } else if (jeux.donneJoueurs().size == 4) {
                        mainVue.alertChgmtJoueur4(plateau4j)
                    }
                    resetPlateau()
                }
            }

            // cas ou le total n'est pas suffisant pour prendre un pickomino
            val de2 = desPlateau[0]
            var same2 = false
            for (i in 0 until desPlateau.size) {
                same2 = desPlateau[i] == de2
                if (!same2) {
                    break
                }
            }
            var tootal = 0
            for (i in desPlateau) {
                tootal += i
            }
            for (i in desGardes) {
                tootal += i
            }
            if (same2 && tootal < 21) {
                if (desPlateau[0] == 6) {
                    jeux.garderLeDe(De(DICE.valueOf("worm")))
                } else {
                    jeux.garderLeDe(De(DICE.valueOf("d${desPlateau[0]}")))
                }
                mainVue.alertRate21()
                if (jeux.listDesDominosDisponible().size == 0) {
                    mainVue.alertFinJeu(musicPlayer)
                    resultat.afficherLesResultats(jeux.donneJoueurs(), jeux.scoreFinal())
                    mainVue.updateVue(resultat)
                    finDePartie = true
                }

                if (!finDePartie) {
                    if (jeux.donneJoueurs().size == 2) {
                        mainVue.alertChgmtJoueur2(plateau2j)
                    } else if (jeux.donneJoueurs().size == 3) {
                        mainVue.alertChgmtJoueur3(plateau3j)
                    } else if (jeux.donneJoueurs().size == 4) {
                        mainVue.alertChgmtJoueur4(plateau4j)
                    }
                resetPlateau()
                }
            }
        }
    }

    // Réinitialise toutes les images du plateau
    fun resetPlateau() {
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

        // tourner les joueurs
        if (jeux.donneJoueurs().size == 2) {
            plateau2j.joueurBas.text = plateau.joueurs[jeux.donneJoueurCourant().donneId() - 1].donneNom()
            plateau2j.joueurBas.style = "-fx-background-color: ${
                plateau.joueurs[jeux.donneJoueurCourant().donneId() - 1].donneCouleur()
            }; -fx-background-radius: 5px;"
            plateau2j.pilePickoBas.graphic = ImageView(
                Image(
                    FileInputStream(
                        "images/pickominos/picko${
                            (jeux.sommetPileDesJoueurs()[jeux.donneJoueurCourant().donneId() - 1].donneValuer())
                        }.png"
                    )
                )
            )
            var id2 = jeux.donneJoueurCourant().donneId() - 1
            if (id2 == 0) {
                id2 = 1
            } else if (id2 == 1) {
                id2 = 0
            }
            plateau2j.joueurHaut2.text = plateau.joueurs[id2].donneNom()
            plateau2j.joueurHaut2.style =
                "-fx-background-color: ${plateau.joueurs[id2].donneCouleur()}; -fx-background-radius: 5px;"
            plateau2j.pilePickoHaut.graphic =
                ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id2].donneValuer())}.png")))
        } else if (jeux.donneJoueurs().size == 3) {
            plateau3j.joueurBas.text = plateau.joueurs[jeux.donneJoueurCourant().donneId() - 1].donneNom()
            plateau3j.joueurBas.style = "-fx-background-color: ${
                plateau.joueurs[jeux.donneJoueurCourant().donneId() - 1].donneCouleur()
            }; -fx-background-radius: 5px;"
            plateau3j.pilePickoBas.graphic = ImageView(
                Image(
                    FileInputStream(
                        "images/pickominos/picko${
                            (jeux.sommetPileDesJoueurs()[jeux.donneJoueurCourant().donneId() - 1].donneValuer())
                        }.png"
                    )
                )
            )
            var id2 = jeux.donneJoueurCourant().donneId() - 1
            if (id2 == 0) {
                id2 = 1
            } else if (id2 == 1) {
                id2 = 2
            } else if (id2 == 2) {
                id2 = 0
            }
            plateau3j.joueurGauche.text = plateau.joueurs[id2].donneNom()
            plateau3j.joueurGauche.style =
                "-fx-background-color: ${plateau.joueurs[id2].donneCouleur()}; -fx-background-radius: 5px;"
            plateau3j.pilePickoGauche.graphic =
                ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id2].donneValuer())}.png")))
            var id3 = jeux.donneJoueurCourant().donneId() - 1
            if (id3 == 0) {
                id3 = 2
            } else if (id3 == 1) {
                id3 = 0
            } else if (id3 == 2) {
                id3 = 1
            }
            plateau3j.joueurDroite.text = plateau.joueurs[id3].donneNom()
            plateau3j.joueurDroite.style =
                "-fx-background-color: ${plateau.joueurs[id3].donneCouleur()}; -fx-background-radius: 5px;"
            plateau3j.pilePickoDroite.graphic =
                ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id3].donneValuer())}.png")))
        } else if (jeux.donneJoueurs().size == 4) {
            plateau4j.joueurBas.text = plateau.joueurs[jeux.donneJoueurCourant().donneId() - 1].donneNom()
            plateau4j.joueurBas.style = "-fx-background-color: ${
                plateau.joueurs[jeux.donneJoueurCourant().donneId() - 1].donneCouleur()
            }; -fx-background-radius: 5px;"
            plateau4j.pilePickoBas.graphic = ImageView(
                Image(
                    FileInputStream(
                        "images/pickominos/picko${
                            (jeux.sommetPileDesJoueurs()[jeux.donneJoueurCourant().donneId() - 1].donneValuer())
                        }.png"
                    )
                )
            )
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
            plateau4j.joueurGauche.text = plateau.joueurs[id2].donneNom()
            plateau4j.joueurGauche.style =
                "-fx-background-color: ${plateau.joueurs[id2].donneCouleur()}; -fx-background-radius: 5px;"
            plateau4j.pilePickoGauche.graphic =
                ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id2].donneValuer())}.png")))
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
            plateau4j.joueurHaut.text = plateau.joueurs[id3].donneNom()
            plateau4j.joueurHaut.style =
                "-fx-background-color: ${plateau.joueurs[id3].donneCouleur()}; -fx-background-radius: 5px;"
            plateau4j.pilePickoHaut.graphic =
                ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id3].donneValuer())}.png")))
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
            plateau4j.joueurDroite.text = plateau.joueurs[id4].donneNom()
            plateau4j.joueurDroite.style =
                "-fx-background-color: ${plateau.joueurs[id4].donneCouleur()}; -fx-background-radius: 5px;"
            plateau4j.pilePickoDroite.graphic =
                ImageView(Image(FileInputStream("images/pickominos/picko${(jeux.sommetPileDesJoueurs()[id4].donneValuer())}.png")))
        }
        plateau2j.boutonVisible(plateau2j.boutonLancer,true)
        plateau3j.boutonVisible(plateau3j.boutonLancer,true)
        plateau4j.boutonVisible(plateau4j.boutonLancer,true)

        for (i in plateau2j.plateauDes.children) {
            i.isVisible = false
        }
        for (i in plateau3j.plateauDes.children) {
            i.isVisible = false
        }
        for (i in plateau4j.plateauDes.children) {
            i.isVisible = false
        }

        if (pickoTop != 0) {
            plateau2j.pickominos.children[pickoTop - 21].isVisible = true
            plateau3j.pickominos.children[pickoTop - 21].isVisible = true
            plateau4j.pickominos.children[pickoTop - 21].isVisible = true
        }

        // désactiver pickominos retournés
        if (jeux.donneJoueurs().size == 2) {
            inc = 15
            for (i in inc downTo 1) {
                if (this.pickoPerdu > this.dernierPicko) {
                    break
                } else if (!plateau2j.pickominos.children[i].isVisible) {
                    inc--
                } else if (plateau2j.pickominos.children[i].isDisable) {
                    inc--
                } else if (!plateau2j.pickominos.children[i].isDisable) {
                    plateau2j.pickominos.children[i].isDisable = true
                    break
                }
            }
        } else if (jeux.donneJoueurs().size == 3) {
            inc = 15
            for (i in inc downTo 1) {
                if (this.pickoPerdu > this.dernierPicko) {
                    break
                } else if (!plateau3j.pickominos.children[i].isVisible) {
                    inc--
                } else if (plateau3j.pickominos.children[i].isDisable) {
                    inc--
                } else if (!plateau3j.pickominos.children[i].isDisable) {
                    plateau3j.pickominos.children[i].isDisable = true
                    break
                }
            }
        } else if (jeux.donneJoueurs().size == 4) {
            inc = 15
            for (i in inc downTo 1) {
                if (this.pickoPerdu > this.dernierPicko) {
                    break
                } else if (!plateau4j.pickominos.children[i].isVisible) {
                    inc--
                } else if (plateau4j.pickominos.children[i].isDisable) {
                    inc--
                } else if (!plateau4j.pickominos.children[i].isDisable) {
                    plateau4j.pickominos.children[i].isDisable = true
                    break
                }
            }
        }
    }
}