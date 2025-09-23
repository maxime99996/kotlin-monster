package item
import org.example.monstre.IndividuMonstre
import kotlin.random.Random

/**
 * Représente un MonsterKube (équivalent d'une Pokéball).
 *
 * Hérite de [Item] et implémente l’interface [Utilisable].
 *
 * @property chanceCapture Chance de base de capturer un monstre sauvage.
 */
class MonsterKube(
    id: Int,
    nom: String,
    description: String,
    var chanceCapture: Double
) : Item(id, nom, description), Utilisable {

    /**
     * Tente de capturer un [IndividuMonstre].
     *
     * Version complexe : la chance dépend des PV restants du monstre.
     *
     * @param cible Le monstre sauvage.
     * @return `true` si la capture réussit, sinon `false`.
     */
    override fun utiliser(cible: IndividuMonstre): Boolean {
        val ratioVie = cible.pv.toDouble() / cible.pvMax.toDouble() // entre 0.0 et 1.0
        val chanceEffective = (chanceCapture * (1.5 - ratioVie)).coerceAtLeast(5.0)
        val tirage = Random.nextDouble(0.0, 100.0)

        println("Tentative de capture sur ${cible.nom}... Chance effective = ${"%.2f".format(chanceEffective)}%")
        return if (tirage < chanceEffective) {
            println("${cible.nom} a été capturé ! 🎉")
            true
        } else {
            println("${cible.nom} s'est libéré... 😢")
            false
        }
    }
}
