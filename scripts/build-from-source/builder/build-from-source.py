import os

def replace_in_file(needle, replacement, haystack) :
    temp = ""
    with open(haystack, "r+") as file:
        for line in file:
            if needle in line:
               temp += replacement
            else: # not found append current line
               temp += line

    with open(haystack, "w") as file:
        file.write(temp)

def append_in_file(needle, addition, haystack) :
    temp = ""
    with open(haystack, "r+") as file:
        for line in file:
            temp += line
            if needle in line:
               temp += addition

    with open(haystack, "w") as file:
        file.write(temp)


if __name__ == '__main__':

        #
        # update sdk-base/build.gradle.kts to replace binary distributions
        #
        # Replace:
        #  api(Dependencies.mapboxGlNative)
        #  api(Dependencies.mapboxCoreCommon)
        # With:
        #  api(project(":maps-core"))
        #  api(project(":common"))
        #
        gl_old = "api(Dependencies.mapboxGlNative)"
        gl_new = "  api(project(\":maps-core\"))\n"
        replace_in_file(gl_old, gl_new, "sdk-base/build.gradle.kts")

        common_old = "api(Dependencies.mapboxCoreCommon)"
        common_new = "  api(project(\":common\"))\n"
        replace_in_file(common_old, common_new, "sdk-base/build.gradle.kts")

        #
        # update sdk/build.gradle.kts to replace binary distributions
        #
        # Replace:
        #  api(Dependencies.mapboxOkHttp)
        # With:
        #  api(project(":okhttp"))
        #
        okhttp_old = "api(Dependencies.mapboxOkHttp)"
        okhttp_new = "  api(project(\":okhttp\"))\n"
        replace_in_file(okhttp_old, okhttp_new, "sdk/build.gradle.kts")

        #
        # append maven configuration into build.gradle.kts
        #
        #     maven {
        #       url = uri("http://mapbox.bintray.com/mapbox_private")
        #       credentials {
        #         username = System.getenv("BINTRAY_USER") ?: project.property("BINTRAY_USER") as String
        #         password = System.getenv("BINTRAY_API_KEY") ?: project.property("BINTRAY_API_KEY") as String
        #       }
        #     }
        #
        maven_append_below = "jcenter()"
        maven_addition = "\t\tmaven {\n\t\t\turl = uri(\"http://mapbox.bintray.com/mapbox_private\")\n\t\t\tcredentials {\n\t\t\t\tusername = System.getenv(\"BINTRAY_USER\") ?: project.property(\"BINTRAY_USER\") as String\n\t\t\t\tpassword = System.getenv(\"BINTRAY_API_KEY\") ?: project.property(\"BINTRAY_API_KEY\") as String\n\t\t\t}\n\t\t}\n"
        append_in_file(maven_append_below, maven_addition, "build.gradle.kts")

        #
        # append gradle plugin configuration
        #
        #      classpath(Plugins.bintray)
        #      classpath(Plugins.mapboxBindgen)
        #      classpath(Plugins.mapboxNative)
        #
        plugin_append_below = "classpath(Plugins.mapboxSdkRegistry)"
        plugin_addition = "\t\tclasspath(Plugins.bintray)\n\t\tclasspath(Plugins.mapboxBindgen)\n\t\tclasspath(Plugins.mapboxNative)"
        append_in_file(plugin_append_below, plugin_addition, "build.gradle.kts")

        #
        # append project dependency to settings.gradle.kts
        #
        #   include(":maps-core")
        #   project(":maps-core").projectDir = File("./vendor/mapbox-gl-native-internal/internal/platform/android/sdk")
        #   include(":common")
        #   project(":common").projectDir = File("./vendor/mapbox-gl-native-internal/internal/vendor/common/platform/android/common")
        #
        project_append_below = "rootProject.buildFileName = \"build.gradle.kts\""
        project_addition = "\ninclude(\":maps-core\")\nproject(\":maps-core\").projectDir = File(\"./mapbox-gl-native-internal/internal/platform/android/sdk\")\ninclude(\":common\")\nproject(\":common\").projectDir = File(\"./mapbox-gl-native-internal/internal/vendor/common/platform/android/common\")\ninclude(\":okhttp\")\nproject(\":okhttp\").projectDir = File(\"./mapbox-gl-native-internal/internal/vendor/common/platform/android/okhttp\")"
        append_in_file(project_append_below, project_addition, "settings.gradle.kts")
