package site.qbox.qboxserver.global.entity

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId
import site.qbox.qboxserver.domain.question.command.QuestionRepo
import site.qbox.qboxserver.domain.question.command.entity.Question
import java.time.LocalDateTime

@DataJpaTest
@DisplayName("BaseEntity")
class BaseEntityTest : DescribeSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var questionRepo: QuestionRepo

    init {
        describe("Question에서") {
            val beforeTime = LocalDateTime.now()
            val question = Question("제목", "내용", LectureId("AAABB", 5), "작성자")

            context("새로 생성이 된 후") {
                var result = questionRepo.save(question)
                it("createdAt이 새롭게 반영된다") {
                    result.createdAt.isAfter(beforeTime) shouldBe true
                }

                it("isUpdated가 false를 반환한다") {
                    result.isUpdated.shouldNotBeNull()
                    result.isUpdated shouldBe false
                }
                context("내용이 변경되면") {
                    result.changeContents("새로운 내용", "새로운 내용")
                    val updateResult = questionRepo.save(result)
                    it("update가 새롭게 반영된다") {
                        updateResult.updatedAt.isAfter(result.updatedAt) shouldBe true
                    }
                    it("isUpdated가 true를 반환한다") {
                        updateResult.isUpdated shouldBe true
                    }
                }
            }
        }

        afterEach {
            questionRepo.deleteAll()
        }
    }
}
