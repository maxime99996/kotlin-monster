package item

import org.example.monstres.IndividuMonstre

/**
 * Interface définissant un objet utilisable en combat.
 *
 * Toute classe qui implémente Utilisable doit fournir une
 * implémentation de [utiliser].
 */
interface Utilisable {
    /**
     * Applique l’effet de l’objet sur un [IndividuMonstre].
     *
     * @param cible Le monstre sur lequel l’objet est utilisé.
     * @return `true` si l’action a eu un effet, sinon `false`.
     */
    fun utiliser(cible: IndividuMonstre): Boolean
}
