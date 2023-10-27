package site.qbox.qboxserver.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.snippet.Snippet
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.util.MultiValueMap
import java.nio.charset.StandardCharsets

@ContextConfiguration
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(TestSecurityConfig::class)
@WithUserDetails(value = "username", userDetailsServiceBeanName = "testUserDetailService")
@MockBean(JpaMetamodelMappingContext::class)
abstract class WebClientDocsTest : DescribeSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var mockMvc: MockMvc
    private val mapper = jacksonObjectMapper()

    protected fun performPost(endpoint: String, body: Any? = null): ResultActions {
        val requestBuilder = post(endpoint)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .apply {
                body?.let { content(generateBody(it)) }
            }

        return mockMvc.perform(requestBuilder)
    }

    protected fun performGet(endpoint: String, vararg urlValue: String): ResultActions {
        return mockMvc.perform(get(endpoint, *urlValue))
    }

    protected fun performGet(
        endpoint: String,
        params: MultiValueMap<String, String>,
        vararg urlValue: String,
    ): ResultActions {

        return mockMvc.perform(get(endpoint, *urlValue).params(params))
    }

    protected fun performFormData(method: HttpMethod, endpoint: String, filename: String): ResultActions {
        return mockMvc.perform(
            multipart(method, endpoint)
                .file(filename, ByteArray(0))
                .with(csrf())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .characterEncoding(StandardCharsets.UTF_8)
        )
    }

    private fun generateBody(obj: Any) = mapper.writeValueAsString(obj)

    protected fun print(title: String, vararg snippets: Snippet) =
        document(title, getDocumentRequest(), getDocumentResponse(), *snippets)


    protected fun getDocumentRequest() = preprocessRequest(prettyPrint())
    protected fun getDocumentResponse() = preprocessResponse(prettyPrint())
}
