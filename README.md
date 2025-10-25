1. Introduction

Ce projet propose un jeu de capture et de combat de créatures, inspiré dans son concept par la franchise Pokémon, développé en Kotlin. Il vise à démontrer l’usage concret des principes fondamentaux de la programmation orientée objet tout en offrant une expérience de jeu complète, modulaire et évolutive.

1.1 Objectifs du projet

Mettre en place une architecture logique, modulaire et extensible.

Implémenter des mécaniques RPG : progression de niveau, combat tour-par-tour, capture de créatures, gestion d’inventaire.

Appliquer les concepts avancés de POO : héritage, polymorphisme, encapsulation, composition.

Proposer une interface utilisateur en ligne de commande (CLI) avec un affichage coloré pour l’immersion.

1.2 Fonctionnalités principales

Combat au tour par tour avec calculs de dégâts, bonus/malus selon type élémentaire.

Gestion d’une équipe de monstres, d’un coffre de monstre, et d’un sac d’objets pour un joueur.

Progression par accumulation d’expérience, montée de niveaux, recalcul de statistiques.

Système de types élémentaires avec forces, faiblesses et immunités.

Capture de monstres sauvages via un item dédié.

Exploration de zones interconnectées avec génération aléatoire de monstres.

2. Architecture du projet
2.1 Diagramme de classes simplifié
Item (abstract class)
   ├── Badge
   └── MonsterKube (implements Utilisable)

Entraineur
   ├ equipeMonstre : MutableList<IndividuMonstre>
   ├ boiteMonstre   : MutableList<IndividuMonstre>
   └ sacAItems     : MutableList<Item>

EspeceMonstre
   └ IndividuMonstre
        └ techniques : List<Technique>

Zone
   └ especesMonstres : List<EspeceMonstre>

Element
   ├ forces     : List<Element>
   ├ faiblesses : List<Element>
   └ immunises  : List<Element>

3. Analyse détaillée des composants
3.1 Package dresseur – Classe Entraineur

La classe Entraineur représente un joueur ou un adversaire dans le jeu. Elle contient trois collections : l’équipe active (equipeMonstre), la boîte de monstres stockés (boiteMonstre), et le sac d’objets (sacAItems). Ces collections sont initialisées par défaut avec des listes vides, ce qui évite les exceptions de type null.
Par exemple :

class Entraineur(
    var id: Int,
    var nom: String,
    var argents: Int,
    var equipeMonstre: MutableList<IndividuMonstre> = mutableListOf(),
    var boiteMonstre: MutableList<IndividuMonstre> = mutableListOf(),
    var sacAItems: MutableList<Item> = mutableListOf()
)


Points forts relevés

Utilisation de valeurs par défaut pour les listes mutables pour garantir l’initialisation.

Distinction claire entre les monstres actifs (équipe) et stockés (boîte).

Le polymorphisme est utilisé puisque sacAItems peut contenir tout objet de type Item.

Méthode d’affichage

fun afficheDetail(){
    println("Dresseur : ${this.nom}")
    println("Argents : ${this.argents}")
    println("Équipe (${equipeMonstre.size}) : " + equipeMonstre.joinToString { it.nom })
    println("Boîte (${boiteMonstre.size}) : " + boiteMonstre.joinToString { it.nom })
}


Utilisation de joinToString { it.nom } pour afficher uniquement les noms de monstres.

Interpolation de chaînes via ${}.

3.2 Package monstre – EspeceMonstre

La classe EspeceMonstre définit un « template » pour les monstres : leurs statistiques de base, leurs modificateurs de croissance, leurs types élémentaires, etc.

class EspeceMonstre(
    var id: Int,
    var nom: String,
    var type: String,
    val baseAttaque: Int,
    val baseDefense: Int,
    val baseVitesse: Int,
    val baseAttaqueSpe: Int,
    val baseDefenseSpe: Int,
    val basePv: Int,
    val modAttaque: Double,
    val modDefense: Double,
    val modVitesse: Double,
    val modAttaqueSpe: Double,
    val modDefenseSpe: Double,
    val modPv: Double,
    val description: String = "",
    val particularites: String = "",
    val caractères: String = "",
    val elements: MutableList<Element> = mutableListOf(),
    var paliersTechniques: MutableList<PalierTechnique> = mutableListOf()
)


Analyse

Les statistiques de base sont définies avec val (immuables après construction), tandis que les modificateurs de croissance sont mutables selon le besoin.

Les types élémentaires et les paliers techniques sont représentés par des listes mutables, permettant l’extension future.

Les paramètres optionnels avec valeurs par défaut augmentent la flexibilité de l’instanciation.

Méthode d’affichage d’art ASCII

