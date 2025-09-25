package item

/**
 * Représente un objet de base.
 *
 * @property id Identifiant unique de l’objet.
 * @property nom Nom de l’objet.
 * @property description Description de l’objet.
 */
open class Item(
    var id: Int,
    var nom: String,
    var description: String
)
