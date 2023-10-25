package site.qbox.qboxserver.domain.lecture.command

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import site.qbox.qboxserver.domain.lecture.command.dto.CreateLectureReq

@RestController
@RequestMapping("lectures")
class LectureCtrl(
    private val lectureSvc: LectureSvc,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createLecture(@RequestBody @Valid req: CreateLectureReq) = lectureSvc.create(req)
}