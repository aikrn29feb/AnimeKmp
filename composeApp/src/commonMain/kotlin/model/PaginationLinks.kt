package model

data class PaginationLinks(
    val first: String,
    val previous: String?,
    val next: String?,
    val last: String
) 