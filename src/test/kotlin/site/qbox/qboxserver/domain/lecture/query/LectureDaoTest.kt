package site.qbox.qboxserver.domain.lecture.query

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import site.qbox.qboxserver.domain.lecture.command.LectureRepo
import site.qbox.qboxserver.domain.lecture.command.entity.Lecture

@SpringBootTest
@DisplayName("LectureDao")
class LectureDaoTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var lectureDao: LectureDao

    @Autowired
    lateinit var lectureRepo: LectureRepo

    init {
        context("이미 lecture가 저장되어 있을 때") {
            val targetDepartId: Long = 5
            beforeEach {
                lectureRepo.saveAll(
                    listOf(
                        Lecture("112", targetDepartId, "심리학"),
                        Lecture("113", targetDepartId, "네트워크"),
                        Lecture("114", targetDepartId, "운영체제"),
                        Lecture("115", targetDepartId, "플라즈마"),
                        Lecture("116", targetDepartId, "임베디드"),
                        Lecture("117", targetDepartId, "경영수학"),
                        Lecture("118", targetDepartId, "수학2"),
                        Lecture("119", targetDepartId, "국어"),
                        Lecture("345", targetDepartId, "영어기초"),
                        Lecture("567", targetDepartId, "디자인분석"),
                        Lecture("ABCD", targetDepartId, "웹디자인기초"),
                        Lecture("HHH", targetDepartId, "웹프로그래밍"),
                        Lecture("KKK", targetDepartId, "자바"),
                        Lecture("III", targetDepartId, "파이썬"),

                        Lecture("KKK", 6, "파이썬"),
                        Lecture("KKUK", 6, "심리학 기초"),
                        Lecture("5656", 6, "회계원리"),
                    )
                )
            }

            it("depart Id를 통한 목록 조회를 수행한다") {
                val page1 = 5
                val page2 = 6
                val page3 = 7
                val result1 = lectureDao.findAllByDepartId(targetDepartId, PageRequest.of(0, page1))
                val result2 = lectureDao.findAllByDepartId(targetDepartId, PageRequest.of(1, page2))
                val result3 = lectureDao.findAllByDepartId(targetDepartId, PageRequest.of(0, page3))

                result1.size shouldBe 5
                result1.map { it.name } shouldContain "네트워크"
                result2.size shouldBe 6
                result3.size shouldBe 7
            }
        }

        afterEach {
            lectureRepo.deleteAll()
        }
    }
}
