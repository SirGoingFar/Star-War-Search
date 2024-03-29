package com.trivago.starwarsearch.data.datasource.specie.data

import com.trivago.starwarsearch.data.dto.specie.Planet
import com.trivago.starwarsearch.data.dto.specie.Specie

object SpecieDataUtil {

    val specieUrlList = listOf(
        "http://swapi.dev/api/species/1/",
        "http://swapi.dev/api/species/2/",
        "http://swapi.dev/api/species/3/",
        "http://swapi.dev/api/species/3/",
        "http://swapi.dev/api/species/5/"
    )

    val specieList = listOf(
        Specie(
            "Name",
            "Language",
            "http://swapi.dev/api/planets/1/",
            "Home World Name",
            "190000"
        ),
        Specie(
            "Name",
            "Language",
            "http://swapi.dev/api/planets/2/",
            "Home World Name",
            "190000"
        ),
        Specie(
            "Name",
            "Language",
            "http://swapi.dev/api/planets/3/",
            "Home World Name",
            "190000"
        ),
        Specie(
            "Name",
            "Language",
            "http://swapi.dev/api/planets/4/",
            "Home World Name",
            "190000"
        ),
        Specie(
            "Name",
            "Language",
            "http://swapi.dev/api/planets/5/",
            "Home World Name",
            "190000"
        )
    )

    val planetList = listOf(
        Planet("Name", "Population"),
        Planet("Name", "Population"),
        Planet("Name", "Population"),
        Planet("Name", "Population"),
        Planet("Name", "Population")
    )

    val specieUrlToSpecieMap: Map<String, Specie> = specieList.mapIndexed { index, specie ->
        specieUrlList[index] to specie
    }.toMap()

    val specieUrlToPlanetMap: Map<String, Planet> = planetList.mapIndexed { index, planet ->
        specieUrlList[index] to planet
    }.toMap()

}