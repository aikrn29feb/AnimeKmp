package org.atulit.anime.model

import kotlinx.serialization.Serializable

@Serializable
data class JikanResponse(
    val data: List<CharacterData> = emptyList(),
    val pagination: Pagination = Pagination()
)

@Serializable
data class Pagination(
    val last_visible_page: Int = 1,
    val has_next_page: Boolean = false,
    val current_page: Int = 1
)

@Serializable
data class CharacterData(
    val mal_id: Int,
    val name: String,
    val images: CharacterImages? = null,
    val favorites: Int = 0,
    val about: String = ""
)

@Serializable
data class CharacterImages(
    val jpg: ImageSet? = null,
    val webp: ImageSet? = null
)

@Serializable
data class ImageSet(
    val image_url: String = "",
    val small_image_url: String? = null
) 