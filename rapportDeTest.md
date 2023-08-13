# **Rapport de test**

## **Point de vigilance vis à vie de la testabilité**

Afin de faciliter les interactions dans le jeu Pickomino, M. Lanoix a développé une librairie `pickomino-lib` nous permettant l'utilisation de plusieurs classes et méthodes pour lancer de dés ou bien encore passer au joueur suivant une fois qu’un joueur a terminé son tour, pouvoir garder ou voler un Pickomino des autres joueurs,...

Pour pouvoir ainsi proposer un jeu de qualité, nous allons devoir effectuer plusieurs tests. Toutefois, avant de commencer la conception et l'implémentation des cas de tests, nous allons commencer par identifier les différents points de vigilance vis-à-vis de la testabilité sur notre jeu et la librairie.

Il existe plusieurs points de vigilance à prendre en compte vis-à-vie de la testabilité avec la librairie `Pickomino-lib`.

### **L'observabilité :**

L'observabilité dans une librairie offre une visibilité sur l'état du jeu, facilite les interactions, améliore la testabilité, facilite le débogage et le diagnostic.

La librairie `pickomino-lib` possède plusieurs classes tels que Connector, Game, ... Ces classes possèdent principalement des attributs et des méthodes publics facilitant ainsi l'interaction avec le jeu, ainsi que l'accès à l'état du jeu. Même si ces classes possèdent des méthodes et des attributs privés, malgré cela, la librairie et l'état du jeu restent souvent observable.

Dans le cadre de notre application, nous avons adopté l'architecture MVC (Modèle Vue Contrôleur) lors de la conception du diagramme de classe (Modèle). Nous avons veillé à rendre l'état du jeu aussi visible que possible, ce qui facilite sa testabilité.

Néanmoins, il convient de noter qu'il existe certaines limitations en termes d'observabilité. Par exemple, nous n'avons pas la possibilité d'observer l'environnement tel que le temps qui s'écoule depuis la librairie.

Enfin, aucune intéraction n'est présente entre notre application et d'autres logiciels.

### **La contrôlabilité :**

La contrôlabilité est essentielle dans une librairie, car elle permet de manipuler et de contrôler le système selon des besoins et des scénarios de test. La librairie que nous utilisons offre une flexibilité permettant de contrôler le jeu et les interactions avec les différentes classes associées.

L'une des fonctionnalités qui nous est offerte est la possibilité de choisir le résultat des lancés de dés en utilisant la méthode `choiceDices` de la classe `Connector`. Toutefois, il faut lancer le jeu en mode debug, ce qui garantit que cette manipulation ne se produit pas dans un contexte de jeu réel.

De plus, cette capacité de contrôle n'étant pas limitée à la librairie, nous avons mis en place ce mécanisme dans notre modèle également.

Néanmoins, il existe certaines limitations dans le niveau de contrôle que nous pouvons exercer. Par exemple, il n'est pas possible de retirer des Pickominos qui se trouvent au centre, ni de choisir les dés d'un joueur sans faire un lancé. Une telle contrôlabilité nous aurait permis de réduire les lignes de code dans nos fonctions de test.

### **La disponibilité :**

La librairie est mise à disposition avec une version suffisamment avancée pour pouvoir effectuer des tests et l'utiliser dans notre application, elle nous est fournie en boîte noire (ce qui signifie que le code source de la librairie n'est pas accessible). 

Cependant, bien que le code source ne soit pas connu, la librairie `pickomino-lib` est accompagnée d'une excellente spécification courte mais claire, ce qui nous permet de comprendre la signification de chaque classe, ainsi que les actions réalisées par chaque méthode. Cela nous facilite l'utilisation de la librairie dans notre application et sa testabilité, ce qui est le plus important.

### **La stabilité :**

La stabilité d'une librairie est une caractéristique essentielle, car elle garantit le bon fonctionnement ainsi qu'une bonne fiabilité de la librairie.

La librairie est dans un état stable vis-à-vis de la testabilité, c'est-à-dire qu'elle fonctionne de manière fiable et prévisible. Les modifications apportées par la librairie n'introduisent pas d'erreurs ou de comportements inattendus.

