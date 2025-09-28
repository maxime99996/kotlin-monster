package org.example.Jeu

import org.example.dresseur.Entraineur
import org.example.especeAquamy
import org.example.especeFlamkip
import org.example.especeSpringLeaf
import monstre.EspeceMonstre
import monde.Zone
import org.example.monstre.individuMonstre

class Partie(
    val id: Int,
    var joueur: Entraineur,
    var zone: Zone
) {

    fun choixStarter() {
        val starter1 = individuMonstre(1, "springleaf", especeSpringLeaf, null, 1500.0)
        val starter2 = individuMonstre(4, "aquamy", especeAquamy, null, 1500.0)
        val starter3 = individuMonstre(5, "flamkip", especeFlamkip, null, 1500.0)

        starter1.afficheDetail()
        starter2.afficheDetail()
        starter3.afficheDetail()

        println("Choisissez un monstre entre les 3 (1 -> springleaf, 2 -> aquamy, ou 3 -> flamkip) :")
        val choixStarter = readln()

        val starter = when (choixStarter) {
            "1" -> starter1
            "2" -> starter2
            "3" -> starter3
            else -> {
                println("Erreur lors de la saisie du choix.")
                return
            }
        }

        starter.renommer()
        starter.entraineur = joueur
        joueur.equipeMonstre.add(starter)
    }

    fun modifierOrdreEquipe() {
        if (joueur.equipeMonstre.size < 2) {
            println("Erreur : il n'y a pas assez de monstres dans l'équipe.")
            return
        }

        println("Équipe :")
        joueur.equipeMonstre.forEachIndexed { index, monstre ->
            println("$index : ${monstre.nom}")
        }

        val position1 = demanderPosition("Saisir la position du premier monstre :")
        val position2 = demanderPosition("Saisir la position du second monstre :")

        joueur.equipeMonstre.swap(position1, position2)
        println("Ordre modifié !")
    }

    fun examineEquipe() {
        if (joueur.equipeMonstre.isEmpty()) {
            println("Votre équipe est vide.")
            return
        }

        println("Équipe :")
        joueur.equipeMonstre.forEachIndexed { index, monstre ->
            println("$index : ${monstre.nom}")
        }

        println("Saisir la position du monstre pour voir les détails, 'q' pour quitter, 'm' pour modifier l'ordre")
        val input = readln().lowercase()

        when (input) {
            "q" -> return
            "m" -> modifierOrdreEquipe()
            else -> {
                val pos = input.toIntOrNull()
                if (pos != null && pos in joueur.equipeMonstre.indices) {
                    joueur.equipeMonstre[pos].afficheDetail()
                } else {
                    println("Entrée invalide.")
                }
            }
        }
    }

    fun jouer() {
        println("Vous êtes dans la zone ${zone.nom}")

        while (true) {
            println("\nActions possibles :")
            println("1 => Rencontrer un monstre sauvage")
            println("2 => Examiner l'équipe de monstres")
            println("3 => Aller à la zone suivante")
            println("4 => Aller à la zone précédente")
            println("q => Quitter le jeu")

            when (readln().lowercase()) {
                "1" -> zone.rencontreMonstre()
                "2" -> examineEquipe()
                "3" -> {
                    zone.zoneSuivante?.let {
                        zone = zone
                    } ?: println("Pas de zone suivante.")
                }
                "4" -> {
                    zone.zonePrecedente?.let {
                        zone = zone
                    } ?: println("Pas de zone précédente.")
                }
                "q" -> {
                    println("À bientôt !")
                    return
                }
                else -> println("Choix invalide.")
            }
        }
    }

    // Fonction d'aide pour demander une position sécurisée
    private fun demanderPosition(message: String): Int {
        while (true) {
            println(message)
            val input = readln().toIntOrNull()
            if (input != null && input in joueur.equipeMonstre.indices) {
                return input
            }
            println("Position invalide.")
        }
    }

    // Extension pour swap les éléments d'une liste
    private fun <T> MutableList<T>.swap(i: Int, j: Int) {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
    }
}


