package site.qbox.qboxserver.domain.question.query

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import site.qbox.qboxserver.domain.lecture.command.LectureRepo
import site.qbox.qboxserver.domain.lecture.command.entity.Lecture
import site.qbox.qboxserver.domain.member.command.MemberRepo
import site.qbox.qboxserver.domain.member.command.entity.Member
import site.qbox.qboxserver.domain.question.command.QuestionRepo
import site.qbox.qboxserver.domain.question.command.entity.Question

@SpringBootTest
@DisplayName("QuestionDao")
class QuestionDaoTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var questionDao: QuestionDao

    @Autowired
    lateinit var memberRepo: MemberRepo

    @Autowired
    lateinit var questionRepo: QuestionRepo

    @Autowired
    lateinit var lectureRepo: LectureRepo

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    init {
        context("question들이 생성되어 있을 때") {
            beforeEach {
                val members = memberRepo.saveAll(
                    listOf(
                        Member("aaa@ccc.ac.kr", "n1", "p1", passwordEncoder),
                        Member("bbb@ccc.ac.kr", "n2", "p2", passwordEncoder)
                    )
                )
                val lectures = lectureRepo.saveAll(
                    listOf(
                        Lecture("123", 1, "경제학"),
                        Lecture("456", 1, "사회학")
                    )
                )
                val questions = questionRepo.saveAll(
                    listOf(
                        Question("제목0", "내용", lectures[0].id, members[0].email),
                        Question("제목1", "내용", lectures[0].id, members[1].email),
                        Question("제목2", "내용", lectures[0].id, members[0].email),
                        Question("제목3", "내용", lectures[1].id, members[0].email),
                        Question("제목4", "내용", lectures[1].id, members[0].email),
                    )
                )
            }
            it("전체 목록을 조회한다") {
                val result = questionDao.findAllByLecture("123", 1, PageRequest.of(0, 100))
                result.size shouldBe 3
            }
            it("Id를 통한 조회를 수행한다") {
                val any = questionRepo.findAll()[0]

                val result = questionDao.getBy(any.id)

                result.id shouldBe any.id
                result.lecture.code shouldBe any.lecture.code
                result.writer.email shouldBe any.writerId
            }
        }

        afterEach {
            memberRepo.deleteAll()
            lectureRepo.deleteAll()
            questionRepo.deleteAll()
        }
    }

}