fun afficheArt(deFace: Boolean = true): String {
    val nomFichier = if (deFace) "front" else "back"
    val chemin = "src/main/resources/art/${this.nom.lowercase()}/$nomFichier.txt"

    return try {
        val art = File(chemin).readText()
        val safeArt = art.replace("/", "∕")
        safeArt.replace("\\u001B", "\u001B")
    } catch (e: Exception) {
        "Erreur : ASCII art non trouvé pour ${this.nom} ($chemin)"
    }
}


Construction du chemin de fichier par interpolation de chaîne.

Utilisation de bloc try-catch pour gérer les erreurs de lecture.

Remplacement de caractères pour garantir l’affichage correct et la manipulation des codes ANSI.

3.3 Package monstre – IndividuMonstre

Cette classe représente une instance particulière d’une espèce, avec des statistiques qui évoluent, une expérience, un niveau, etc.

Initialisation aléatoire des statistiques

init {
    attaque = espece.baseAttaque + Random.nextInt(-2, 3)
    // …
    pvMax = espece.basePv + Random.nextInt(-5, 6)
    if (pvMax < 1) pvMax = 1
    pv = pvMax
    this.exp = expInit  // déclenche le setter
}


Génération de variation aléatoire (ex. Random.nextInt(-2,3) → –2 à +2).

Validation pour s’assurer que pvMax ≥ 1.

Affectation de expInit déclenchant automatiquement la gestion du level-up.

Setter pour exp avec automatisation du passage de niveau

var exp: Double = 0.0
    set(value) {
        field = if (value < 0) 0.0 else value
        while (field >= palierExp(niveau + 1)) {
            levelUp()
        }
    }


Utilisation de field pour accéder au champ interne.

Validation : l’expérience ne peut pas être négative.

while permet de gérer plusieurs montées de niveau successives.

Clamping pour les PV

var pv: Int = 0
    set(nouveauPv) {
        field = when {
            nouveauPv < 0 -> 0
            nouveauPv > pvMax -> pvMax
            else -> nouveauPv
        }
    }


when sans argument pour plusieurs conditions.

Garantit que pv reste dans l’intervalle valide [0, pvMax].

Méthode levelUp() intégrée avec fonction locale

fun levelUp() {
    niveau++

    fun calcStat(baseStat: Int, modCarac: Double, potentiel: Double, randomRange: IntRange): Int {
        val statBonus = (modCarac * potentiel).roundToInt() +
                        Random.nextInt(randomRange.first, randomRange.last + 1)
        return baseStat + statBonus
    }

    attaque = calcStat(attaque, espece.modAttaque, potentiel, -2..2)
    defense = calcStat(defense, espece.modDefense, potentiel, -2..2)
    // …
}


Définition d’une fonction locale calcStat() : bon usage de l’encapsulation de logique.

Production de code « DRY » (ne pas répéter pour chaque statistique).

Incrémentation du niveau et recalcul des statistiques.

Méthode apprendreTechnique()

Vérification initiale : si la technique est déjà connue → sortie rapide.

Si l’équipe de techniques n’est pas pleine : ajout simple.

Sinon : boucle d’interaction utilisateur pour choisir quelle technique oublier.
Extraits :

if (techniques.contains(nouvelleTechnique)) {
    println("${espece.nom} connaît déjà ${nouvelleTechnique.nom} !")
    return
}
...
do {
    print("Votre choix : ")
    choix = readLine()?.toIntOrNull() ?: -1
    ...
} while (choix !in 1..techniques.size)


Utilisation d’early return pour sortir rapidement.

Gestion sécurisée de l’entrée utilisateur via toIntOrNull() + opérateur Elvis ?:.

Validation via choix !in 1..techniques.size.

3.4 Package item
Classe MonsterKube

Cette classe hérite de Item (classe abstraite) et implémente l’interface Utilisable.

class MonsterKube(
    id: Int, nom: String, description: String,
    var chanceCapture: Double
) : Item(id, nom, description), Utilisable


Méthode utiliser()

override fun utiliser(cible: IndividuMonstre): Boolean {
    if (cible.entraineur != null) {
        println("Le monstre ne peut pas être capturé.")
        return false
    }

    val ratioVie = cible.pv.toDouble() / cible.pvMax.toDouble()
    var chanceEffective = chanceCapture * (1.5 - ratioVie)
    chanceEffective = chanceEffective.coerceAtLeast(5.0)

    val nbAleatoire = (0..100).random()
    return if (nbAleatoire < chanceEffective) {
        println("Le monstre est capturé !")
        true
    } else {
        println("Presque ! Le Kube n’a pas pu capturer le monstre !")
        false
    }
}


Vérification que la cible n’est pas déjà capturée (entraineur != null).

Calcul du ratio de vie pour ajuster la chance de capture.

