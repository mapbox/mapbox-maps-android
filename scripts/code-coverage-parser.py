import argparse
import requests
import os
import sys
import xml.etree.ElementTree as ET
import subprocess
import glob

CODECOV_FILE_PATTERN = "*/build/jacoco/jacoco.xml"


def _parse_code_coverage(path):
    root = ET.parse(path).getroot()
    for mutation in root:
        if 'type' in mutation.attrib and mutation.attrib['type'] == 'LINE':
            covered = int(mutation.attrib['covered'])
            missed = int(mutation.attrib['missed'])
            return covered, missed

    sys.exit("Can't parse code coverage")


def _get_total_coverage():
    xml_reports = glob.glob(CODECOV_FILE_PATTERN)
    covered_sum = 0
    missed_sum = 0
    for report_path in xml_reports:
        covered, missed = _parse_code_coverage(report_path)
        covered_sum += covered
        missed_sum += missed

    return round((covered_sum / (covered_sum + missed_sum)) * 100.0, 2)


if __name__ == '__main__':
    token = os.getenv("GITHUB_ACCESS_TOKEN")
    parser = argparse.ArgumentParser(description="Sets the status of a commit on GitHUB.")

    parser.add_argument("--token", default=token, dest="token",
                        help="GitHub token, otherwise environment GITHUB_ACCESS_TOKEN. Needs repo:status scope.")
    parser.add_argument("--slug", dest="slug", default="mapbox/mapbox-maps-android",
                        help="Repository slug, example: mapbox/mapbox-maps-android.")
    parser.add_argument("--hash", dest="hash", required=True,
                        help="Commit git hash.")

    args = parser.parse_args()

    total_coverage = _get_total_coverage()
    description_message = "Code coverage: %s" % total_coverage
    print(description_message)

    subprocess.Popen(
        "scripts/ci-github-set-commit-status.py --token %s --slug %s --hash %s --description %s" % (
            args.token, args.slug, args.hash, description_message),
        shell=True
    )

