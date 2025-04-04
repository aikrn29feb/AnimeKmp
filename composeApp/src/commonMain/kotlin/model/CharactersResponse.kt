package model

data class CharactersResponse(
    val items: List<Character>,
    val meta: PaginationMeta,
    val links: PaginationLinks
) 