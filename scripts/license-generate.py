#!/usr/bin/python
import json
import os

# run gradle license generation
os.system("./gradlew sdk:licenseReleaseReport")

# convert output to LICENSE.md file
path = os.getcwd()
try:
    with open(path + "/LICENSE.md", 'w+') as licenseFile:
        licenseFile.write("### License\n")
        licenseFile.write("\n")
        licenseFile.write("Mapbox Maps for Android version 10.0\n")
        licenseFile.write("Mapbox Maps Android SDK\n")
        licenseFile.write("\n")
        licenseFile.write("Copyright &copy; 2021 Mapbox\n")
        licenseFile.write("\n")
        licenseFile.write("All rights reserved.\n")
        licenseFile.write("\n")
        licenseFile.write("Mapbox Maps for Android version 10.0 ('Mapbox Maps Android SDK') or higher must be used according to the Mapbox Terms of Service. This license allows developers with a current active Mapbox account to use and modify the Mapbox Maps Android SDK. Developers may modify the Mapbox Maps Android SDK code so long as the modifications do not change or interfere with marked portions of the code related to billing, accounting, and anonymized data collection. The Mapbox Maps Android SDK sends anonymized location and usage data, which Mapbox uses for fixing bugs and errors, accounting, and generating aggregated anonymized statistics. This license terminates automatically if a user no longer has an active Mapbox account.\n")
        licenseFile.write("\n")
        licenseFile.write("For the full license terms, please see the Mapbox Terms of Service at https://www.mapbox.com/legal/tos/\n")
        licenseFile.write("\n")
        licenseFile.write("## Additional Mapbox Maps Android licenses\n")
        with open(path + "/sdk/build/reports/licenses/licenseReleaseReport.json", 'r') as dataFile:
            data = json.load(dataFile)

            gradleLicensePlugin ="""
            {
                "project": "Gradle License Plugin",
                "url": "https://github.com/jaredsburrows/gradle-license-plugin",
                "licenses": [
                    {
                        "license": "The Apache Software License, Version 2.0",
                        "license_url": "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                 ]
            }
            """
            data.append(json.loads(gradleLicensePlugin))

            for entry in data:
                projectName = entry["project"]
                projectUrl = entry["url"]
                for license in entry["licenses"]:
                    licenseName = license["license"]
                    licenseUrl = license["license_url"]

                licenseFile.write("Mapbox Maps Android uses portions of the %s.\n" % projectName +
                                  ("\nURL: [%s](%s)\n" % (projectUrl, projectUrl) if projectUrl is not None else "") +
                                  "\nLicense: [%s](%s)" % (licenseName, licenseUrl) +
                                  "\n\n===========================================================================\n\n")
    licenseFile.close()
except IOError as err:
    print("I/O error:} " + err)

# remove obsolete open_source_licenses.html
os.system("rm -f sdk/src/main/assets/open_source_licenses.html")


