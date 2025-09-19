package org.example.dresseur

class Entraineur(
    var id: Int,
    var nom: String,
    var argents:Int,
    //TODO equipeMonstre
    //TODO boiteMonstre
    //TODO sacAKube
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
