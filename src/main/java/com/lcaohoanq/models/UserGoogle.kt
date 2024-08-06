package com.lcaohoanq.models

data class UserGoogle(
    val id: String? = null,
    val email: String? = null,
    val verified_email: Boolean = false,
    val name: String? = null,
    val given_name: String? = null,
    val family_name: String? = null,
    val picture: String? = null
)

fun main(args: Array<String>) {
    val userGoogle = UserGoogle(
        name = "Hoang",
        given_name = "Hoan",
        family_name = "Nguyen",
        email = "hoang",
        picture = "https://www.google.com",
        verified_email = true
    )
    println(userGoogle)
}
