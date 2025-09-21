package monde

import monstres.EspeceMonstre

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
    // TODO: Implémenter plus tard la méthode genereMonstre()
    // TODO: Implémenter plus tard la méthode rencontreMonstre()
}
