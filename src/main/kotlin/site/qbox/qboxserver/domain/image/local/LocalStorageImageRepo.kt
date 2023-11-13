package site.qbox.qboxserver.domain.image.local

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import site.qbox.qboxserver.domain.image.ImageRepo
import java.io.File

@Repository
class LocalStorageImageRepo(
    @Value("\${local-storage.directory}") private val localStorageDirectory: String,
    @Value("\${local-storage.url}") private val localStorageUrl: String,
) : ImageRepo {
    override fun save(file: MultipartFile, name: String): String {
        file.transferTo(File(localStorageDirectory, name))
        return "${localStorageUrl}/${name}"
    }
}