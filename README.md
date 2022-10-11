Приложение представляет собой API для функциональности покупки сертификатов.
Доступны следующие ресурсы:
1) /tag
2) /certificate

Каждый ресурс поддерживает основные HTTP методы для соответствующих операций:
1) POST
http://localhost:8083/api/v1/tags
http://localhost:8083/api/v1/gift-certificates

2) PATCH
http://localhost:8083/api/v1/tags
http://localhost:8083/api/v1/gift-certificates

3) DELETE
http://localhost:8083/api/v1/tags/4
http://localhost:8083/api/v1/gift-certificates/5

4) GET
- ТЕГИ
- - все теги
http://localhost:8083/api/v1/tags
- - один тег по id
http://localhost:8083/api/v1/tags/100
- СЕРТИФИКАТЫ
- - все сертификаты
http://localhost:8083/api/v1/gift-certificates
- - один сертификат по id
http://localhost:8083/api/v1/gift-certificates/10
- - все сертификаты по имени тега
http://localhost:8083/api/v1/gift-certificates/tag-name/extreme
