package combat

import org.example.dresseur.Entraineur
import org.example.monstres.IndividuMonstre

/**
 * Représente un combat entre un monstre du joueur et un monstre sauvage.
 *
 * @property monstreJoueur Le monstre du joueur.
 * @property monstreSauvage Le monstre sauvage.
 */
class CombatMonstre(
    var joueur: Entraineur,
    var monstreJoueur: IndividuMonstre,
    var monstreSauvage: IndividuMonstre
) {
    var round: Int = 1

    /**
     * Vérifie si le joueur a perdu le combat.
     */
    fun gameOver(): Boolean {
        return joueur.equipeMonstre.all { it.pv <= 0 }
    }

    /**
     * Vérifie si le joueur a gagné :
     * - en capturant le monstre
     * - ou si le monstre sauvage est KO.
     */
    fun joueurGagne(): Boolean {
        if (monstreSauvage.pv <= 0) {
            println("${monstreSauvage.nom} est KO !")
            monstreJoueur.exp += 200.0 // gain d’XP simple
            return true
        }
        return false
    }

    /**
     * Action de l’adversaire (simple : il attaque si encore en vie).
     */
    fun actionAdversaire() {
        if (monstreSauvage.pv > 0) {
            monstreSauvage.attaquer(monstreJoueur)
        }
    }

    /**
     * Action du joueur (attaque, item, switch).
     *
     * @return `true` si le combat continue, `false` sinon.
     */
    fun actionJoueur(): Boolean {
        println("Choisissez une action :")
        println("1. Attaquer")
        println("2. Utiliser un objet")
        println("3. Changer de monstre")

        when (readLine()?.trim()) {
            "1" -> monstreJoueur.attaquer(monstreSauvage)
            "2" -> {
                if (joueur.sacAItems.isEmpty()) {
                    println("Votre sac est vide !")
                } else {
                    println("Quel objet utiliser ?")
                    joueur.sacAItems.forEachIndexed { i, item -> println("${i + 1}. ${item.nom}") }
                    val choix = readLine()?.toIntOrNull()
                    if (choix != null && choix in 1..joueur.sacAItems.size) {
                        val objet = joueur.sacAItems[choix - 1]
                        if (objet is item.Utilisable) {
                            val reussi = objet.utiliser(monstreSauvage)
                            if (reussi) return false // capture réussie → combat fini
                        } else {
                            println("Cet objet ne peut pas être utilisé en combat.")
                        }
                    }
                }
            }
            "3" -> {
                if (joueur.equipeMonstre.size > 1) {
                    println("Choisissez un autre monstre :")
                    joueur.equipeMonstre.forEachIndexed { i, m -> println("${i + 1}. ${m.nom}") }
                    val choix = readLine()?.toIntOrNull()
                    if (choix != null && choix in 1..joueur.equipeMonstre.size) {
                        monstreJoueur = joueur.equipeMonstre[choix - 1]
                        println("Vous envoyez ${monstreJoueur.nom} au combat !")
                    }
                } else {
                    println("Vous n'avez pas d'autre monstre !")
                }
            }
            else -> println("Action invalide.")
        }
        return true
    }

    /**
     * Affiche l’état du combat.
     */
    fun afficheCombat() {
        println("=== Round $round ===")
        println("Joueur → ${monstreJoueur.nom} [${monstreJoueur.pv}/${monstreJoueur.pvMax}]")
        println("Sauvage → ${monstreSauvage.nom} [${monstreSauvage.pv}/${monstreSauvage.pvMax}]")
    }

    /**
     * Un tour de combat.
     */
    fun jouer() {
        afficheCombat()

        // Le plus rapide joue en premier
        val premier = if (monstreJoueur.vitesse >= monstreSauvage.vitesse) monstreJoueur else monstreSauvage
        val second = if (premier == monstreJoueur) monstreSauvage else monstreJoueur

        if (premier == monstreJoueur) {
            if (!actionJoueur()) return
            if (monstreSauvage.pv > 0) actionAdversaire()
        } else {
            actionAdversaire()
            if (monstreJoueur.pv > 0) {
                if (!actionJoueur()) return
            }
        }
    }

    /**
     * Lance la boucle du combat.
     */
    fun lanceCombat() {
        while (!gameOver() && !joueurGagne()) {
            jouer()
            println("======== Fin du Round : $round ========")
            round++
        }
        if (gameOver()) {
            joueur.equipeMonstre.forEach { it.pv = it.pvMax }
            println("Game Over !")
        }
    }
}
