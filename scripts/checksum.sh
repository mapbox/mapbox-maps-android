#!/bin/bash
RESULT_FILE=$1
FILES=()

if [ -f $RESULT_FILE ]; then
  rm $RESULT_FILE
fi

FILE="`basename "${RESULT_FILE}"`"
DIR="`dirname "${RESULT_FILE}"`"
mkdir -p "${DIR}" && touch "${DIR}/${FILE}"

add_files() {
    find $1 -type f -name $2  -print0 >tmpfile
    while IFS=  read -r -d $'\0'; do
        FILES+=("$REPLY")
    done <tmpfile
    rm -f tmpfile
}

checksum_file() {
    echo `openssl md5 $1 | awk '{print $2}'`
}

add_files . build.gradle.kts
add_files ./buildSrc/src/main/kotlin/ Project.kt

for FILE in ${FILES[@]}; do
	echo `checksum_file $FILE` >> $RESULT_FILE
done

sort $RESULT_FILE -o $RESULT_FILE

echo "New hash sum file:"
cat $RESULT_FILE