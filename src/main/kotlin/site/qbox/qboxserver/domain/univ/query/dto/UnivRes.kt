package site.qbox.qboxserver.domain.univ.query.dto

import site.qbox.qboxserver.domain.univ.command.entity.Univ

class UnivRes(
    val emailDomain: String,
    val name: String,
) {
    constructor(univ: Univ) : this(univ.emailDomain, univ.name)
}