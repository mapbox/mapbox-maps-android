#!/bin/bash -eo pipefail
make generate-annotation-code 2>&1 | grep -i '* Updating outdated file' && { echo 'Annotation plugin validation failed, please sync the annotation plugin code and their templates.'; exit 1; }
exit 0