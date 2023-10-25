package site.qbox.qboxserver.domain.question.command

import org.springframework.data.jpa.repository.JpaRepository
import site.qbox.qboxserver.domain.question.command.entity.Question

interface QuestionRepo : JpaRepository<Question, Long>