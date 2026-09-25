.PHONY: check-api
check-api:
	./gradlew checkApi

.PHONY: update-api
update-api:
	./gradlew updateApi

.PHONY: fix
fix:
	./gradlew ktlintFormat

.PHONY: check
check:
	./gradlew ktlint lint

.PHONY: test
test:
	./gradlew test -i

.PHONY: clean
clean:
	./gradlew clean