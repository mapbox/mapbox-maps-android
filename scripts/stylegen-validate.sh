#!/bin/bash -eo pipefail
make generate-style-code 2>&1 | grep -i '* Updating outdated file' && { echo 'stylegen validation failed, please sync the style code and their templates.'; exit 1; }
make generate-config-code 2>&1 | grep -i '* Updating outdated file' && { echo 'stylegen validation failed, please sync the style code and their templates.'; exit 1; }
exit 0