package site.qbox.qboxserver.domain.lecture.command

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.qbox.qboxserver.domain.lecture.command.dto.CreateLectureReq
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId

@SpringBootTest
@DisplayName("LectureSvc")
class LectureSvcTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var lectureSvc: LectureSvc

    @Autowired
    lateinit var lectureRepo: LectureRepo


    init {
        it("Lecture 생성을 수행한다") {
            val code = "AAABBB"
            val departId = 4L
            val req = CreateLectureReq(code, "컴퓨터공학", departId)
            lectureSvc.create(req)

            val savedLecture = lectureRepo.findById(LectureId(code, departId)).get()

            savedLecture.name shouldBe req.name
            savedLecture.id.code shouldBe req.code
            savedLecture.id.departId shouldBe req.departId
        }

        afterEach {
            lectureRepo.deleteAll()
        }
    }
}
