package example.micronaut

import io.micronaut.http.HttpHeaders.ACCEPT
import io.micronaut.http.HttpHeaders.USER_AGENT
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Headers
import io.micronaut.http.client.annotation.Client
import org.reactivestreams.Publisher;

@Client(GithubConfiguration.GITHUB_API_URL)
@Headers(
    Header(name = USER_AGENT, value = "Micronaut HTTP Client"),
    Header(name = ACCEPT, value = "application/vnd.github.v3+json, application/json")
)
interface GithubApiClient {

    @Get("/repos/\${github.organization}/\${github.repo}/releases")
    fun fetchReleases(): Publisher<GithubRelease?>?
}