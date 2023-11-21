package site.qbox.qboxserver.domain.image

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import site.qbox.qboxserver.domain.image.dto.ImageRes

@RestController
@RequestMapping("images")
class ImageCtrl(
    private val imageSvc: ImageSvc,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestPart file: MultipartFile): ImageRes =
        imageSvc.save(file)
}