package org.example

import item.MonsterKube
import monde.Zone
import monstres.EspeceMonstre
import org.example.dresseur.Entraineur
import org.example.monstres.IndividuMonstre

// Instanciation globale des objets
var joueur = Entraineur(
    1, "Sacha",
    argents = TODO(),
    equipeMonstre = TODO(),
    boiteMonstre = TODO(),
    sacAItems = TODO()
)
var rival = Entraineur(
    2, "Regis",
    argents = TODO(),
    equipeMonstre = TODO(),
    boiteMonstre = TODO(),
    sacAItems = TODO()
)

var especeSpringLeaf = EspeceMonstre(
    1, "SpringLeaf", "Plante",
    45, 40, 60, 50, 50, 55,
    1.1, 1.0, 1.2, 1.1, 1.0, 1.2,
    description = "Petit monstre plante agile",
    particularites = "Rapide mais fragile",
    caracteres = "Joyeux et curieux"
)

var especeFlamkip = EspeceMonstre(
    2, "Flamkip", "Feu",
    60, 45, 55, 65, 50, 50,
    1.2, 1.0, 1.1, 1.3, 1.0, 1.1,
    description = "Lézard de feu courageux",
    particularites = "Attaque puissante",
    caracteres = "Têtu et brave"
)

var especeAquamy = EspeceMonstre(
    3, "Aquamy", "Eau",
    50, 60, 55, 50, 65, 60,
    1.0, 1.2, 1.1, 1.0, 1.3, 1.1,
    description = "Créature aquatique calme",
    particularites = "Très bonne défense",
    caracteres = "Calme et loyal"
)

fun main() {
    // Création de 3 individus
    val monstre1 = IndividuMonstre(1, "Springleaf", especeSpringLeaf, null, 1500.0)
    val monstre2 = IndividuMonstre(2, "Flamkip", especeFlamkip, null, 1500.0)
    val monstre3 = IndividuMonstre(3, "Aquamy", especeAquamy, null, 1500.0)

    // Test ascii art
    println(especeSpringLeaf.afficheArt(true))   // front
    println(especeSpringLeaf.afficheArt(false))  // back
    println(especeFlamkip.afficheArt(true))
    println(especeFlamkip.afficheArt(false))
    println(especeAquamy.afficheArt(true))
    println(especeAquamy.afficheArt(false))

    // Création de zones
    val zone1 = Zone(
        id = 1,
        nom = "Route 1",
        expZone = 50,
        especesMonstres = mutableListOf(especeSpringLeaf, especeFlamkip)
    )

    val zone2 = Zone(
        id = 2,
        nom = "Forêt Mystique",
        expZone = 100,
        especesMonstres = mutableListOf(especeAquamy)
    )

    // Relier les zones
    zone1.zoneSuivante = zone2
    zone2.zonePrecedente = zone1

    // Joueur avec équipe et items
    val joueur = Entraineur(
        1, "Maxou",
        argents = TODO(),
        equipeMonstre = TODO(),
        boiteMonstre = TODO(),
        sacAItems = TODO()
    )
    joueur.equipeMonstre.add(monstre1)

    val kube1 = MonsterKube(1, "MonsterKube", "Un cube qui capture les monstres", 30.0)
    joueur.sacAItems.add(kube1)

    // Vérification des monstres
    println("=== Vérification initiale ===")
    println("${monstre1.nom} → niveau ${monstre1.niveau}, attaque ${monstre1.attaque}, pv ${monstre1.pv}/${monstre1.pvMax}")
    println("${monstre2.nom} → niveau ${monstre2.niveau}, attaque ${monstre2.attaque}, pv ${monstre2.pv}/${monstre2.pvMax}")
    println("${monstre3.nom} → niveau ${monstre3.niveau}, attaque ${monstre3.attaque}, pv ${monstre3.pv}/${monstre3.pvMax}")

    // Test XP
    println("\n=== Gain d'expérience ===")
    monstre1.exp += 1000.0
    println("${monstre1.nom} est maintenant niveau ${monstre1.niveau}")
}

fun changeCouleur(message: String, couleur: String = ""): String {
    val reset = "\u001B[0m"
    val codeCouleur = when (couleur.lowercase()) {
        "rouge" -> "\u001B[31m"
        "vert" -> "\u001B[32m"
        "jaune" -> "\u001B[33m"
        "bleu" -> "\u001B[34m"
        "magenta" -> "\u001B[35m"
        "cyan" -> "\u001B[36m"
        "blanc" -> "\u001B[37m"
        else -> "" // pas de couleur si non reconnu
    }
    return "$codeCouleur$message$reset"
}




