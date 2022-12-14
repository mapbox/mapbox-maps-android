#!/usr/bin/env python3

import argparse
import subprocess
import json

USES_PERMISSION = "uses-permission: name="


def load_apk_permissions(args):
    permissions = []
    shell = subprocess.Popen(f"aapt d permissions {args.apk}", stdout=subprocess.PIPE, shell=True).stdout
    for output in shell:
        line = output.decode('utf-8')
        if USES_PERMISSION in line:
            permission = line.partition(USES_PERMISSION)[2].strip()[1:-1]
            permissions.append(permission)
    permissions.sort()
    return permissions


def load_file(args):
    with open(args.file, "r") as read_file:
        return json.load(read_file)


def update_file(args, permissions):
    with open(args.file, "w") as write_file:
        json.dump(permissions, write_file, indent=4, sort_keys=True)


if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        description="Check permission change.")
    parser.add_argument("--apk", required=True,
            help="Provide the path to the APK to validate permissions.")
    parser.add_argument("--file", default="app/permission.json",
            help="File path to lookup/update stored permissions.")
    parser.add_argument("--update", default=False,
            help="Update the permission file")
    args = parser.parse_args()

    print("Utility to validate permissions of an application\n")
    print(f"Loading apk permissions {args.apk}:\n")
    permissions = load_apk_permissions(args)
    print(f"{json.dumps(permissions, indent=4)}\n")

    if args.update:
        print(f"Updating permissions file: {args.file}")
        update_file(args, permissions)
    else:
        print(f"Checking if permissions file({args.file}) matches\n")
        expected = load_file(args)
        assert expected == permissions, "Permission check failed, the permission file is outdated and you need to rerun the script with the --update true to update the baseline file"
        print("Success, exiting..")
