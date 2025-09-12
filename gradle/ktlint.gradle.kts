val ktlintConfig by configurations.creating

dependencies {
  ktlintConfig("com.pinterest:ktlint:0.48.2")
}

val ktlint by tasks.registering(JavaExec::class) {
  group = LifecycleBasePlugin.VERIFICATION_GROUP
  description = "Check Kotlin code style"
  classpath = ktlintConfig
  mainClass.set("com.pinterest.ktlint.Main")
  // see https://pinterest.github.io/ktlint/latest/install/cli/#command-line-usage for more information
  args(
    "**/src/**/*.kt",
    "**.kts",
    "!**/build/**",
    "!**/ksp/**/*.kt",
  )
}

tasks.register<JavaExec>("ktlintFormat") {
  group = LifecycleBasePlugin.VERIFICATION_GROUP
  description = "Check Kotlin code style and format"
  classpath = ktlintConfig
  mainClass.set("com.pinterest.ktlint.Main")
  jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
  // see https://pinterest.github.io/ktlint/latest/install/cli/#command-line-usage for more information
  args(
    "-F",
    "**/src/**/*.kt",
    "**.kts",
    "!**/build/**",
    "!**/ksp/**/*.kt",
  )
}
