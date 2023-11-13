package site.qbox.qboxserver.domain.image.command

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@RequestMapping("Images")
class ImageCtrl (
    private val imageSvc: ImageSvc,
){

    @PostMapping
    fun save(@RequestPart file: MultipartFile) {
        imageSvc.save(file)
    }
}