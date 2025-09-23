package monstre

import java.io.File

/**
 * Représente une espèce de monstre dans le jeu.
 *
 * Une espèce définit les caractéristiques de base de tous les individus
 * qui en font partie (par exemple leurs statistiques, type, description).
 * Chaque individu d'une espèce partagera ces propriétés de base,
 * mais pourra avoir des variations (niveau, PV, etc.).
 *
 * @property id Identifiant unique de l'espèce.
 * @property nom Nom de l'espèce de monstre.
 * @property type Type principal du monstre (ex: "Feu", "Eau", "Plante").
 * @property baseAttaque Valeur de base de l'attaque.
 * @property baseDefense Valeur de base de la défense.
 * @property baseVitesse Valeur de base de la vitesse.
 * @property baseAttaqueSpe Valeur de base de l'attaque spéciale.
 * @property baseDefenseSpe Valeur de base de la défense spéciale.
 * @property basePv Points de vie de base.
 * @property modAttaque Modificateur de croissance de l’attaque (appliqué lors des level-up).
 * @property modDefense Modificateur de croissance de la défense.
 * @property modVitesse Modificateur de croissance de la vitesse.
 * @property modAttaqueSpe Modificateur de croissance de l’attaque spéciale.
 * @property modDefenseSpe Modificateur de croissance de la défense spéciale.
 * @property modPv Modificateur de croissance des points de vie.
 * @property description Brève description de l’espèce.
 * @property particularites Particularités de l’espèce (forces/faiblesses spéciales).
 * @property caracteres Traits de caractère typiques de l’espèce.
 */
class EspeceMonstre(
    /*id de l'Espèce */var id: Int,
    /*nom de l' */var nom: String,
    /* */var type: String,
    /* */ val baseAttaque: Int,
    /* */val baseDefense: Int,
    /* */val baseVitesse: Int,
    /* */val baseAttaqueSpe: Int,
    /* */val baseDefenseSpe: Int,
    /* */val basePv: Int,
    /* */val modAttaque: Double,
    /* */val modDefense: Double,
    /* */val modVitesse: Double,
    /* */val modAttaqueSpe: Double,
    /* */val modPv: Int,
    /* */val modDefenseSpe: Double,
    /* */val description: String = "",
    /* */val particularites: String = "",
    /* */val caracteres: String = ""
) {
    /**
     * Affiche la représentation artistique ASCII du monstre.
     *
     * L’art ASCII est chargé depuis un fichier texte dans `resources/art/{nom}/`.
     * Il existe deux variantes : de face (`front.txt`) ou de dos (`back.txt`).
     *
     * @param deFace Si `true`, affiche l'art de face, sinon de dos.
     * @return Une chaîne de caractères contenant l'art ASCII avec les codes couleur ANSI.
     */
    fun afficheArt(deFace: Boolean = true): String {
        val nomFichier = if (deFace) "front" else "back"
        val art = File("src/main/resources/art/${this.nom.lowercase()}/$nomFichier.txt").readText()
        val safeArt = art.replace("/", "⁄") // évite certains problèmes d'affichage
        return safeArt.replace("\\u001B", "\u001B")
    }
}


