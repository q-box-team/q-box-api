package site.qbox.qboxserver.domain.question.command.entity

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import site.qbox.qboxserver.domain.lecture.command.entity.LectureId

@DisplayName("Question")
class QuestionTest : DescribeSpec({

    it("내용 변경을 수행한다 ") {
        val question = Question("제목", "내용", LectureId("AAABB", 5), "작성자")

        val newTitle = "새로운 제목"
        val newContent = "새로운 내용"
        question.changeContents(newTitle, newContent)

        question.title shouldBe newTitle
        question.body shouldBe newContent
    }
})
