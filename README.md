Приложение представляет собой API для функциональности получения сертификатов.
Доступны следующие ресурсы:
1) tags http://localhost:8083/api/v1/tags
2) gift certificates http://localhost:8083/api/v1/gift-certificates
3) users http://localhost:8083/api/v1/users
4) orders http://localhost:8083/api/v1/orders

Каждый ресурс поддерживает основные HTTP методы для соответствующих операций:

## Запросы для тегов:
1) GET
- все теги: http://localhost:8083/api/v1/tags
- один тег по id: http://localhost:8083/api/v1/tags/100
- самый используемый тег с самой высокой стоимостью заказов: http://localhost:8083/api/v1/tags/100

2) POST
- http://localhost:8083/api/v1/tags
- - JSON example: 
{
        "name": "new tag name"
	}

3) PATCH
- http://localhost:8083/api/v1/tags/34
- - JSON example: 
{
        "name": "new name to update tag name"
    }

4) DELETE
- http://localhost:8083/api/v1/tags/4
- - path example: http://localhost:8083/api/v1/tags/11



## Запросы для сертификатов:
1) GET
- все сертификаты: http://localhost:8083/api/v1/gift-certificates
- один сертификат по id: http://localhost:8083/api/v1/gift-certificates/10
- все сертификаты по имени одного тега: http://localhost:8083/api/v1/gift-certificates/tag-name/extreme
- все сертификаты: http://localhost:8083/api/v1/gift-certificates/parameters 
- с поиском по: 
- - имени одного тега: переменная запроса - tagName;
- - части имени сертификата: переменная запроса - partOfName (и/или)
- - части описания: переменная запроса - partOfDescription
- с сортировкой
- - по имени / без нее (переменная запроса - sortByName)
- - по возрастанию / убыванию (переменная запроса - sortAscending)
- path example: http://localhost:8083/api/v1/gift-certificates/parameters?tagName=sport&partOfName=al&partOfDescription=free&sortByName=true&sortAscending=true

2) POST
- http://localhost:8083/api/v1/gift-certificates
- - JSON example:
{
    "name": "test name",
    "description": "test description",
    "price": 10.10,
    "duration": 10,
    "tags": [
        {
            "name": "new tag name 1"
        },
        {
            "name": "new tag name 2"
        }
    ]
}

3) PATCH
- http://localhost:8083/api/v1/gift-certificates/5
- - JSON example:
{
    "name": "new certificate name",
    "description": "new certificate description",
    "tags": [
        {
            "name": "new tag name 1"
        },
        {
            "name": "new tag name 2"
        }
    ]
}
- изменить только имя: http://localhost:8083/api/v1/gift-certificates/names/5
- - JSON example:
{
    "name": "new certificate name"
}
- изменить только описание: http://localhost:8083/api/v1/gift-certificates/descriptions/5
- - JSON example:
{
    "description": "test description_UPD"
}
- изменить только цену: http://localhost:8083/api/v1/gift-certificates/prices/5
- - JSON example:
{
    "price": 10.10
}
- изменить только срок действия: http://localhost:8083/api/v1/gift-certificates/names/5
- - JSON example:
{
    "duration": 10
}

4) DELETE
- http://localhost:8083/api/v1/gift-certificates/5
- - path example: http://localhost:8083/api/v1/gift-certificates/61

## Запросы для пользователей:
1) GET
- все пользователи: http://localhost:8083/api/v1/users
- пользователь по id: http://localhost:8083/api/v1/users/12
- пользователь по nick-name: http://localhost:8083/api/v1/users/

## Запросы для заказов:
1) GET
- все заказы: http://localhost:8083/api/v1/orders
- заказ по id: http://localhost:8083/api/v1/orders/1
- заказ по id заказа и id пользователя: http://localhost:8083/api/v1/orders/users
- - path example: http://localhost:8083/api/v1/orders/users/?userId=2&orderId=6
- все заказы по id пользователя: http://localhost:8083/api/v1/orders
- все заказы по nick-name пользователя: http://localhost:8083/api/v1/orders/nick-name/user1

2) POST
- новый заказ по id сертификата и id пользователя
- http://localhost:8083/api/v1/orders
- path example: http://localhost:8083/api/v1/orders?userId=2&certificateId=4
* новый заказ через dto 
- http://localhost:8083/api/v1/orders/dtos
- - JSON example:
{
"user": {
        "id": 2,
        "nickName": "user2",
        "userName": "tim simpson"
    },
    "certificate": {
        "id": 3,
        "name": "AirClub Big Plane",
        "description": "one free flight",
        "price": 33.33,
        "duration": 33,
        "tags": [
            {
                "id": 3,
                "name": "extreme"
            }
        ]
    }
}