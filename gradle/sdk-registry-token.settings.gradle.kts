// Fails fast with setup instructions when the token needed to download Mapbox dependencies is
// missing or of the wrong kind, instead of an "unknown property" or HTTP 401/403 error later on.
val sdkRegistryToken = providers.environmentVariable("SDK_REGISTRY_TOKEN")
  .orElse(providers.gradleProperty("SDK_REGISTRY_TOKEN"))
  .orNull

val problem = when {
  sdkRegistryToken.isNullOrBlank() -> "SDK_REGISTRY_TOKEN is not set."
  sdkRegistryToken.startsWith("pk.") ->
    "SDK_REGISTRY_TOKEN is a public token (pk.), but a secret token (sk.) is required."
  else -> null
}

if (problem != null) {
  throw GradleException(
    """
    |$problem
    |
    |Building this project requires a Mapbox secret access token with the DOWNLOADS:READ scope:
    |  1. Sign up or log in at https://account.mapbox.com/
    |  2. Create a token at https://account.mapbox.com/access-tokens/ with the DOWNLOADS:READ
    |     secret scope checked, and copy the generated token (it starts with "sk.").
    |  3. Either export it as the SDK_REGISTRY_TOKEN environment variable, or add it to
    |     ~/.gradle/gradle.properties:
    |       SDK_REGISTRY_TOKEN=sk.xxx
    |
    |See DEVELOPING.md for more details.
    """.trimMargin()
  )
}
