import item.Item
import item.Utilisable
package org.example.item

import org.example.monstre.IndividuMonstre
import org.example.dresseur.Entraineur

/**
 * Représente un MonsterKube, une sorte de Pokéball permettant de capturer des monstres.
 *
 * @param chanceCapture La probabilité de capturer un monstre.
 */
class MonsterKube(
    id: Int,
    nom: String,
    description: String,
    var chanceCapture: Double
) : Item(id, nom, description), Utilisable {

    /**
     * Tente de capturer un monstre en utilisant ce MonsterKube.
     *
     * Si le monstre n'a pas encore d'entraîneur, la capture peut réussir ou échouer selon la chance.
     * Si la capture réussit, le monstre est ajouté à l'équipe ou à la boîte du dresseur.
     *
     * @param cible Le monstre sauvage à capturer.
     * @param entraineur L'entraîneur qui tente la capture.
     * @return `true` si la capture est réussie, `false` sinon.
     */
    override fun utiliser(cible: IndividuMonstre, entraineur: Entraineur): Boolean {
        println("Vous lancez le Monstre Kube !")

        // Vérifier si le monstre a déjà un entraîneur
        if (cible.entraineur != null) {
            println("Le monstre ${cible.nom} a déjà un entraîneur, il ne peut pas être capturé.")
            return false
        }

        // Calculer un nombre aléatoire pour déterminer si la capture réussit
        val nbAleatoire = (0 until 100).random()
        if (chanceCapture < nbAleatoire) {
            // Si la capture échoue
            println("Presque ! Le Kube n'a pas pu capturer le monstre ${cible.nom} !")
            return false
        }

        // Si la capture réussit
        println("Le monstre ${cible.nom} a été capturé !")

        // Demander un nouveau nom pour le monstre
        print("Entrez un nouveau nom : ")
        val nouveauNom = readln().takeIf { it.isNotEmpty() } ?: cible.nom
        cible.nom = nouveauNom

        // Ajouter le monstre à l'équipe ou à la boîte de l'entraîneur
        if (entraineur.equipeMonstre.size < 6) {
            entraineur.equipeMonstre.add(cible)
            println("Le monstre ${cible.nom} a été ajouté à votre équipe.")
        } else {
            entraineur.boiteMonstre.add(cible)
            println("Votre équipe est pleine, le monstre ${cible.nom} a été ajouté à votre boîte.")
        }

        // Affecter l'entraîneur au monstre capturé
        cible.entraineur = entraineur

        return true
    }
}
