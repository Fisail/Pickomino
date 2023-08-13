package Controleurs.ControleursChoixJoueurs

import Modele.Jeux
import Modele.Joueur
import Vues.*
import iut.info1.pickomino.Connector
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler

class ControleurButtonCommencer(mainVue: MainVue, choixJoueurs: ChoixJoueurs, plateau: Plateau, plateau4j: Plateau4j, plateau3j: Plateau3j, plateau2j: Plateau2j, jeux: Jeux, joueur: Joueur) : EventHandler<ActionEvent> {
    private val jeux: Jeux
    private val mainVue : MainVue
    private var choixJoueurs : ChoixJoueurs
    private val plateau : Plateau
    private var plateau4j : Plateau4j
    private val plateau3j : Plateau3j
    private val plateau2j : Plateau2j
    private val joueur : Joueur

    init {
        this.jeux = jeux
        this.mainVue = mainVue
        this.choixJoueurs = choixJoueurs
        this.plateau = plateau
        this.plateau4j = plateau4j
        this.plateau3j = plateau3j
        this.plateau2j = plateau2j
        this.joueur = joueur
    }

    override fun handle(event: ActionEvent) {
        if (choixJoueurs.nbJoueurs == 4) {
            mainVue.updateVue(plateau4j)
        } else if (choixJoueurs.nbJoueurs == 3) {
            mainVue.updateVue(plateau3j)
        } else {
            mainVue.updateVue(plateau2j)
        }

        // changement noms joueurs si nom > 15 char
        choixJoueurs.changementNomJoueur(choixJoueurs.choixNomJoueur1)
        choixJoueurs.changementNomJoueur(choixJoueurs.choixNomJoueur2)
        choixJoueurs.changementNomJoueur(choixJoueurs.choixNomJoueur3)
        choixJoueurs.changementNomJoueur(choixJoueurs.choixNomJoueur4)

        //Ajoute le nom du joueur dans la vue
        choixJoueurs.ajouterNomJoueur(0, choixJoueurs.choixNomJoueur1.text)
        choixJoueurs.ajouterNomJoueur(1, choixJoueurs.choixNomJoueur2.text)
        choixJoueurs.ajouterNomJoueur(2, choixJoueurs.choixNomJoueur3.text)
        choixJoueurs.ajouterNomJoueur(3, choixJoueurs.choixNomJoueur4.text)

        // ajout des joueurs dans la liste des joueurs
        plateau4j.joueurBas.text = choixJoueurs.retourneListeJoueurs()[0]
        plateau4j.joueurGauche.text = choixJoueurs.retourneListeJoueurs()[1]
        plateau4j.joueurHaut.text = choixJoueurs.retourneListeJoueurs()[2]
        plateau4j.joueurDroite.text = choixJoueurs.retourneListeJoueurs()[3]

        plateau3j.joueurBas.text = choixJoueurs.retourneListeJoueurs()[0]
        plateau3j.joueurGauche.text = choixJoueurs.retourneListeJoueurs()[1]
        plateau3j.joueurDroite.text = choixJoueurs.retourneListeJoueurs()[2]

        plateau2j.joueurBas.text = choixJoueurs.retourneListeJoueurs()[0]
        plateau2j.joueurHaut2.text = choixJoueurs.retourneListeJoueurs()[1]

        val listJoueurs = choixJoueurs.retourneJoueurs()
        this.plateau.joueurs = listJoueurs

        //Création de la partie + affichage des joueurs sur le plateau.
        jeux.creerLaPartie(listJoueurs)
        plateau.afficherLesJoueursPlateau(jeux.donneJoueurs())
    }
}