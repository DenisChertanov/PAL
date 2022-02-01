cd "$(dirname $0)"

for FILE_NAME in $(ls -p json | grep -v /); do

ANIME_ID=$(curl --location --request POST 'http://localhost:8081/api/private/anime/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2ZGMwM2JhNy1mNmYyLTQwNzEtYWU1MC02MWIzNmNmMzBmMTAiLCJpYXQiOjE2NDM2Njc3MjIsImV4cCI6MTY0Mzc1NDEyMn0.ofDWsD5crslE0gzJZ5Y849dhb4k94iQ_nk2LxVWAxMqor0WgFpB1wuC-HIiS7Hbj6q0syp5604RTnro6n4_FGg' \
--header 'Content-Type: application/json' \
-d @json/"${FILE_NAME%.*}".json \
| tr -d " " | egrep -o  "\"AnimeId\":\".{36}\"" | cut -d ':' -f2)

curl --location --request POST 'http://localhost:8081/api/private/anime/upload-image' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2ZGMwM2JhNy1mNmYyLTQwNzEtYWU1MC02MWIzNmNmMzBmMTAiLCJpYXQiOjE2NDM2Njc3MjIsImV4cCI6MTY0Mzc1NDEyMn0.ofDWsD5crslE0gzJZ5Y849dhb4k94iQ_nk2LxVWAxMqor0WgFpB1wuC-HIiS7Hbj6q0syp5604RTnro6n4_FGg' \
--form "animeId=${ANIME_ID}" \
--form "file=@"images/"${FILE_NAME%.*}".jpeg""

done