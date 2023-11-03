package site.qbox.qboxserver.domain.member.email.infra

import org.springframework.data.repository.CrudRepository
import site.qbox.qboxserver.domain.member.email.entity.Email

interface AuthenticatedEmailRepo : CrudRepository<Email, String>