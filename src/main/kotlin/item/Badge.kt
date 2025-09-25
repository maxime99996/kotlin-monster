<<<<<<< HEAD:src/main/kotlin/item/Badge.kt
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
=======
package item
import org.example.dresseur.Entraineur


/**
 * Représente un badge, obtenu en battant un champion.
 *
 * Hérite de la classe [Item].
 *
 * @property champion L’entraîneur à battre pour obtenir ce badge.
 */
class Badge(
    id: Int,
    nom: String,
    description: String,
    var champion: Entraineur
) : Item(id, nom, description)
>>>>>>> 2dad8ee7e1f36386e813916e4e6395673afa4293:src/main/kotlin/Item/Badge.kt
