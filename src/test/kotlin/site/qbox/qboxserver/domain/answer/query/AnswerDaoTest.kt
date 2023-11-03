package site.qbox.qboxserver.domain.answer.query

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import site.qbox.qboxserver.domain.answer.command.AnswerCommentRepo
import site.qbox.qboxserver.domain.answer.command.AnswerRepo
import site.qbox.qboxserver.domain.answer.command.entity.Answer
import site.qbox.qboxserver.domain.answer.command.entity.AnswerComment
import site.qbox.qboxserver.domain.answer.command.entity.AnswerId
import site.qbox.qboxserver.domain.member.command.MemberRepo
import site.qbox.qboxserver.fixture.member.MemberFixture

@SpringBootTest
@DisplayName("AnswerDao")
class AnswerDaoTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var answerDao: AnswerDao

    @Autowired
    lateinit var answerRepo: AnswerRepo
    @Autowired
    lateinit var answerCommentRepo: AnswerCommentRepo
    @Autowired
    lateinit var memberRepo: MemberRepo
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    init {
        context("answer들이 생성되어 있을 때") {
            val targetQuestion = 1L
            beforeEach {
                val members = memberRepo.saveAll(MemberFixture.members)

                val answers = answerRepo.saveAll(
                    listOf(
                        Answer("내용1", AnswerId(targetQuestion, members[0].id)),
                        Answer("내용2", AnswerId(targetQuestion, members[1].id)),
                        Answer("내용3", AnswerId(targetQuestion, members[2].id)),
                        Answer("내용4", AnswerId(targetQuestion, members[3].id)),
                        Answer("내용5", AnswerId(targetQuestion, members[4].id)),
                        Answer("내용6", AnswerId(2, members[0].id)),
                        Answer("내용7", AnswerId(2, members[1].id)),
                        Answer("내용8", AnswerId(2, members[2].id)),
                    )
                )

                answerCommentRepo.saveAll(
                    listOf(
                        AnswerComment("댓글1", members[0].id, answers[1].id),
                        AnswerComment("댓글2", members[1].id, answers[1].id),
                        AnswerComment("댓글3", members[1].id, answers[1].id),
                        AnswerComment("댓글4", members[2].id, answers[1].id),
                        AnswerComment("댓글5", members[1].id, answers[2].id),
                        AnswerComment("댓글6", members[3].id, answers[2].id),
                        AnswerComment("댓글7", members[3].id, answers[7].id),
                    )
                )


            }

            it("question을 통한 목록 조회를 수행한다.") {
                val result = answerDao.findAllByQuestion(targetQuestion)
                result.size shouldBe 5
                result.map { it.content } shouldContainAll listOf("내용1", "내용2", "내용3", "내용4", "내용5")
            }

            it("answer에 해당하는 댓글들을 조회한다.") {
                val result = answerDao.findAllByQuestion(targetQuestion)
                result.size shouldBe 5
                result[0].comments.map { it.content }.containsAll(listOf("댓글1","댓글2", "댓글3", "댓글4"))
            }
        }

        afterEach {
            memberRepo.deleteAll()
            answerRepo.deleteAll()
            answerCommentRepo.deleteAll()
        }
    }
}
