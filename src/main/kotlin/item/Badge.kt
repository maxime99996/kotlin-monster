package item
import org.example.dresseur.Entraineur


/**
 * Représente un badge, obtenu en battant un champion.
 *
 * Hérite de la classe [item].
 *
 * @property champion L’entraîneur à battre pour obtenir ce badge.
 */
class Badge(
    id: Int,
    nom: String,
    description: String,
    var champion: Entraineur
) : Item(id, nom, description)