Utilisation de coerceAtLeast(5.0) pour garantir une probabilité minimale.

Génération aléatoire via (0..100).random().

Retour booléen indiquant succès ou échec, avec message à l’utilisateur.

3.5 Package monde – Classe Zone
Méthode genereMonstre()
fun genereMonstre(): IndividuMonstre {
    if (especesMonstres.isEmpty()) {
        throw IllegalStateException("Aucune espèce disponible dans la zone $nom")
    }

    val especeChoisie = especesMonstres.random()
    val variation = Random.nextDouble(0.8, 1.2)
    val expInit = expZone * variation

    return IndividuMonstre(
        id = Random.nextInt(1000, 9999),
        nom = especeChoisie.nom,
        expInit = expInit,
        espece = especeChoisie,
        entraineur = null
    )
}


Vérification que la liste especesMonstres n’est pas vide, sinon exception explicite.

Sélection aléatoire d’une espèce via .random().

Variation de l’expérience initiale dans une plage ±20 % (0.8 à 1.2).

Génération d’un identifiant aléatoire dans un intervalle défini.

Méthode rencontreMonstre()
fun rencontreMonstre(joueur: Entraineur) {
    val monstreSauvage = genereMonstre()
    val premierMonstre = joueur.equipeMonstre.firstOrNull { it.pv > 0 }

    if (premierMonstre == null) {
        println("Tous vos monstres sont K.O. Vous ne pouvez pas combattre.")
        return
    }

    val combat = CombatMonstre(joueur, premierMonstre, monstreSauvage)
    combat.lanceCombat()
}


Utilisation de firstOrNull { it.pv > 0 } pour récupérer le premier monstre vivant ou null.

Si aucun monstre vivant, message à l’utilisateur et retour.

Sinon, instanciation d’un combat et lancement.

4. Concepts de POO mis en œuvre
4.1 Encapsulation

Exemple : Setter de exp dans IndividuMonstre qui gère la validation de l’entrée et déclenche automatiquement un passage de niveau.

Exemple : Setter de pv qui assure que la valeur reste entre 0 et pvMax, via un when.

4.2 Héritage

Illustration : MonsterKube hérite de Item, et implémente l’interface Utilisable.

class MonsterKube(...) : Item(...), Utilisable

4.3 Polymorphisme

Utilisation de is Utilisable pour vérifier au runtime le type de l’objet et permettre un « smart cast ».

if (itemChoisi is Utilisable) {
    itemChoisi.utiliser(monstreSauvage)
}

4.4 Composition

Exemple : La classe Entraineur possède des listes de IndividuMonstre et de Item, illustrant la relation « has-a ».

var equipeMonstre: MutableList<IndividuMonstre>
var sacAItems: MutableList<Item>

5. Systèmes de jeu
5.1 Système de progression

Fonction de palier d’expérience :

fun palierExp(niveau: Int): Double {
    return 100 * (niveau - 1).toDouble().pow(2.0)
}


Exemple de progression :

Niveau	Exp requise
1	0
2	100
3	400
4	900
5	1600

Calcul des statistiques au niveau supérieur via la fonction locale calcStat().

5.2 Système de combat

Exemple de formule de dégâts :

val degatsBase = if (estSpecial) attaquant.attaqueSpe else attaquant.attaque
val multiplicateurStab = calculBonusStab(attaquant)
var multiElement = elementTechnique.efficaciteContre(defenseur.espece.elements[0])
if (defenseur.espece.elements.size > 1) {
    multiElement *= elementTechnique.efficaciteContre(defenseur.espece.elements[1])
}
return (degatsBase * multiplicateurStab) * multiElement


Les bonus/malus sont appliqués selon la correspondance élémentaire, et la vitesse détermine l’ordre d’attaque.

5.3 Système de capture

Formule :

val ratioVie = cible.pv.toDouble() / cible.pvMax.toDouble()
var chanceEffective = chanceCapture * (1.5 - ratioVie)
chanceEffective = chanceEffective.coerceAtLeast(5.0)
val nbAleatoire = (0..100).random()


Plus le monstre est affaibli, meilleure est la chance de capture, avec un minimum garanti de 5 %.

5.4 Système d’éléments

Exemple de relations définies:

feu.forces = listOf(plante, insecte)

plante.faiblesses = listOf(feu, insecte)

Une efficacité calculée via :

fun efficaciteContre(elementCible: Element): Double {
    return when {
        forces.contains(elementCible) -> 2.0
        faiblesses.contains(elementCible) -> 0.5
        immunises.contains(elementCible) -> 0.0
        else -> 1.0
    }
}

