Приложение представляет собой API для функциональности получения сертификатов.
Доступны следующие ресурсы:
1) tag http://localhost:8083/api/v1/tags
2) certificate http://localhost:8083/api/v1/gift-certificates

Каждый ресурс поддерживает основные HTTP методы для соответствующих операций:
1) POST
http://localhost:8083/api/v1/tags * http://localhost:8083/api/v1/gift-certificates

2) PATCH
http://localhost:8083/api/v1/tags * http://localhost:8083/api/v1/gift-certificates

3) DELETE
http://localhost:8083/api/v1/tags/4 * http://localhost:8083/api/v1/gift-certificates/5

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
- - все сертификаты по имени одного тега
http://localhost:8083/api/v1/gift-certificates/tag-name/extreme
- - все сертификаты:
http://localhost:8083/api/v1/gift-certificates/parameters
- - - по имени одного тега (переменная запроса - tagName) и/или
- - - по части имени сертификата (переменная запроса - partOfName) и/или
- - - по части описания (переменная запроса - partOfDescription)
- - - - с сортировкой по имени / без нее (переменная запроса - sortByName)
- - - - с сортировкой по возрастанию / убыванию (переменная запроса - sortAscending)
