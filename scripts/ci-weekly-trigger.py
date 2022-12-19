#!/usr/bin/env python3

# Implements https://circleci.com/docs/api/v2/#trigger-a-new-pipeline

import argparse
import os
import requests
import sys

def TriggerPipeline(token, branch):
    url = "https://circleci.com/api/v2/project/github/mapbox/mapbox-maps-android/pipeline"
    headers = {
        "Content-Type": "application/json",
        "Accept": "application/json",
    }

    data = {
        "parameters": {
            "weekly": True
        }
    }

    data["branch"] = branch

    r = requests.post(url, auth=(token, ""), headers=headers, json=data)

    if r.status_code != 201 and r.status_code != 200:
        print("Error triggering the CircleCI: %s." % r.json()["message"])
        sys.exit(1)


def Main():
    parser = argparse.ArgumentParser(description="Creates CircleCI jobs and waits for the result.")
    parser.add_argument("--token", default=os.getenv("CIRCLE_API_TOKEN"),
            help="CircleCI token, otherwise environment CIRCLE_API_TOKEN.")
    parser.add_argument("--branch", default="main",
            help="Build a specific branch, otherwise it will build the default branch.")
    args = parser.parse_args()

    if not args.token:
        print("CircleCI token not set. Use --token or set CIRCLE_API_TOKEN.")
        sys.exit(1)

    TriggerPipeline(args.token, args.branch)
    return 0

if __name__ == "__main__":
    Main()