6. Exemples de code significatifs
6.1 Fonction de coloration de texte
fun changeCouleur(texte: String, couleur: String): String {
    val reset = "\u001B[0m"
    val codeCouleur = when (couleur.lowercase()) {
        "rouge"   -> "\u001B[31m"
        "vert"    -> "\u001B[32m"
        "jaune"   -> "\u001B[33m"
        "bleu"    -> "\u001B[34m"
        "magenta" -> "\u001B[35m"
        "cyan"    -> "\u001B[36m"
        "blanc"   -> "\u001B[37m"
        "marron"  -> "\u001B[38;5;94m"
        else      -> ""
    }
    return if (codeCouleur == "") texte else "$codeCouleur$texte$reset"
}


Bonne utilisation de lowercase(), des codes ANSI et des templates.

6.2 Builder via paramètres nommés
val especeSpringleaf = EspeceMonstre(
    id = 1,
    nom = "Springleaf",
    type = "Graine",
    baseAttaque = 60,
    baseDefense = 9,
    baseVitesse = 11,
    baseAttaqueSpe = 10,
    baseDefenseSpe = 12,
    basePv = 14,
    modAttaque = 34.0,
    modDefense = 6.5,
    modVitesse = 9.0,
    modAttaqueSpe = 8.0,
    modDefenseSpe = 7.0,
    modPv = 10.0,
    description = "Petit monstre espiègle rond comme une graine, adore le soleil.",
    particularites = "Sa feuille sur la tête indique son humeur.",
    caractères = "Curieux, amical, timide"
)


Les paramètres nommés offrent de la clarté pour l’appel du constructeur.

6.3 Navigation entre zones
val route1 = Zone(id = 1, nom = "Route 1", expZone = 20, …)
val route2 = Zone(id = 2, nom = "Route 2", expZone = 25, …)
route1.zoneSuivante = route2
route2.zonePrecedente = route1


Liaison bidirectionnelle entre zones.

Exemple de navigation utilisateur :

"3" -> if (zone.zoneSuivante != null) {
    zone = zone.zoneSuivante!!
    println("Vous avancez vers ${zone.nom}")
}

6.4 Initialisation de la partie
fun nouvellePartie(): Partie {
    println("Bienvenue dans le monde des monstres !")
    println("Quel est ton nom ?")
    val nomChoisi = readln()
    joueur.nom = nomChoisi

    println("Enchanté $nomChoisi ! Ton aventure commence maintenant...")

    return Partie(4, rival, route1)
}


Flux clair d’initialisation : demande du nom, choix du starter, lancement du gameplay.

7. Tests et validation
7.1 Tests unitaires dans main

Test de chargement d’art ASCII via afficheArt().

Création d’un IndividuMonstre, affichage des détails initiaux.

Ajout d’expérience pour vérifier le level-up automatique.

Test de la gestion des PV en excès ou en défaut (clamping).

Test d’une attaque simple et vérification des PV de la cible.

Test de l’ajout de monstres et d’items à un Entraineur, et affichage via afficheDetail().

7.2 Test de capture

Exemple :

val kubeBasique = MonsterKube(10, "MonsterKube", "Une sphère pour capturer les monstres", 30.0)
val monstre = IndividuMonstre(id = 4, nom = "Flamkip", expInit = 1500.0, espece = especeFlamkip, entraineur = null)
kubeBasique.utiliser(monstre)


Vérification que la cible est bien libre.

Vérification du calcul probabiliste de capture.

Message utilisateur correct selon succès/échec.

7.3 Test de combat complet

Exemple :

val monstreJoueur     = IndividuMonstre(101, "FLAM", 0.0, flamkip)
val monstreSauvage    = IndividuMonstre(201, "AQUA", 0.0, aquamy)
val joueur            = Entraineur(1, "Sacha", 500)
joueur.equipeMonstre.add(monstreJoueur)

val combat = CombatMonstre(joueur, monstreJoueur, monstreSauvage)
combat.lanceCombat()


Tests de : boucle de combat, ordre d’attaque basé sur vitesse, menu d’action (attaquer, objet, changement), conditions de victoire/défaite, gain d’expérience.

7.4 Test d’intégration – Partie complète
val partie = nouvellePartie()
partie.choixStarter()
partie.jouer()


Test de l’enchaînement complet : saisie du nom, choix du starter, boucle de jeu principale avec navigation, combats et interactions.

8. Conclusion

Ce projet met en lumière une maîtrise solide de la programmation orientée objet en Kotlin. L’architecture est réfléchie, modulaire et prête à être étendue. Le code est clair, bien structuré, et montre une application concrète de concepts comme l’héritage, le polymorphisme, l’encapsulation ou la composition. Les systèmes de jeu sont bien définis (progression, combat, capture, éléments) et fonctionnels. Enfin, les tests montrent une rigueur appréciable dans la validation des fonctionnalités. 

