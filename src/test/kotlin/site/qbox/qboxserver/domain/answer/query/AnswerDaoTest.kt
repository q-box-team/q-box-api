package site.qbox.qboxserver.domain.answer.query

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import site.qbox.qboxserver.domain.answer.command.AnswerRepo
import site.qbox.qboxserver.domain.answer.command.entity.Answer
import site.qbox.qboxserver.domain.answer.command.entity.AnswerId
import site.qbox.qboxserver.domain.member.command.MemberRepo
import site.qbox.qboxserver.domain.member.command.entity.Member

@SpringBootTest
@DisplayName("AnswerDao")
class AnswerDaoTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var answerDao: AnswerDao

    @Autowired
    lateinit var answerRepo: AnswerRepo
    @Autowired
    lateinit var memberRepo: MemberRepo
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    init {
        context("answer들이 생성되어 있을 때") {
            val targetQuestion = 1L
            beforeEach {
                val members = memberRepo.saveAll(
                    listOf(
                        Member("aaa@ccc.ac.kr", "n1", "p1", passwordEncoder),
                        Member("bbb@ccc.ac.kr", "n2", "p2", passwordEncoder),
                        Member("fff@ccc.ac.kr", "n3", "p4", passwordEncoder),
                        Member("ggg@ccc.ac.kr", "n4", "32", passwordEncoder),
                        Member("hhh@ccc.ac.kr", "n5", "42", passwordEncoder),
                    )
                )

                answerRepo.saveAll(
                    listOf(
                        Answer("내용1", AnswerId(targetQuestion, members[0].email)),
                        Answer("내용2", AnswerId(targetQuestion, members[1].email)),
                        Answer("내용3", AnswerId(targetQuestion, members[2].email)),
                        Answer("내용4", AnswerId(targetQuestion, members[3].email)),
                        Answer("내용5", AnswerId(targetQuestion, members[4].email)),
                        Answer("내용6", AnswerId(2, members[0].email)),
                        Answer("내용7", AnswerId(2, members[1].email)),
                        Answer("내용8", AnswerId(2, members[2].email)),
                    )
                )
            }

            it("question을 통한 목록 조회를 수행한다.") {
                val result = answerDao.findAllByQuestion(targetQuestion)
                result.size shouldBe 5
                result.map { it.content } shouldContainAll listOf("내용1", "내용2", "내용3", "내용4", "내용5")
            }
        }

        afterEach {
            memberRepo.deleteAll()
            answerRepo.deleteAll()
        }
    }
}
