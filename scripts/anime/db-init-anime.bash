cd "$(dirname $0)"

ADMIN_JWT_TOKEN=$(curl 'http://localhost:8081/api/test/get-jwt/a8ade8dc-6d11-4175-8f69-8b65457e577d')

#Проход по всем файлам с аниме
for FILE_NAME in $(ls -p json | grep -v /); do

#Добавление нового аниме через приватную ручку бэка
ANIME_ID=$(curl --location --request POST 'http://localhost:8081/api/private/anime/add' \
--header "Authorization: Bearer ${ADMIN_JWT_TOKEN}" \
--header 'Content-Type: application/json' \
-d @json/"${FILE_NAME%.*}".json \
| tr -d " " | egrep -o  "\"animeId\":\".{36}\"" | cut -d ':' -f2)

#Добавление картинки ко всем аниме
curl --location --request POST 'http://localhost:8081/api/private/anime/upload-image' \
--header "Authorization: Bearer ${ADMIN_JWT_TOKEN}" \
--form "animeId=${ANIME_ID}" \
--form "file=@"images/"${FILE_NAME%.*}".jpeg""

#Отправка на ручку бэка сообщения о просмотренности аниме
#EPISODES=$(cat json/"${FILE_NAME%.*}".json | tr -d " " | grep Episodes | cut -d ':' -f2 | cut -d ',' -f1)
#UUID_ANIME_ID=$(echo "${ANIME_ID}" | cut -d '"' -f2)
#curl http://localhost:8081/api/private/anime-activity/update-last-watched-episode/"${UUID_ANIME_ID}"/"${EPISODES}" \
#--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhOGFkZThkYy02ZDExLTQxNzUtOGY2OS04YjY1NDU3ZTU3N2QiLCJpYXQiOjE2NTA4NTI2NDYsImV4cCI6MTY3Njc3MjY0Nn0.cyZv7urF4l1htGTN7eY6DQuRCL_tWDe4-iG3O2NuzovZsOrW8hUqm6WNY6uA3QLp-hNU_j-ioPC3cG2jOuEqqg'

done

# Инициализация просмотров аниме для пользователей
curl http://localhost:8081/api/test/initialize-all-users

# Создание плейлиста для админа
curl --location --request POST 'http://localhost:8081/api/test/create-playlist' \
--form "userId=a8ade8dc-6d11-4175-8f69-8b65457e577d" \
--form "image=@"anime-logo.jpeg""