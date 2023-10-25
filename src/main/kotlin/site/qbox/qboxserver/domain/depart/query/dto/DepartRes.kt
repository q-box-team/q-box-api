package site.qbox.qboxserver.domain.depart.query.dto

import site.qbox.qboxserver.domain.depart.command.entity.Depart

data class DepartRes(
    val id: Long,
    val name: String,
    val univId: String,
) {
    constructor(depart: Depart) : this(depart.id, depart.name, depart.univId)
}