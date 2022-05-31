cd "$(dirname $0)"

# Регистрация обычных пользователей
for FILE_NAME in $(ls -p json/user | grep -v /); do

USER_ID=$(curl --location --request POST 'http://localhost:8081/api/public/auth/signup' \
--header 'Content-Type: application/json' \
-d @json/user/"${FILE_NAME%.*}".json \
| tr -d " " | egrep -o  "\"userId\":\".{36}\"" | cut -d ':' -f2)

UUID_USER_ID=$(echo "${USER_ID}" | cut -d '"' -f2)

USER_JWT_TOKEN=$(curl "http://localhost:8081/api/test/get-jwt/${UUID_USER_ID}")

curl --location --request POST 'http://localhost:8081/api/public/user/upload-image' \
--header "Authorization: Bearer ${USER_JWT_TOKEN}" \
--form "file=@"images/"${FILE_NAME%.*}".jpeg""

done

# Регистрация админов
for FILE_NAME in $(ls -p json/admin | grep -v /); do

ADMIN_ID=$(curl --location --request POST 'http://localhost:8081/api/public/auth/signup-admin' \
--header 'Content-Type: application/json' \
-d @json/admin/"${FILE_NAME%.*}".json \
| tr -d " " | egrep -o  "\"userId\":\".{36}\"" | cut -d ':' -f2)

UUID_ADMIN_ID=$(echo "${ADMIN_ID}" | cut -d '"' -f2)

ADMIN_JWT_TOKEN=$(curl "http://localhost:8081/api/test/get-jwt/${UUID_ADMIN_ID}")

curl --location --request POST 'http://localhost:8081/api/public/user/upload-image' \
--header "Authorization: Bearer ${ADMIN_JWT_TOKEN}" \
--form "file=@"images/"${FILE_NAME%.*}".jpeg""

done