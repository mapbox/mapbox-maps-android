#!/usr/bin/python
import json
import os

# run gradle license generation
os.system("./gradlew sdk:licenseReleaseReport")

# convert output to LICENSE.md file
path = os.getcwd()
try:
    with open(path + "/LICENSE.md", 'w+') as licenseFile:
        licenseFile.write("<!-- This file was generated. Use `python scripts/license-generate.py`to update. -->  \n")
        licenseFile.write("## Additional Mapbox Maps Android licenses  \n")
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
                                  ("URL: [%s](%s)\n" % (projectUrl, projectUrl) if projectUrl is not None else "") +
                                  "License: [%s](%s)" % (licenseName, licenseUrl) +
                                  "\n\n===========================================================================\n\n")
    licenseFile.close()
except IOError as err:
    print("I/O error:} " + err)

# remove obsolete open_source_licenses.html
os.system("rm -f sdk/src/main/assets/open_source_licenses.html")


