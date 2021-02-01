# Building project from source

The scripts in this folder are dedicated building this and the upstream projects from source.
This requires that you have read access to these underlying private repositories.
The scripts require you to run them from the root of the project.
A facility make target was added for your convenience.
Prior art to this script, we were using gradle project configurations and git submodules.
This iteration tries to avoid polluting the code and private repository configurations.  

### Initialization

Replace commit_sha/tag_name with an actual value of the upstream repository

> $ make build-source-init-{commit_sha/tag_name}

### Update

For testing and development purposes we also expose an update command.
This command updates local changes without cloning any upstream repositories.
It does require that you have ran build-source-init before.

> $ make build-source-update

### Clean

To make sure your build is fully clean before compiling,
you can run the command below to clear out any old references in build folders:

> $ make build-source-clean

### Removal

To restore your local project to use binary releases and cleanup the references
to the upstream repository. Execute:

> $ make build-source-remove
