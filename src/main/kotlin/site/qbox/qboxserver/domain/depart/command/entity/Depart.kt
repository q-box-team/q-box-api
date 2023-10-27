package site.qbox.qboxserver.domain.depart.command.entity

import jakarta.persistence.*

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(
            columnNames = ["name", "univId"]
        )
    ]
)
class Depart(
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var univId: String,
    @Id @GeneratedValue val id: Long = 0L,
)