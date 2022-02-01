cd "$(dirname $0)"

for FILE_NAME in $(ls -p json | grep -v /); do

curl --location --request POST 'http://localhost:8081/api/public/auth/signup' \
--header 'Content-Type: application/json' \
-d @json/"${FILE_NAME%.*}".json

done