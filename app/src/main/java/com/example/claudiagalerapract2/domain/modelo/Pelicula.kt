package com.example.claudiagalerapract2.domain.modelo

class Pelicula(
    val id : Int = 0,
    val titulo: String = "",
    val anyoEstreno: Int? = null,
    val director: String? = null,
    val genero: String? = null,
    val recomendado: Boolean = false,
    val calificacion: Double=0.0

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true  // Compara la referencia del objeto
        if (other == null || javaClass != other.javaClass) return false

        other as Pelicula

        if (id != other.id) return false  // Compara los ids de las películas
        if (titulo != other.titulo) return false
        if (director != other.director) return false
        if (anyoEstreno != other.anyoEstreno) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + titulo.hashCode()
        result = 31 * result + director.hashCode()
        result = 31 * result + anyoEstreno!!
        return result
    }
}

