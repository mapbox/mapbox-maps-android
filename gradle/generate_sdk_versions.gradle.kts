/**
 * This is a script to replace android-sdk-versions-plugin
 * (https://github.com/mapbox/android-sdk-versions-plugin)
 */

fun versionNameCompliesSemVer(name: String): Boolean {
    val regex = "^([0-9]+)\\.([0-9]+)\\.([0-9]+)(?:-([0-9A-Za-z-]+(?:\\.[0-9A-Za-z-]+)*))?(?:\\+[0-9A-Za-z-]+)?\$".toRegex()
    return regex.matches(name)
}

val sdkVersionsDir = File(project.projectDir.toString() + "/src/main/assets/sdk_versions")
val sdkVersionFileName = org.gradle.internal.hash.HashUtil.sha256(project.name.toByteArray()).asHexString()
val sdkVersionFile = File(sdkVersionsDir.toString() + "/" + sdkVersionFileName)

if (!versionNameCompliesSemVer(project.version as String)) {
    val message = "Version \"${project.version}\" of project \"${project.name}\" is invalid. Should match the standard https://semver.org"
    throw IllegalArgumentException(message)
}

if (!sdkVersionsDir.exists()) {
    sdkVersionsDir.mkdirs()
}

val sdkVersionString = project.name + "/" + project.version
sdkVersionFile.createNewFile()
val output = java.io.FileOutputStream(sdkVersionFile, false)
output.write(sdkVersionString.toByteArray())
output.write("\nv".toByteArray())
output.close()