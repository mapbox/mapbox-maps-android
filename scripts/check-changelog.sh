#!/usr/bin/env bash

set -Eeuxo pipefail

if [ $(git rev-parse origin/main) == $(git rev-parse HEAD) ]; then
    echo "No new changes, skipping changelog check."
    exit 0
fi

if ! command -v gh &> /dev/null
then
    echo "gh (github cli tool) is not found, install it first"
    exit
fi

gh auth login --with-token < ./gh_token.txt

pr_number=${CIRCLE_PULL_REQUEST:51}
echo "$pr_number"

skip_changelog_prs=$(gh pr list --label "skip changelog")
if grep -q "$pr_number" <<< "$skip_changelog_prs"; then
  echo "$pr_number has skip changelog label"
  exit 0
fi

changelog_diff=$(git diff origin/main..HEAD CHANGELOG.md extension-androidauto/CHANGELOG.md extension-compose/CHANGELOG.md)

if [[ -z "$changelog_diff" ]]; then
    echo "No changes in CHANGELOG.md found"
    exit 1
fi

echo "All good. You have modified CHANGELOG.md"