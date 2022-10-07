Приложение представляет собой API для функциональности покупки сертификатов.
Доступны следующие ресурсы:
1) /tag
2) /certificate

Каждый ресурс поддерживает основные HTTP методы для соответствующих операций:
1) POST (добавление)
http://localhost:8083/api/v1/tags
http://localhost:8083/api/v1/gift-certificates

2) PATCH (обновление полей)
http://localhost:8083/api/v1/tags
http://localhost:8083/api/v1/gift-certificates

3) DELETE (удаление)
http://localhost:8083/api/v1/tags/4
http://localhost:8083/api/v1/gift-certificates/5

4) GET (получение всех или одного)
http://localhost:8083/api/v1/tags
http://localhost:8083/api/v1/tags/100

http://localhost:8083/api/v1/gift-certificates
http://localhost:8083/api/v1/gift-certificates/10
