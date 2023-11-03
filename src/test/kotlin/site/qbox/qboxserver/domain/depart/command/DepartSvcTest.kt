package site.qbox.qboxserver.domain.depart.command

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.qbox.qboxserver.domain.depart.command.dto.CreateDepartReq

@SpringBootTest
@DisplayName("DepartSvc")
class DepartSvcTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var departSvc: DepartSvc

    @Autowired
    lateinit var departRepo: DepartRepo


    init {
        it("Depart 생성을 수행한다.") {
            val req = CreateDepartReq("컴뷰터공학과", "test.ac.kr")

            val result = departSvc.create(req)

            result.id.shouldNotBeNull()
        }

        afterEach {
            departRepo.deleteAll()
        }
    }
}
