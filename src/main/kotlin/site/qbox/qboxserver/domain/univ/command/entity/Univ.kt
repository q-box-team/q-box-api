package site.qbox.qboxserver.domain.univ.command.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Univ(
    @Id val emailDomain: String,
    @Column(nullable = false) val name: String,
)