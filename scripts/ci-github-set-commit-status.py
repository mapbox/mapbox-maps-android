#!/usr/bin/env python3

# Implements https://developer.github.com/v3/repos/statuses/#create-a-status

import argparse
import os
import requests
import sys


def SetCommitStatus(slug, hash, token, params):
    url = "https://api.github.com/repos/%s/statuses/%s" % (slug, hash)

    headers = {
        "Authorization": "token %s" % token
    }

    r = requests.post(url, headers=headers, json=params)

    if r.status_code != 201:
        print("Error setting commit status: %s." % r.status_code)
        sys.exit(1)


def Main():
    token = os.getenv("GITHUB_ACCESS_TOKEN")
    url = os.getenv("CIRCLE_BUILD_URL", default="http://mapbox.com")
    context = "internal: " + os.getenv("CIRCLE_JOB", default="default")

    parser = argparse.ArgumentParser(
        description="Sets the status of a commit on GitHUB.")

    parser.add_argument("--token", default=token,
            help="GitHub token, otherwise environment GITHUB_API_TOKEN. Needs repo:status scope.")
    parser.add_argument("--slug", default="mapbox/mapbox-gl-native",
            help="Repository slug, example: mapbox/mapbox-gl-native.")
    parser.add_argument("--hash", required=True,
            help="Commit git hash.")
    parser.add_argument("--success", dest="success", action="store_true",
            help="The status of the commit, 'failure' if not set.")
    parser.add_argument("--pending", dest="pending", action="store_true",
            help="The status of the commit, 'failure' if not set.")
    parser.add_argument("--url", default=url,
            help="URL to associate with this status.")
    parser.add_argument("--description",
            help="A short description of the status.")
    parser.add_argument("--context", default=context,
            help="A string label to differentiate this status.")

    args = parser.parse_args()

    if not args.token:
        print("GitHub token not set. Use --token or set GITHUB_ACCESS_TOKEN.")
        sys.exit(1)

    state = "failure"
    description = "Your tests failed on MapboxCI"

    if args.success:
        state = "success"
        description = "Your tests passed on MapboxCI!"
    elif args.pending:
        state = "pending"
        description = "MapboxCI is running your tests"

    if args.description:
        description= args.description

    params = {
        "state": state,
        "target_url": args.url,
        "description": description,
        "context": args.context
    }

    SetCommitStatus(args.slug, args.hash, args.token, params)

    return 0


if __name__ == "__main__":
    Main()
