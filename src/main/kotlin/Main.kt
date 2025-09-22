package org.example

import item.MonsterKube
import monde.Zone
import monstres.EspeceMonstre
import org.example.dresseur.Entraineur
import org.example.monstres.IndividuMonstre

// Fonction utilitaire pour changer la couleur d'un message (si supporté par le terminal)
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

// Définir les espèces de monstres
val especeSpringLeaf = EspeceMonstre(
    1, "SpringLeaf", "Graine",
    9, 11, 10, 14, 50, 60,
    6.5, 9.0, 8.0, 7.0, 10.0,
    description = "Petit monstre espiègle rond comme une graine, adore le soleil.",
    particularites = "Sa feuille sur la tête indique son humeur.",
    caracteres = "Curieux; amical, timide."
)

val especeFlamkip = EspeceMonstre(
    4, "Flamkip", "Animal",
    9, 8,  13, 16, 7,
    50, 10.0, 5.5, 9.5, 9.5, 6.5,
    description = "Petit animal entouré de flammes, déteste le froid.",
    particularites = "Sa flamme change d'intensité selon son énergie.",
    caracteres = "impulsif, joueur, loyal."
)

val especeAquamy = EspeceMonstre(
    7, "Aquamy", "Meteo",
    10, 11, 9, 14, 14, 55,
    9.0, 10.0, 7.5, 1.0, 12.0,
    description = "¨Créature vaporeuse semblable à un nuage, produit des gouttes pures. ",
    particularites = "Fait baisser la température en s'endormant.",
    caracteres = "calme, rêveur, mystérieux"
)

val especeLaoumi = EspeceMonstre(
    8, "Laoumi", "Animal",
    11, 10, 9, 8, 11, 58,
    11.0, 8.0, 7.0, 6.0, 11.5,
    description = "¨Petit ourson au pelage soyeux, aime se tenir debout.",
    particularites = "Son grognement est mignon mais il protège ses amis.",
    caracteres = "Affectueux, protecteur, gourmand"
)

val especeBugsyface = EspeceMonstre(
    10, "bugsyface", "insect",
    10, 13, 8, 7, 13, 45,
    7.0, 11.0, 6.5, 8.0, 11.5,
    description = "¨Insecte à carapace luisante, se déplace par bonds et vibre des antennes.",
    particularites = "Sa carapace devient plus dure après chaque mue.",
    caracteres = "Travailleur, sociable, infatigable"
)

val especeGalum = EspeceMonstre(
    13, "Galum", "Minéral",
    12, 15, 6, 8, 12, 55,
    9.0, 13.0, 4.0, 6.5, 10.5,
    description = "¨Golem ancien de pierre, yeux lumineux en garde.",
    particularites = "Peut rester immobile des heures comme une statue.",
    caracteres = "Sérieux, stoïque, fiable"
)


fun main() {


    // Création de 6 individus
    val monstre1 = IndividuMonstre(1, "Springleaf", especeSpringLeaf, null, 1500.0)
    val monstre2 = IndividuMonstre(4, "Flamkip", especeFlamkip, null, 1500.0)
    val monstre3 = IndividuMonstre(7, "Aquamy", especeAquamy, null, 1500.0)
    val monstre4 = IndividuMonstre(8, "Laoumi", especeLaoumi, null, 1500.0)
    val monstre5 = IndividuMonstre(10, "Bugsyface", especeBugsyface, null, 1500.0)
    val monstre6 = IndividuMonstre(13, "Galum", especeGalum, null, 1500.0)


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

    // Items
    val kube1 = MonsterKube(1, "MonsterKube", "Un cube qui capture les monstres", 30.0)

    // Joueur avec équipe et items
    val joueur = Entraineur(
        id = 1,
        nom = "Sacha",
        argents = 1000,
        equipeMonstre = mutableListOf(monstre1),
        boiteMonstre = mutableListOf(monstre2, monstre3),
        sacAItems = mutableListOf(kube1)
    )


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







