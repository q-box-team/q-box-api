package site.qbox.qboxserver.domain.image

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import site.qbox.qboxserver.domain.image.dto.ImageRes

@RestController
@RequestMapping("images")
class ImageCtrl(
    private val imageSvc: ImageSvc,
) {

    @PostMapping
    fun save(@RequestPart file: MultipartFile): ImageRes =
        imageSvc.save(file)
}