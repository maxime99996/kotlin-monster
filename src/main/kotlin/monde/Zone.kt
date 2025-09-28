package monde
import jeu.CombatMonstre
import org.example.joueur
import monstre.EspeceMonstre
import org.example.monstre.individuMonstre
import org.example.dresseur.Entraineur
import kotlin.random.Random

/**
 * Représente une zone du monde (route, caverne, mer, ville…).
 *
 * Une zone contient une liste d'espèces de monstres qu’on peut rencontrer,
 * et permet de se déplacer vers une zone précédente ou suivante.
 *
 * @property id Identifiant unique de la zone.
 * @property nom Nom de la zone.
 * @property expZone Expérience gagnée en moyenne dans cette zone.
 * @property especesMonstres Liste des espèces de monstres présents dans cette zone.
 * @property zoneSuivante Zone suivante accessible depuis cette zone (ou null si aucune).
 * @property zonePrecedente Zone précédente accessible depuis cette zone (ou null si aucune).
 */
class Zone(
    var id: Int,
    var nom: String,
    var expZone: Int,
    var especesMonstres: MutableList<EspeceMonstre> = mutableListOf(),
    var zoneSuivante: Zone? = null,
    var zonePrecedente: Zone? = null
) {
    var listeMonstres : MustableList<individuMonstre> = mutableListOf()
    fun genereMonstre(): individuMonstre{
        var idgenereMonstre = 0
        var valeurMonstre : Int
        var unique : Boolean
        do {
            valeurMonstre = Random.nextInt(10,101)
            unique= true
            for (monstre in listeMonstres) {
                if (monstre.id == valeurMonstre) {
                    // si l'on trouve la m^me valeur cela n'est pas unique
                    unique = false
                    break
                }
            }
            if (unique) {
                idgenereMonstre = valeurMonstre
            }
        }
    } while (!unique)
    fun rencontreMonstre() {
        val monstreSauvage = genereMonstre()

        // Pour but de charcher le premier monstre que posséde le joueur qui a encore de la vie
        val monstreDujoeur = joueur.equipeMoonstre.firstOrNull {it.pv > 0 }
        if (monstreDujoeur != null) {
            val combat = CombatMonstre(monstreDujoeur, monstreSauvage)
            combat.lancerCombat()
        }
    }