De plus, elle fournit un suivi régulier de l'état du jeu. Toutefois, il faut faire attention aux injections de dépendances car il y a des dépendances qui ne dépendent pas des données de tests (Serveur du jeu par exemple).



## Conception des tests
### Connector

#### **newGame(nbPlayers : Int) Pair<Int, Int>**
1. Variables forment les données de tests : nbPlayers
2. On partitionne les données de tests
    
    a. Type : Int 
    
    b. Plage : [-2^31 ; 2^31-1]
    
    c. Fonctionellement : [-2^31 ; 1[ (partition exceptionnelle) ; [2 ; 4] (partition nominale) ;  ]4 ; 2^31-1[ (partition exceptionnelle)


3. Table de décision 

|          |                        |                                |   |   |   |
|:--------:|:-----------------------|:-------------------------------|---|---|---|
| DT       | nbPlayers              | [-2^31 ; 1[                    | X |   |   |
|          |                        | [2 ; 4]                        |   | X |   |
|          |                        | ]4 ; 2^31-1]                   |   |   | X |
| oracle   | nbPlayer incorrect     | Pair<-1, -1>                   | X |   | X |
|          | id et clé d'une partie | Pair<Int, Int> != Pair<-1 ,-1> |   | X |   |

4. Cas de test
* CT1 (DT1(1),Pair<-1, -1>)
* CT2 (DT2(2),Pair<Int, Int> != Pair<-1 ,-1>)
* CT3 (DT3(8),Pair<-1, -1>)



#### **rollDices(id : Int, key : Int) : List<DICE>**
1. Variables forment les Données de tests : id, key
2. Partitionne les données de tests
    Partitionement de l'id :
    
    a. Type : Int
    
    b. Plage : [-2^31 ; 2^31-1]
    
    c. Fonctionellement : correct ; incorrect

    Partitionement de key :
    
    a. Type : Int
    
    b. Plage : [-2^31 ; 2^31-1]
    
    c. Fonctionellement : correct ; incorrect

3. Table de décision 

|        |                              |                     |   |   |   |   |
|:------:|:-----------------------------|:--------------------|---|---|---|---|
| DT     | Id                           | correct             | X |   |\| | X |
|        |                              | incorrect           |   | X |\| |   |
|        | Key                          | correct             | X |\| |   | X |
|        |                              | incorrect           |   |\| | X |   |
|        | STATUS = (ROLL_DICE ou       | True                | X |\| |\| |   |
|        | ROLL_DICE_OR_TAKE_PICKOMINO) | False               |   |\| |\| | X |
| Oracle | Réaliser un lancer de dés    |                     | X |   |   |   |
|        | UnknownIdException           |                     |   | X |   |   |
|        | IncorrectKeyException        |                     |   |   | X |   |
|        | BadStepException             |                     |   |   |   | X |

4. Cas de test
* CT1(DT1(`correct`,`correct`),List<DICE>)
* CT2(DT2(-1,`correct`),UIE)
* CT3(DT3(`correct`,-1),IKE)
* CT4(DT4(`correct`,`correct`),BSE) -> mauvaise étape du jeu

2 cas de test bonus : 
* On pourra vérifier si après un lancé raté, le jeu passe au joueur suivant et le dernier pickomino pris par le joueur est remis au centre et le pickomino de plus grande valeur est retourné.
* De plus, si le picjomino qu'est remis est le pickomino avec la plus grande valeur, il ne doit pas se retourné

#### **keepDices(id : Int, key : Int, dice : DICE) : Boolean**
1. Variables forment les données de tests : id, key, dice
2. Partitionnement des données de tests
    Partitionement de l'id :
    
    a. Type : Int
    
    b. Plage : [-2^31 ; 2^31-1]
    
    c. Fonctionellement : correct ou incorrect

    Partitionnement de key :
    
    a. Type : Int
    
    b. Plage : [-2^31 ; 2^31-1]
    
    c. Fonctionellement : correct ou incorrect

    Partitionnement ddice :
    
    a. Type : DICE
    
    b. Plage : {DICE.d1, DICE.d2, DICE.d3, DICE.d4, DICE.d5, DICE.worm}
    
    c. Fonctionellement : correct, déjà pris, n'est pas obtenue dans la lancé

3. Table de décision

|        |                           |             |   |   |   |   |   |   |
|:------:|:--------------------------|:------------|---|---|---|---|---|---|
| DT     | Id                        | correct     | X |   |\| | X | X | X | 
|        |                           | incorrect   |   | X |\| |   |   |   |   
|        | Key                       | correct     | X |\| |   | X | X | X | 
|        |                           | incorrect   |   |\| | X |   |   |   |   
|        | dice                      | correct     | X |\| |\| | X |   |   |
|        |                           | non obtenue |   |\| |\| |   | X |   |
|        |                           | déjà pirs   |   |\| |\| |   |   | X |
|        | STATUS == KEEP_DICE       | True        | X |\| |\| |   | X | X |   
|        |                           | False       |   |\| |\| | X |   |   |   
| Oracle | True                      |             | X |   |   |   |   |   |   
|        | False                     |             |   |   |   | X | X | X |   
|        | UnknownIdException        |             |   | X |   |   |   |   |   
|        | IncorrectKeyException     |             |   |   | X |   |   |   |   
|        | BadStepException          |             |   |   |   | X |   |   |   
|        | DiceNotInRollException    |             |   |   |   |   | X |   | 
|        | DiceAlreadyKeptException  |             |   |   |   |   |   | X | 

4. Cas de test 
* CT3(DT3(`id : correct`,`key : correct`,DICE.d1),true)
* CT1(DT1(-1,`key : correct`,DICE.d1),UIE)
* CT2(DT2(`id : correct`,-1,DICE.d1)IKE)
* CT4(DT4(`id : correct`,`key : correct`,DICE.d1),BSE)
* CT5(DT5(`id : correct`,`key : correct`.d1),DNIRE) --> le dé n'était pas obtenue lors de la lancé
* CT6(DT6(`id : correct`,`key : correct`,DICE.d1)DAKE) --> le dé à déjà été pris



#### **GameState (id : Int, key : Int) : Game (à demander à M. Mottu)**
1. Variables forment les Données de tests : id, key 
   Exceptions retournés :BadStepException

2. Partitions données de tests : 

    Partitionnement de l'id :

    a. Type : Int

    b. Plage : [-2^31 ; 2^31-1]

    c. Fonctionellement : correct ou incorrect

    Partitionnement de key :

    a. Type : Int

    b. Plage : [-2^31 ; 2^31-1]

    c. Fonctionellement : correct ou incorrect


3. Table de décision 

|        |                           |                     |   |   |   |      
|:------:|:--------------------------|:--------------------|---|---|---|
| DT     | Id                        | valide              |   |\| | X | 
|        |                           | invalide            | X |\| |   |
|        | Key                       | valide              |\| |   | X | 
|        |                           | invalide            |\| | X |   | 
|--------|---------------------------|---------------------|---|---|---|   
| Oracle | status-game               |                     |   |   | X |   
|        | UnknownIdException        |                     | X |   |   |  
|        | IncorrectKeyException     |                     |   | X |   |


4. Cas de test
* CT1(DT1(-1,5897),UIE)
* CT2(DT2(5897,-1),IKE)
* CT3(DT3(1351,351531), status-game) 


#### **takePickomino(id : Int, key : Int, pickomino : Int) : Boolean OK** 
1. Variables forment les Données de tests : id, key, pickomino 
   Exceptions retournés : BadPickominoChosenException,  BadStepException

2. Partitionne les données de tests : 

    Partitionnement de l'id :

    a. Type : Int

    b. Plage : [-2^31 ; 2^31-1]

    c. Fonctionellement : correct ou incorrect

    Partitionnement de key :

    a. Type : Int

    b. Plage : [-2^31 ; 2^31-1]

    c. Fonctionellement : correct ou incorrect


    Partitionnement de pickomino :

    a. Type : Int
    
    b. Plage : [-2^31 ; 2^31-1]

    c. Fonctionellement : 
    -  [-2^31 , 20] --> Partition Exceptionnelle 
    -  [21 , 36] --> Partition nominale 
    -  [37 , 2^31-1] --> Partition Exceptionnelle 

3. Table de décision 

|        |                           |                     |   |   |   |   |   |   |   |   |
|:------:|:--------------------------|:--------------------|---|---|---|---|---|---|---|---|
| DT     | Id                        | valide              |   |\| | X | X | X | X | X | X |
|        |                           | invalide            | X |\| |   |   |   |   |   |   |
|        | Key                       | valide              |\| |   | X | X | X | X | X | X |  
|        |                           | invalide            |\| | X |   |   |   |   |   |   |
|        | pickomino                 | [-2^31 , 20]        |\| |\| | X |   |   |   |   |   |
|        |                           | [21 , 36]           |\| |\| |\| | X | X |   | X | X |
|        |                           | [37 , 2^1-1]        |\| |\| |\| |   |   | X |   |   |
|        | STATUS_TAKE_PICKOMINO     | Actif               |\| |\| |\| | X |   |\| | X | X |
|        |                           | Non Actif           |\| |\| |\| |   | X |\| |   |   |
|        |  PickominoDisponible      | True                |\| |\| |\| | X |\| |\| |   | X |
|        |                           | False               |\| |\| |\| |   |\| |\| | X |   |
|        |  Dés comportant un vers   | True                |\| |\| |\| | X |\| |\| |\| |   |
|        |                           | False               |\| |\| |\| |   |\| |\| |\| | X |
|--------|---------------------------|---------------------|---|---|---|---|---|---|---|---|
| Oracle | Prends Pickomino          |                     |   |   |   | X |   |   |   |   |
|        | UnknownIdException        |                     | X |   |   |   |   |   |   |   |
|        | IncorrectKeyException     |                     |   | X |   |   |   |   |   |   |
|        | BadStepException          |                     |   |   |   |   | X |   |   | X |
|        |BadPickominoChosenException|                     |   |   | X |   |   | X | X |   |


4. Cas de test
* CT1(DT1(-1,5454, 23),UIE)
* CT2(DT2(1442,-1, 23),IKE)
* CT3(DT3(1442,5454, 2),BPCE)
* CT4(DT4(1442,5454, 23), true)
* CT5(DT5(1442,5454, 23), BPCE) --> Si il n'est pas dans le top de la pile
* CT6(DT6(1442,5454, 23), BSE) --> Si ce n'est pas STATUS TAKE PICKOMINO
* CT7(DT6(1442,5454, 23), BSE) --> Si le dés ne comporte pas de vers 
* CT8(DT6(1442,5454, 23), BPCE) --> Si le pickpmino n'est pas disponible  


#### **choiceDices(id: Int, key: Int, dices: List<DICE>): List<DICE>**

1. Variables forment les données de test

2. Partitionnement des données de test

Partitionnement de id :

a. Type : Int

b. Plage : [-2³31 ; 2³31-1]

c. Fonctionnellement : id correct ou incorrect

Partitionnement de key :

a. Type : Int

b. Plage : [-2³31 ; 2³31-1]

c. Fonctionnellement : key correct ou incorrect

Partionnement de dices

a. Type : List<Dice>

b. Plage : {DICE.d1,DICE.d2,DICE.d3,DICE.d4,DICE.d5,DICE.worn}

c. Fonctionnellement : de même

La liste des dés peut être vide

3. Table de décision


|        |                                |           |   |   |   |   |
|--------|--------------------------------|-----------|---|---|---|---|
| DT     | id                             | correct   | X |   |\| | X |
|        |                                | incorrect |   | X |\| |   |
|        | key                            | correct   | X |\| |   | X |
|        |                                | incorrect |   |\| | X |   |
|        | STATUS == ROLL_DICE            | True      | X |\| |\| |   |
|        | ou ROLL_DICE_OR_TAKE_PICKOMINO | False     |   |\| |\| | X |
| Oracle | List<DICE>                     |           | X |   |   |   |
|        | UnknownIdException             |           |   | X |   |   |
|        | IncorrectKeyException          |           |   |   | X |   |
|        | BadStepException               |           |   |   |   | X |

4. Cas de test

- CT1(DT1(`id : correct`,`key : correct`,listOf(DICE.d1,DICE.d3)), listOf(DICE.d1,DICE.d3))
- CT1(DT1(-1,`key : correct`,listOf(DICE.d1,DICE.d3)), UIE)
- CT1(DT1(`id : correct`,-1,listOf(DICE.d1,DICE.d3)), IKE)
- CT1(DT1(`id : correct`,`key : correct`,listOf(DICE.d1,DICE.d3)), BSE)


#### finalScore (id : Int, key : Int) : List<Int>
1. Variables forment les Données de tests : id, key
   Exceptions retournés :BadStepException
2. Partitionne les données de tests :

   Partitionement de l'id :
   a. Type : Int
   b. Plage : [-2^31 ; 2^31-1]
   c. Fonctionellement :
   - [-2^31 ; -1] --> Partition exceptionnelle
   - [0 ; 2^31-1] --> Partition nominale

   Partitionement de key :
   a. Type : Int
   b. Plage : [-2^31 ; 2^31-1]
   c. Fonctionellement :
   - [-2^31 ; -1] --> Partition exceptionnelle
   - [0 ; 2^31-1] --> Partition nominale




3. Table de décision

|        |                           |                     |   |   |   |   |
|:------:|:--------------------------|:--------------------|---|---|---|---|
| DT     | Id                        | correct             | X |   |\| | C | 
|        |                           | incorrect           |   | X |\| |   |
|        | Key                       | correct             | X |\| |   | C | 
|        |                           | incorrect           |   |\| | X |   |
|        | STATUS=GAME_FINISHED      | True                | X |\| |\| |   |
|        |                           | False               |   |\| |\| | C |
| Oracle | list<Int>                 |                     | X |   |   |   |
|        | UnknownIdException        |                     |   | X |   |   |
|        | IncorrectKeyException     |                     |   |   | X |   | 
|        | BadStepException          |                     |   |   |   | X | 

4. Cas de test
* CT1(DT1(`id : correct`,`key : correct`), list<Int>) --> Mettre le status dans une variable
* CT2(DT2(-1,`key : correct`),UIE)
* CT3(DT3(`id : correct`,-1),IKE)
* CT4(DT4(`id : correct`,`key : correct`),BSE) --> Mettre le status dans une variable


### Game

#### **accessiblePickos(): List<Int>**

Pour cette méthode, il faut tester si après qu'un joueur ait pris un pickomino ou reposé son pickomino, la liste se mette à jour. De plus, il faut vérifer que après un tour raté, le pickomino de plus grande valeur soit retourné (2 cas à tester)

Pour cette méthode, il est difficile de faire une table de décision dans la mesure où les données de tests (DTS) ne dépendent pas des variables d'entrés mais dépendent de ce que les joueurs ont joués.

Voici les cas de tests :

- CT1 : Vérifier quand un joueur prend un pickomino, ce dernier soit bien retiré de la liste.
- CT2 : Vérifier quand un joueur rate son tour, le pickomino qui se trouve au sommet de sa pile se remet en jeu et le pickomino de plus grande valeur se retire de la liste.
- CT3 : Vérifier quand un joueur rate son tour, le pickomino qui se trouve au sommet de sa pile se remet en jeu et comme c'est le pickomino de plus grande valeur, il ne doit pas se retirer de la liste des pickominos disponibles

#### **pickosStackTops(): List<Int>**

Il s'agit pour cette méthode de vérifier si la méthode d'une part, renvoie bien les bons pickominos qui se trouvent en haut de la pile de chaque joueur sous la forme d'une liste où les pickominos sont ordonnés par le numéro des joueurs. D'autre part, il faut aussi vérifier que la méthode gére bien les cas de vol et de tour raté.

Comme les cas de test dépend des autres évenements, il est difficile d'exprimer ces cas de tests par écrit. Néanmoins, voici les procédures que nous allons effectuer sur la librairie. :

- CAT1 : Après avoir lancé une partie et pris un pockominos par joueur, vérifier si la liste renvoyée correspond bien aux pickominos qui se trouvent au sommet de la pile de chacun des joueurs.
- CT2 ; Reprendre le même état du jeu et effectuer un vol de pickomino afin de vérifier si la méthode gére bien ce cas.
- CT3 ; Reprendre le même état du jeu et faire en sort de raté le tour d'un joueur afin de vérifier si la méthode gère bien ce cas.


#### score(): List<Int>

Nous trouvons que cette méthode n'est pas utile pendant les parties, c'est pour cette raison que nous n'allons pas la tester.

### State
Cette classe ne possède pas de méthode qui intéragie avec le jeu. Cependant, elle possèdent des attributs qui contient des valeurs intéréssantes comme la liste des dés de la lancé.

#### keptDices: List<DICE>
Cet attribut stock la liste des dés gardé par le joueur actuel.

Pour cet attribut, il faut tester si au début la liste est bien vide et que quand un joueur choisie des dés, la liste se met bien à jour.

Voici les cas de test :
* CT1 : Au début du tour la liste est vide.
* CT2 : Après qu'un joueur ait choisi ses dés, la liste doit se mettre à jour.
* CT3 : A la fin du tour, la liste doit se réinitialiser.

#### player: Int
Cet attribut contient l'id du joueur courant.

Il faut tester que l'id change à la fin du tour (tour réussi ou raté)

Les cas de tests :
* CT1 : Au début de la partie, c'est joueur avec l'id = 0 qui doit commencer.
* CT2 : Après avoir choisi un pickomino, c'est au joueur suivant de jouer.
* CT4 : Après un tour raté, c'est au joueur suivant de jouer.


#### rolls: List<DICE>
Cette attribut contient la liste des dés qui représente la lancé réalisée par le joueur courant.

il faut tester avant que le joueur fasse le lancé, et après que le joueur ait choisi son pickomino, la liste doit se réinitialiser.

Voici les cas de tests :
* CT1 : Au début la liste doit être vide.
* CT2 : Après un lancé, la liste doit représenter les dés obtenus
* CT3 : Après qu'un joueur ait choisi de garder des dés, la liste doit se réinitialiser.
* CT4 : Suite à un tour raté, la liste doit se réinitialiser.

### STATUS
Cette classe indique à quelle étapes le jeu est rendu.

Il faut vérifier que le jeu met bien à jour l'état de la partie.

Il faut donc vérifier le statue du jeu à chaque étape :

Voici les cas de tests :
* CT1 : STATUS == STATUS.ROLL_DICE : Etape "dés à lancer" du tour courant

* CT2 : STATUS == STATUS.KEEP_DICE : Etape "dés à prendre" du tour courant

* CT3 : STATUS == STATUS.ROLL_DICE_OR_TAKE_PICKOMINO : Etape "dés à lancer ou pickomino à prendre" du tour courant

* CT4 : STATUS == STATUS.TAKE_PICKOMINO : Etape "Pickomo à prendre" du tour courant

* CT5 : STATUS == STATUS.GAME_FINISHED : Etape "Partie terminée"

### DICE
Pour cette classe, il faut vérifier si l'attribue ` ordinal: Int` si revoi bien la bonne valeur

|        |           |   |   |   |   |   |   |
|--------|-----------|---|---|---|---|---|---|
| DT     | DICE.d1   | X |   |   |   |   |   |
|        | DICE.d2   |   | X |   |   |   |   |
|        | DICE.d3   |   |   | X |   |   |   |
|        | DICE.d4   |   |   |   | X |   |   |
|        | DICE.d5   |   |   |   |   | X |   |
|        | DICE.worm |   |   |   |   |   | X |
| Oracle | 1         | X |   |   |   |   |   |
|        | 2         |   | X |   |   |   |   |
|        | 3         |   |   | X |   |   |   |
|        | 4         |   |   |   | X |   |   |
|        | 5         |   |   |   |   | X | X |

Cas de test : 
* CT1(DT1(DICE.d1),0)
* CT2(DT1(DICE.d2),1)
* CT3(DT1(DICE.d3),2)
* CT4(DT1(DICE.d4),3)
* CT5(DT1(DICE.d5),4)
* CT6(DT1(DICE.worm),4)


## Synthèse 

Nous n'avons pas détecté des erreurs dans la librairie `pickomino-lib`. Néanmoins, la conception et l'implémentation de ces cas de tests nous ons permis d'une part, d'améliorer notre maîtrise de la librairie et vérifier son bon fonctionnement par rapport à la spécification. D'autre part, de développer notre capacité de tester des systèmes complexes comme ce dernier.