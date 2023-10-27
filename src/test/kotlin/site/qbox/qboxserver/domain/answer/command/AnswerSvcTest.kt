package site.qbox.qboxserver.domain.answer.command

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.qbox.qboxserver.domain.answer.command.dto.CreateAnswerCommentReq
import site.qbox.qboxserver.domain.answer.command.dto.CreateAnswerReq
import site.qbox.qboxserver.domain.answer.command.entity.AnswerId

@SpringBootTest
@DisplayName("AnswerSvc")
class AnswerSvcTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var answerRepo: AnswerRepo
    @Autowired
    lateinit var answerCommentRepo: AnswerCommentRepo

    @Autowired
    lateinit var answerSvc: AnswerSvc

    init {
        it("answer 생성을 수행한다") {
            val question = 5L
            val writer = "writer"
            val req = CreateAnswerReq("답변입니당", question)

            answerSvc.create(req, writer)

            val result = answerRepo.findById(AnswerId(question, writer)).get()

            result.id.shouldNotBeNull()
            result.content shouldBe req.content
            result.id.questionId shouldBe question
            result.id.writerId shouldBe writer
        }

        it("answer comment 생성을 수행한다") {
            val content = "댓글내용"
            val req =  CreateAnswerCommentReq(AnswerId(4, "aaa@bb.com"), content)

            answerSvc.addAnswer(req, "댓글작성자")
            answerSvc.addAnswer(req, "댓글작성자")
            answerSvc.addAnswer(req, "댓글작성자")

            val result = answerCommentRepo.findAll()

            result.size shouldBe 3
            result.map { it.content }.containsAll(listOf(content, content, content))
        }

        afterEach {
            answerRepo.deleteAll()
            answerCommentRepo.deleteAll()
        }
    }
}
