package org.example
import monde.Zone
import monstre.EspeceMonstre
import org.example.dresseur.Entraineur
import org.example.item.MonsterKube
import org.example.monstre.individuMonstre

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
    id = 1, nom = "SpringLeaf", type = "Graine",
    baseAttaque = 9, baseDefense = 11, baseVitesse = 10, baseAttaqueSpe = 14, baseDefenseSpe = 50, basePv = 60,
    modAttaque = 6.5, modPv = 34  , modDefense = 9.0, modVitesse =  8.0, modAttaqueSpe =  7.0, modDefenseSpe = 10.0,
    description = "Petit monstre espiègle rond comme une graine, adore le soleil.",
    particularites = "Sa feuille sur la tête indique son humeur.",
    caracteres = "Curieux; amical, timide."
)

val especeFlamkip = EspeceMonstre(
    id = 4, nom = "Flamkip", type = "Animal",
    baseAttaque = 9, baseDefense = 8, baseVitesse = 13, baseAttaqueSpe = 16, baseDefenseSpe = 7, basePv = 50,
    modAttaque = 10.0, modPv = 22  , modDefense = 5.5, modVitesse =  9.5, modAttaqueSpe =  9.5, modDefenseSpe = 6.5,
    description = "Petit animal entouré de flammes, déteste le froid.",
    particularites = "Sa flamme change d'intensité selon son énergie.",
    caracteres = "impulsif, joueur, loyal."
)

val especeAquamy = EspeceMonstre(
    id = 7, nom = "Aquamy", type = "Meteo",
    baseAttaque = 10, baseDefense = 11, baseVitesse = 9, baseAttaqueSpe = 14, baseDefenseSpe = 14, basePv = 55,
    modAttaque = 9.0, modPv = 27  , modDefense = 10.0, modVitesse =  7.5, modAttaqueSpe =  1.0, modDefenseSpe = 12.0,
    description = "¨Créature vaporeuse semblable à un nuage, produit des gouttes pures. ",
    particularites = "Fait baisser la température en s'endormant.",
    caracteres = "calme, rêveur, mystérieux"
)

val especeLaoumi = EspeceMonstre(
    id = 8, nom = "Laoumi", type = "Animal",
    baseAttaque = 11, baseDefense = 10, baseVitesse = 9, baseAttaqueSpe = 8, baseDefenseSpe = 11, basePv = 58,
    modAttaque = 11.0, modPv = 23  , modDefense = 8.0, modVitesse =  7.0, modAttaqueSpe =  6.0, modDefenseSpe = 11.5,
    description = "¨Petit ourson au pelage soyeux, aime se tenir debout.",
    particularites = "Son grognement est mignon mais il protège ses amis.",
    caracteres = "Affectueux, protecteur, gourmand"
)

val especeBugsyface = EspeceMonstre(
    id = 10, nom = "Bugsyface", type = "insect",
    baseAttaque = 10, baseDefense = 13, baseVitesse = 8, baseAttaqueSpe = 7, baseDefenseSpe = 13, basePv = 45,
    modAttaque = 7.0, modPv = 21  , modDefense = 11.0, modVitesse =  6.5, modAttaqueSpe =  8.0, modDefenseSpe = 11.5,
    description = "¨Insecte à carapace luisante, se déplace par bonds et vibre des antennes.",
    particularites = "Sa carapace devient plus dure après chaque mue.",
    caracteres = "Travailleur, sociable, infatigable"
)

val especeGalum = EspeceMonstre(
    id = 13, nom = "Galum", type = "Minéral",
    baseAttaque = 12, baseDefense = 15, baseVitesse = 6, baseAttaqueSpe = 8, baseDefenseSpe = 12, basePv = 55,
    modAttaque = 9.0, modPv = 13  , modDefense = 13.0, modVitesse =  4.0, modAttaqueSpe =  6.5, modDefenseSpe = 10.5,
    description = "¨Golem ancien de pierre, yeux lumineux en garde.",
    particularites = "Peut rester immobile des heures comme une statue.",
    caracteres = "Sérieux, stoïque, fiable"
)

// Création de zones
val route1 = Zone(
    id = 1,
    nom = "Route 1",
    expZone = 50,
    especesMonstres = mutableListOf(especeSpringLeaf, especeFlamkip)
)

val route2 = Zone(
    id = 2,
    nom = "Route 2",
    expZone = 100,
    especesMonstres = mutableListOf(especeAquamy, especeBugsyface)
)

var joueur = Entraineur(1,"Sacha",100)


fun main() {
    // Création de 6 individus
    val monstre1 = individuMonstre(1, "Springleaf", especeSpringLeaf, null, 1500.0)
    val monstre2 = individuMonstre(4, "Flamkip", especeFlamkip, null, 1500.0)
    val monstre3 = individuMonstre(7, "Aquamy", especeAquamy, null, 1500.0)
    val monstre4 = individuMonstre(8, "Laoumi", especeLaoumi, null, 1500.0)
    val monstre5 = individuMonstre(10, "Bugsyface", especeBugsyface, null, 1500.0)
    val monstre6 = individuMonstre(13, "Galum", especeGalum, null, 1500.0)



    // Relier les zones
    route1.zoneSuivante = route2
    route2.zonePrecedente = route1

    // Items
    val kube1 = MonsterKube(1, "MonsterKube", "Un cube qui capture les monstres", 30.0)


}


