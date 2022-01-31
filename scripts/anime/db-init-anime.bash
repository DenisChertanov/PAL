cd "$(dirname $0)"

for FILE_NAME in $(ls -p json | grep -v /); do

ANIME_ID=$(curl --location --request POST 'http://localhost:8081/api/private/anime/add' \
--header 'Content-Type: application/json' \
-d @json/"${FILE_NAME%.*}".json \
| tr -d " " | egrep -o  "\"AnimeId\":\".{36}\"" | cut -d ':' -f2)

curl --location --request POST 'http://localhost:8081/api/private/anime/upload-image' \
--form "animeId=${ANIME_ID}" \
--form "file=@"images/"${FILE_NAME%.*}".jpeg""

done