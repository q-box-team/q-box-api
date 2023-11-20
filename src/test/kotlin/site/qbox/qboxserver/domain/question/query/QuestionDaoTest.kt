package site.qbox.qboxserver.domain.question.query

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import site.qbox.qboxserver.domain.lecture.command.LectureRepo
import site.qbox.qboxserver.domain.lecture.command.entity.Lecture
import site.qbox.qboxserver.domain.member.command.MemberRepo
import site.qbox.qboxserver.domain.question.command.QuestionRepo
import site.qbox.qboxserver.domain.question.command.entity.Question
import site.qbox.qboxserver.domain.question.query.dto.QuestionCondition
import site.qbox.qboxserver.fixture.member.MemberFixture

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


    init {
        context("question들이 생성되어 있을 때") {
            beforeEach {
                val members = memberRepo.saveAll(MemberFixture.members)
                val lectures = lectureRepo.saveAll(
                    listOf(
                        Lecture("123", 1, "경제학"),
                        Lecture("456", 1, "사회학")
                    )
                )
                val questions = questionRepo.saveAll(
                    listOf(
                        Question("제목0", "내용0", lectures[0].id, members[0].id),
                        Question("제목1", "내용1", lectures[0].id, members[1].id),
                        Question("제목2", "내용2", lectures[0].id, members[0].id),
                        Question("제목3", "내용3", lectures[1].id, members[0].id),
                        Question("제목4", "내용4", lectures[1].id, members[0].id),
                    )
                )
            }
            it("전체 목록을 조회한다") {
                val condition = QuestionCondition(lectureCode = "123", lectureDepart = 1)
                val result = questionDao.findAllBy(condition, PageRequest.of(0, 100))
                result.size shouldBe 3
            }
            it("Id를 통한 조회를 수행한다") {
                val any = questionRepo.findAll()[0]

                val result = questionDao.getBy(any.id)

                result.id shouldBe any.id
                result.lecture.code shouldBe any.lecture.code
                result.writer.email shouldBe any.writerId
            }

            it("filter를 통한 전체 목록을 조회한다") {
                val condition = QuestionCondition(title="4", lectureCode = "456", lectureDepart = 1)
                val result = questionDao.findAllBy(condition, PageRequest.of(0, 100))
                result.size shouldBe 1
            }
        }

        afterEach {
            memberRepo.deleteAll()
            lectureRepo.deleteAll()
            questionRepo.deleteAll()
        }
    }

}
