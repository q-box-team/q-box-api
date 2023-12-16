package site.qbox.qboxserver.domain.lecturebookmark.command

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import site.qbox.qboxserver.domain.lecturebookmark.command.dto.CreateLectureBookmarkReq

@RestController
@RequestMapping("lecture-bookmarks")
class LectureBookmarkCtrl (
    private val lectureBookmarkSvc: LectureBookmarkSvc,
){
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody req : CreateLectureBookmarkReq) =
        lectureBookmarkSvc.register(req)
}