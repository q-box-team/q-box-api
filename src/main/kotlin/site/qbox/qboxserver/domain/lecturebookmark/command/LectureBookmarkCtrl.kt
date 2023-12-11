package site.qbox.qboxserver.domain.lecturebookmark.command

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.qbox.qboxserver.domain.lecturebookmark.command.dto.CreateLectureBookmarkReq

@RestController
@RequestMapping("lecture-bookmark")
class LectureBookmarkCtrl (
    private val lectureBookmarkSvc: LectureBookmarkSvc,
){
    @PostMapping
    fun register(@RequestBody req : CreateLectureBookmarkReq) =
        lectureBookmarkSvc.register(req)
}