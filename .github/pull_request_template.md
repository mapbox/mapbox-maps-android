<!--
Thanks for submitting a pull request!
Please fill out the sections below to complete your submission.
We appreciate your contributions!
-->

### Summary of changes

<!--
• If this is a new feature, include a short summary on how to use it.
• If this is a bug fix, explain how your contribution resolves the problem.
• Include a screenshot or gif if applicable
-->

### User impact (optional)

<!--
If this PR introduces user-facing changes, please note them here.
-->


## Pull request checklist:
 - [ ] Briefly describe the changes in this PR.
 - [ ] Include before/after visuals or gifs if this PR includes visual changes.
    <!--
        | Before | After |
        | ----- | ----- |
        | <img src="" width = 250/> | <img src="" width = 250/> |
        or
        | <video src="" width = 250/> | <video src="" width = 250/> |
    -->
 - [ ] Write tests for all new functionality. If tests were not written, please explain why.
 - [ ] Optimize code for java consumption (`@JvmOverloads`, `@file:JvmName`, etc).
 - [ ] Add example if relevant.
 - [ ] Document any changes to public APIs.
 - [ ] Run `./gradlew apiDump` to update generated api files, if there's public API changes, otherwise the `verify-kotlin-binary-compatibility` CI will fail.
 - [ ] Apply changelog label ('breaking change', 'bug :beetle:', 'build', 'docs', 'feature :green_apple:', 'performance :zap:', 'testing :100:') or use the label 'skip changelog'
 - [ ] Add an entry inside this element for inclusion in the `mapbox-maps-android` changelog: `<changelog></changelog>`.
 - [ ] If this PR is a `v10.[version]` release branch fix / enhancement, merge it to `main` firstly and then port to `v10.[version]` release branch.

Fixes: < Link to related issues that will be fixed by this pull request, if they exist >

PRs must be submitted under the terms of our Contributor License Agreement [CLA](https://github.com/mapbox/mapbox-maps-android/blob/main/CONTRIBUTING.md#contributor-license-agreement).
