#!/usr/bin/python

from subprocess import call
from subprocess import Popen, PIPE

## Run license generation
call('python scripts/license-generate.py', shell=True)

## Git diff changes
p = Popen(['git', 'diff', 'LICENSE.md'], stdin=PIPE, stdout=PIPE, stderr=PIPE)
output, err = p.communicate(b"input data that is passed to subprocess' stdin")
if b"LICENSE.md" in output:
   print(output)
   raise ValueError("""An error ocurred while validating the license generation. 
            Changes were detected to the license generation output 
            but weren't commited. Run python scripts/license-generate.py and 
            commit the changeset to make this validation pass.""")
else:
   print("License file is up to date")
