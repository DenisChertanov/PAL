cd "$(dirname $0)"

for FILE_NAME in $(ls -p json | grep -v /); do

ANIME_ID=$(curl --location --request POST 'http://localhost:8081/api/private/anime/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhOGFkZThkYy02ZDExLTQxNzUtOGY2OS04YjY1NDU3ZTU3N2QiLCJpYXQiOjE2NDk2MDg1MDcsImV4cCI6MTY1MDQ3MjUwN30.3_NTmD0X7wYPsUoPXHCK3dTYjJUS07tqftXKm8lUmZupK6-ODrP891R6vIiBQ1tZs10TnQREtL34kR3kTViN1Q' \
--header 'Content-Type: application/json' \
-d @json/"${FILE_NAME%.*}".json \
| tr -d " " | egrep -o  "\"animeId\":\".{36}\"" | cut -d ':' -f2)

curl --location --request POST 'http://localhost:8081/api/private/anime/upload-image' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhOGFkZThkYy02ZDExLTQxNzUtOGY2OS04YjY1NDU3ZTU3N2QiLCJpYXQiOjE2NDk2MDg1MDcsImV4cCI6MTY1MDQ3MjUwN30.3_NTmD0X7wYPsUoPXHCK3dTYjJUS07tqftXKm8lUmZupK6-ODrP891R6vIiBQ1tZs10TnQREtL34kR3kTViN1Q' \
--form "animeId=${ANIME_ID}" \
--form "file=@"images/"${FILE_NAME%.*}".jpeg""

done