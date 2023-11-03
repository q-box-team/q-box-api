package site.qbox.qboxserver.domain.question.command

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId
import site.qbox.qboxserver.domain.question.command.dto.CreateQuestionReq

@SpringBootTest
@DisplayName("QuestionSvc")
class QuestionSvcTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var questionSvc: QuestionSvc
    @Autowired
    lateinit var questionRepo: QuestionRepo

    init {
        it("question 생성을 수행한다") {
            val req = CreateQuestionReq("제목", "내용", LectureId("AAABB", 5))

            val result = questionSvc.create(req, "토르")

            result.id.shouldNotBeNull()
        }

        afterEach {
            questionRepo.deleteAll()
        }
    }
}
