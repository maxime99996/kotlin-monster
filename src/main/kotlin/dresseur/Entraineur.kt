<<<<<<< HEAD
package org.example.dresseur

import item.Item
import org.example.monstre.individuMonstre

class Entraineur(
    var id: Int,
    var nom: String,
    var argents:Int,
    //TODO equipeMonstre
    //TODO boiteMonstre
    //TODO sacAKube
    var equipeMonstre: MutableList<individuMonstre> = mutableListOf(),
    var boiteMonstre: MutableList<individuMonstre> = mutableListOf(),
    var sacAItems: MutableList<Item> = mutableListOf(),

    ) {

    /**
     * Affiche les détails de l'entraîneur, y compris son nom et la quantité d'argent en sa possession.
     *
     * Cette méthode affiche les informations de l'entraîneur sous la forme de deux lignes :
     * 1. Le nom de l'entraîneur.
     * 2. La somme d'argent qu'il possède.
     */
    fun afficheDetail(){
        println("Dresseur : ${this.nom}")
        println("Argents: ${this.argents} ")
    }
}

=======
package org.example.dresseur

import item.Item
import org.example.monstre.IndividuMonstre

class Entraineur(
    var id: Int,
    var nom: String,
    var argents:Int,
    //TODO equipeMonstre
    //TODO boiteMonstre
    //TODO sacAKube
    var equipeMonstre: MutableList<IndividuMonstre> = mutableListOf(),
    var boiteMonstre: MutableList<IndividuMonstre> = mutableListOf(),
    var sacAItems: MutableList<Item> = mutableListOf(),

    ) {

    /**
     * Affiche les détails de l'entraîneur, y compris son nom et la quantité d'argent en sa possession.
     *
     * Cette méthode affiche les informations de l'entraîneur sous la forme de deux lignes :
     * 1. Le nom de l'entraîneur.
     * 2. La somme d'argent qu'il possède.
     */
    fun afficheDetail(){
        println("Dresseur : ${this.nom}")
        println("Argents: ${this.argents} ")
    }
}

>>>>>>> 2dad8ee7e1f36386e813916e4e6395673afa4293
