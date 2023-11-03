package site.qbox.qboxserver.domain.univ.command

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.qbox.qboxserver.domain.univ.command.dto.CreateUnivReq

@SpringBootTest
@DisplayName("UnivSvc")
class UnivSvcTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var univSvc: UnivSvc

    @Autowired
    lateinit var univRepo: UnivRepo

    init {
        it("univ 생성을 수행한다") {
            val req = CreateUnivReq("test.ac.kr", "테스트대학교")

            val result = univSvc.create(req)

            result.id.shouldNotBeNull()
        }

        afterEach {
            univRepo.deleteAll()
        }
    }
}
