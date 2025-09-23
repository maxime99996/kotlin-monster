package org.example.monstre
import monstre.EspeceMonstre
import org.example.dresseur.Entraineur
import kotlin.math.pow


import kotlin.random.Random

/**
 * Représente un monstre individuel (indépendant de son espèce).
 *
 * Chaque individu a ses propres statistiques (niveau, pv, attaque, défense...),
 * qui peuvent évoluer avec l'expérience et les niveaux.
 *
 * @property id Identifiant unique de l’individu.
 * @property nom Nom du monstre (modifiable).
 * @property espece Espèce du monstre (définit ses bases).
 * @property entraineur Entraîneur auquel appartient le monstre, ou `null` si sauvage.
 * @param expInit Expérience initiale (non stockée directement, mais appliquée au setter de exp).
 */
class IndividuMonstre(
    var id: Int,
    var nom: String,
    var espece: EspeceMonstre,
    var entraineur: Entraineur? = null,
    expInit: Double
) {
    // --- Propriétés dans le corps de la classe ---

    var niveau: Int = 1
    var attaque: Int = espece.baseAttaque + Random.nextInt(-2, 2)
    var defense: Int = espece.baseDefense + Random.nextInt(-2, 2)
    var vitesse: Int = espece.baseVitesse + Random.nextInt(-2, 2)
    var attaqueSpe: Int = espece.baseAttaqueSpe + Random.nextInt(-2, 2)
    var defenseSpe: Int = espece.baseDefenseSpe + Random.nextInt(-2, 2)
    var pvMax: Int = espece.basePv + Random.nextInt(-5, 6)
    var potentiel: Double = Random.nextDouble(0.5, 2.0)

    /**
     * Points de vie actuels.
     * Ne peut pas être inférieur à 0 ni supérieur à [pvMax].
     */
    var pv: Int = pvMax
        get() = field
        set(nouveauPv) {
            field = when {
                nouveauPv < 0 -> 0
                nouveauPv > pvMax -> pvMax
                else -> nouveauPv
            }
        }
    var estNiveau: Boolean = true

    /**
     * Expérience actuelle.
     * Quand elle atteint un palier, le monstre monte de niveau.
     */
    var exp: Double = 0.0
        get() = field
        set(nouvelleExp) {
            field = nouvelleExp
            if (niveau == 1){
                var estNiveau1 = true
            }
            else{
                var estNiveau1= false
            }
            while (field >= palierExp(niveau)) {
                levelUp()
                if (estNiveau == false){
                    println("le monstre $nom est maintenant niveau $niveau! ")
                }
            }

        }

    // --- Constructeur secondaire ---
    init {
        this.exp = expInit // applique le setter → peut déclencher un level-up
    }

    // --- Méthodes ---

    /**
     * Calcule l’expérience nécessaire pour atteindre un niveau donné.
     *
     * @param niveau Niveau cible.
     * @return Expérience totale nécessaire.
     */
    fun palierExp(niveau: Int): Double {
        return 100 * (niveau - 1).toDouble().pow(2.0)
    }

    /**
     * Fait monter le monstre d’un niveau et augmente ses caractéristiques.
     * Les stats gagnées dépendent du potentiel, du modificateur de l’espèce et d’un aléatoire.
     */
    fun levelUp() {
        niveau++

        attaque += (espece.modAttaque * potentiel).toInt() + Random.nextInt(-2, 2)
        defense += (espece.modDefense * potentiel).toInt() + Random.nextInt(-2, 2)
        vitesse += (espece.modVitesse * potentiel).toInt() + Random.nextInt(-2, 2)
        attaqueSpe += (espece.modAttaqueSpe * potentiel).toInt() + Random.nextInt(-2, 2)
        defenseSpe += (espece.modDefenseSpe * potentiel).toInt() + Random.nextInt(-2, 2)

        val ancienPvMax = pvMax
        pvMax += (espece.modPv * potentiel).toInt() + Random.nextInt(-5, 6)

        // On ajoute aux PV actuels le gain de pvMax
        pv += (pvMax - ancienPvMax)

        println("$nom a monté au niveau $niveau !")
    }

    /**
     * Attaque un autre [IndividuMonstre] et inflige des dégâts.
     *
     * Les dégâts sont calculés simplement :
     * `dégâts = attaque - (défense / 2)` (minimum 1 dégât).
     *
     * @param cible Monstre cible de l'attaque.
     */
    fun attaquer(cible: IndividuMonstre) {
        val degats = (this.attaque - (cible.defense / 2)).coerceAtLeast(1)
        cible.pv -= degats
        println("${this.nom} attaque ${cible.nom} et inflige $degats dégâts !")
        println("${cible.nom} a maintenant ${cible.pv}/${cible.pvMax} PV.")
    }
    /**
     * Demande au joueur de renommer le monstre.
     * Si l'utilisateur entre un texte vide, le nom n'est pas modifié.
     */
    fun renommer() {
        println("Entrez un nouveau nom pour ${this.nom} : ")
        val saisie = readLine()
        if (!saisie.isNullOrBlank()) {
            this.nom = saisie
            println("Le monstre a été renommé en $nom.")
        } else {
            println("Le nom n'a pas été modifié.")
        }
        /**
         * Affiche les détails complets du monstre :
         * - caractéristiques
         * - ascii art de l’espèce
         */
        fun afficheDetail() {
            println("=== ${this.nom} (niveau $niveau) ===")
            println("PV : $pv/$pvMax")
            println("Attaque : $attaque")
            println("Défense : $defense")
            println("Vitesse : $vitesse")
            println("Attaque Spéciale : $attaqueSpe")
            println("Défense Spéciale : $defenseSpe")
            println("Potentiel : $potentiel")
            println("Espèce : ${espece.nom} (${espece.type})")
            println("Description : ${espece.description}")
            println()
            println(espece.afficheArt(true)) // affiche l’ASCII de face
        }

    }


}
