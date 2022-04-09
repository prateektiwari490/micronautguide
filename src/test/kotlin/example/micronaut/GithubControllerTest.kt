package example.micronaut

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@MicronautTest
class GithubControllerTest {

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun verifyGithubReleasesCanBeFetchedWithLowLevelHttpClient() {
        //when:
        val request: HttpRequest<Any> = HttpRequest.GET("/github/releases-lowlevel")
        val rsp = client.toBlocking().exchange(request,
            Argument.listOf(GithubRelease::class.java))

        //then: 'the endpoint can be accessed'
        assertEquals(HttpStatus.OK, rsp.status)
        assertNotNull(rsp.body())

        //when:
        val releases = rsp.body()

        //then:
        assertNotNull(releases)
        val regex = Regex("Micronaut [0-9].[0-9].[0-9]([0-9])?( (RC|M)[0-9])?")
        for (release in releases) {
            println(release.name)
            assertTrue(regex.matches(release.name))
        }
    }

    @Test
    fun verifyGithubReleasesCanBeFetchedWithCompileTimeAutoGeneratedAtClient() {
        //when:
        val request: HttpRequest<Any> = HttpRequest.GET("/github/releases-lowlevel")
        val githubReleases = client.toBlocking().retrieve(request, Argument.listOf(GithubRelease::class.java))

        //then:
        val regex = Regex("Micronaut [0-9].[0-9].[0-9]([0-9])?( (RC|M)[0-9])?")
        for (release in githubReleases) {
            println(release.name)
            assertTrue(regex.matches(release.name))
        }
    }
}