# Лабораторные работы по вебу

### ОТЧЁТЫ ПО ЛАБОРАТОРНЫМ РАБОТАМ (№1, №2)

> Выполнил:
> студентка группы ИУ7-68Б(В) \
> Макеева Е.Д. \
> Преподаватель: Бекасов Д. Е. \
> Москва, 2021 г.

<details>
<summary>Изучение протокола HTTP</summary>

**1. Базовая часть работы**\
**1.2. В качестве ПО для тестирования запросов был выбран - Postman. Также в некоторых случаях было проверено на Insomnia.\
1.2.1. Запрос OPTIONS**

Запрос используется для определения возможностей веб-сервера или параметров соединения для конкретного ресурса. В ответ серверу следует включить заголовок Allow со списком поддерживаемых методов. Также в заголовке ответа может включаться информация о поддерживаемых расширениях.\
При тестировании различных интернет-сервисов удалось выяснить, что:
1) При отправке запроса OPTIONS на http://mail.ru/ приходит ответ с кодом 200 OK (успешный ответ), однако без информации о разрешенных методах (если не использовать заголовок Host - ответом будет с кодом 400 Bad Request (сервер обнаружил в запросе клиента синтаксическую ошибку)).
2) Запрос на https://ya.ru/ прислал ответ с кодом 403 Forbidden (сервер понял запрос, но он отказывается его выполнять из-за ограничений в доступе для клиента к указанному ресурсу)  и телом ответа с HTML контентом с сообщением об ошибке ("Произошла ошибка на сервере"), без информации о разрешенных методах. 
3) Запрос на www.rambler.ru/ также прим 200 OK, без информации о разрешенных методах (без заголовка Host приходит ответ с кодом 403 Forbidden без информации о разрешенных методах)
4) Запрос на https://www.google.ru прислал ответ с кодом 405 Method not allowed, что означает, что эту ошибку сервер должен возвращать, если метод ему известен, но он не применим именно к указанному в запросе ресурсу. Информации о разрешенных методах в заголовках ответа:

| NAME | VALUE |
| ------ | ------ |
| Allow | GET, HEAD |

5) Запрос на https://github.com/ вернул ответ с кодом 404 Not Found (самая распространённая ошибка при пользовании Интернетом, основная причина — ошибка в написании адреса Web-страницы. Сервер понял запрос, но не нашёл соответствующего ресурса по указанному URL). Без информации о разрешенных методах.
6) Запрос на www.apple.com/ вернул успешный ответ 200 OK без информации о разрешенных методах (однако если кидать запрос через инсомнию вернется ответ с кодом 200 OK и заголовком allow - GET,HEAD,POST,OPTIONS)

**1.2.2. Запрос HEAD**\
Аналогичен методу GET, за исключением того, что в ответе сервера отсутствует тело. Запрос HEAD обычно применяется для извлечения метаданных, проверки наличия ресурса (валидация URL) и чтобы узнать, не изменился ли он с момента последнего обращения.
Заголовки ответа могут кэшироваться. При несовпадении метаданных ресурса с соответствующей информацией в кэше — копия ресурса помечается как устаревшая.
1) Запрос на https://vk.com/ вернут ответ с кодом 418 I’m a teapot (Этот код был введен в 1998 году как одна из традиционных первоапрельских шуток IETF в RFC 2324, Hyper Text Coffee Pot Control Protocol. Не ожидается, что данный код будет поддерживаться реальными серверами). В заголовках ответа также содержится такой заголовок как content-length со значением 0.
2) Запрос на www.apple.com/ вернул ответ с кодом 200 OK, где можно увидеть в заголовках ответа такой заголовок как content-length, что означает размер содержимого сущности в байтах. (content-length: 73084)
3) Запрос www.msn.com/ вернул ответ с кодом 200 ОК, где можно увидеть в заголовках ответа такой заголовок как content-length, что означает размер содержимого сущности в байтах. (Content-Length: 59030)

**1.2.3. Запросы GET и POST**\
GET Используется для запроса содержимого указанного ресурса. С помощью метода GET можно также начать какой-либо процесс. В этом случае в тело ответного сообщения следует включить информацию о ходе выполнения процесса.\
Клиент может передавать параметры выполнения запроса в URI целевого ресурса после символа «?»\
Тестирование GET запросов:
1) Запрос на yandex.ru вернул ответ с кодом 200 OK и телом ответа с HTML и JS.
2) Запрос на https://google.com/ вернул ответ с кодом 200 OK и телом ответа с HTML и JS.
3) Запрос на apple.com вернул ответ с кодом 200 OK и телом ответа с HTML и JS.

POST Применяется для передачи пользовательских данных заданному ресурсу. При этом передаваемые данные включаются в тело запроса. Аналогично с помощью метода POST обычно загружаются файлы на сервер.\
Тестирование POST запросов:
1) Запрос на yandex.ru вернул ответ с кодом 403 Forbidden, телом ответа с  HTML и JS и с сообщением об ошибке "Произошла ошибка на сервере". 
2) Запрос на google.com вернул ответ с кодом 405 Method Not Allowed, телом ответа с HTML и с сообщением об ошибке "The request method POST is inappropriate for the URL". 
3) Запрос на apple.com вернул ответ с кодом 200 OK, телом ответа с HTML и JS стартовой страницы.

**1.3. Работа с api сайта\
1.3.2.1. Получите список всех факультетов МГТУ им. Н.Э.Баумана.**

Для начала необходимо узнать id университета по GET запросу database.getUniversities:
`https://api.vk.com/method/database.getUniversities?q=МГТУ&country_id=1&city_id=1&access_token={{token}}&v=5.130`
Параметры для запроса: 

| NAME | VALUE |
| ------ | ------ |
| q | строка поискового запроса |
| access_token | токен для работы с API |
| country_id | идентификатор страны, учебные заведения которой необходимо вернуть |
| city_id | идентификатор города, учебные заведения которого необходимо вернуть |
| offset | отступ, необходимый для получения определенного подмножества учебных заведений |
| count | количество учебных заведений, которое необходимо вернуть |

Получаем ответ с кодом 200 OK, телом ответа в формате JSON с университетами
```
{
  "response": {
    "count": 5,
    "items": [
      {
        "id": 248,
        "title": "МГТУ им. А. Н. Косыгина (бывш. МГТА им. А. Н. Косыгина, МТИ)"
      },
      {
        "id": 249,
        "title": "МГТУ ГА"
      },
      {
        "id": 250,
        "title": "МГТУ им. Н. Э. Баумана"
      },
      {
        "id": 252,
        "title": "МГТУ «Станкин»"
      },
      {
        "id": 169759,
        "title": "ИСОТ МГТУ им. Н. Э. Баумана (бывш. МИПК МГТУ им. Н. Э. Баумана)"
      }
    ]
  }
}
```

Далее запрашиваем список факультетов по id университета, используя GET запрос database.getFaculties
`https://api.vk.com/method/database.getFaculties?access_token={{token}}&university_id=250&count=200&v=5.130`

Параметры для запроса: 

| NAME | VALUE |
| ------ | ------ |
| university_id | идентификатор университета, факультеты которого необходимо получить |
| access_token | токен для работы с API |
| offset | отступ, необходимый для получения определенного подмножества факультетов |
| count | количество факультетов которое необходимо получить |

```
{
  "response": {
    "count": 20,
    "items": [
      {
        "id": 1031,
        "title": "Аэрокосмический факультет"
      },
      {
        "id": 1032,
        "title": "Факультет инженерного бизнеса и менеджмента"
      },
      {
        "id": 1033,
        "title": "Факультет информатики и систем управления"
      },
      {
        "id": 1034,
        "title": "Факультет машиностроительных технологий"
      },
      {
        "id": 1035,
        "title": "Факультет оптико-электронного приборостроения"
      },
      {
        "id": 1036,
        "title": "Приборостроительный факультет"
      },
      {
        "id": 1037,
        "title": "Радиотехнический факультет"
      },
      {
        "id": 1038,
        "title": "Факультет радиоэлектроники и лазерной техники"
      },
      {
        "id": 1039,
        "title": "Факультет ракетно-космической техники"
      },
      {
        "id": 1040,
        "title": "Факультет робототехники и комплексной автоматизации"
      },
      {
        "id": 1041,
        "title": "Факультет специального машиностроения"
      },
      {
        "id": 1042,
        "title": "Факультет фундаментальных наук"
      },
      {
        "id": 1043,
        "title": "Факультет энергомашиностроения"
      },
      {
        "id": 1044,
        "title": "Кафедра юриспруденции, интеллектуальной собственности и судебной экспертизы"
      },
      {
        "id": 1803,
        "title": "Факультет биомедицинской техники"
      },
      {
        "id": 1804,
        "title": "Факультет социально-гуманитарных наук"
      },
      {
        "id": 56430,
        "title": "Факультет лингвистики"
      },
      {
        "id": 56431,
        "title": "Физкультурно-оздоровительный факультет"
      },
      {
        "id": 2071503,
        "title": "Головной учебно-исследовательский и методический центр (ГУИМЦ)"
      },
      {
        "id": 2183736,
        "title": "Факультет военного обучения (Военный институт)"
      }
    ]
  }
}
```

**1.3.2.2. Получить свою аватарку**\
Для получения аватарки пользователя был отправлен GET запрос users.get с fields=photo_max_orig
`https://api.vk.com/method/users.get?fields=photo_max_orig&access_token={{token}}&v=5.130`

Параметры для запроса: 

| NAME | VALUE |
| ------ | ------ |
| user_ids | перечисленные через запятую идентификаторы пользователей или их короткие имена (screen_name). По умолчанию — идентификатор текущего пользователя |
| access_token | токен для работы с API |
| fields | список дополнительных полей профилей, которые необходимо вернуть |
| name_case | падеж для склонения имени и фамилии пользователя |

В результате был получен ответ с кодом 200 OK, телом запроса в формате JSON:
```
{
  "response": [
    {
      "first_name": "Elena",
      "id": 197729023,
      "last_name": "Makeeva",
      "can_access_closed": true,
      "is_closed": false,
      "photo_max_orig": "https:\/\/sun1-15.userapi.com\/s\/v1\/if1\/ut7s6CswzgCW1EmaABLKFtG7LNVt79NypuAvGLEau5vIXKisQGVO8plY2nKB65s_kh5YRfcC.jpg?size=400x0&quality=96&crop=432,0,1669,1669&ava=1"
    }
  ]
}
```

**1.3.2.3. Ответьте на вопросы:**\
Какой код ответа присылается от api?
> 200 ОК. Успешный ответ во всех случаях

Что содержит тело ответа? 
> Ответ с информацией в формате JSON

В каком формате и какой кодировке содержаться данные?
> Content-type: application/json; charset=utf-8 - формат JSON, кодировка utf-8

Какой веб-сервер отвечает на запросы? 
> Веб-сервер kittenx

Какая версия протокола HTTP используется?
> HTTP/1.1

**1.3.3.  POST запросы VK API**\
**1.3.3.1. Отправьте запись на стену любому пользователю/группе и убедитесь, что она пришла.**\
Был отправлен запрос wall.post для публикации запись на стену через форму, встроенную в документацию api.  Для просмотра запроса использовалась Консоль разработчика - Вкладка "Network".
```
{
"response": {
"post_id": 1241
}
}
```

**1.3.3.2. Ответьте на вопрос:**

Каким образом передаются данные от пользователя к серверу в POST-запросах?
> Метод запроса POST предназначен для запроса, при котором веб-сервер принимает данные, заключённые в тело сообщения, для хранения. В рамках POST запроса произвольное количество данных любого типа может быть отправлено на сервер в теле сообщения запроса. Поля заголовка в POST-запросе обычно указывают на тип содержимого.

> В VK API в POST запросах данные передаются с _content-type: application/x-www-form-urlencoded_, где значения кодируются в кортежах с ключом, разделенных символом '&', с '=' между ключом и значением. _Примечание: Тело было взято из запроса через Консоль разработчика - Вкладка "Network"_. Пример тела данного запроса:

`act=a_run_method&al=1&hash=1615143628%3A031a951f4120f10888&method=wall.post&param_close_comments=0&param_friends_only=1&param_from_group=0&
param_mark_as_ads=0&param_message=%D1%82%D0%B5%D1%81%D1%82%20API%20%D0%B2%D0%BA&param_mute_notifications=0&param_owner_id=197729023&param_signed=0&
param_v=5.130`

**2. Реализуйте небольшое серверное приложение, с использованием любого фреймворка. Лучшего всего для этой цели подойдет NodeJS: решение получится очень компактным и простым.**\
Сервер должен содержать предоставлять некоторое REST API с поддержкой (GET, POST, DELETE, PUT, OPTION). Данные отправлять в формате json. Конкретное содержание запросов - на ваше усмотрение. Подключите фантазию. (Можно сделать простейший CRUD-сервис с хранением данных в RAM). Спроектированный REST API должен соответствовать принципам проектирования REST.

Серверное приложение было реализовано с помощью Node.JS, фреймворка Express и [Cloud MongoDB](https://cloud.mongodb.com).\
[App](https://git.iu7.bmstu.ru/iu7-second-degree/web-labs-2021/web-labs-2021-elena-makeeva/-/tree/lab1/lab01)\
Добавленные запросы:
| Тип запроса | Запрос | Описание |
| ------ | ------ | ------ |
| GET | /serials | Возвращает список сериалов из БД |
| GET | /serials/:id | Возвращает сериал по указанному идентификатору |
| POST | /serials | Добавление сериала. В теле запроса указывается объект в формате JSON* |
| DELETE | /serials/:id | Удаление сериала по указанному идентификатору |
| PUT | /serials/:id | Изменение сериала по указанному идентификатору. В теле запроа указывается объект в формате JSON* |
| OPTIONS | / | Определения возможностей веб-сервера. Возвращение заголовка Allow. |

* Пример JSON запроса
```
{
	"name":"Роковой патруль",
	"info":"Разбившийся в аварии гонщик, получивший заряд радиации пилот, девушка с 64 личностями, изуродованная актриса, парень-киборг и во главе этого всего - сумасшедший учёный. Эти неудачники и жалкие ничтожества становятся супергероями. Они находят своим сверхспособностям применение, а себе — новый смысл жизни. Теперь они — Роботмен, Негативный Человек, Безумная Джейн, Эласти-гёрл и Киборг."
}
```

**3. Доп. задание. Статика и маршрутизация.**
> 3.1.   Добавьте папку static (классическое название для статически раздаваемой папки).
> 3.2.   В папке static создайте папки html и img.
> 3.3.   В папке static/html создайте файл index.html со следующим содержанием (или любым другим):
```
> <head></head>
> <body>
> <h1>Hello, world!</h1>
> <img src=”/img/image.jpg”>
> </body>
```
> 3.3.   Настройте сервер так, чтобы при запросе из браузера отображалась эта страница.
> 3.4. Настройте routing (маршрутизацию) на вашем сервере. Например, чтобы путь /hack тоже отдавал файл index.html, а путь /, по умолчанию отдающий index, выдавал дополнительную страницу hack.html.
>  3.5. Переименуйте hack.html (содержащую теги html) в hack.txt. Что изменилось? Почему? Как сделать так, чтобы страница отображалась корректно?

Была добавлена папка [static](https://git.iu7.bmstu.ru/iu7-second-degree/web-labs-2021/web-labs-2021-elena-makeeva/-/tree/lab1/lab01/static), в которой также присутствуют папки [html](https://git.iu7.bmstu.ru/iu7-second-degree/web-labs-2021/web-labs-2021-elena-makeeva/-/tree/lab1/lab01/static/html) и [img](https://git.iu7.bmstu.ru/iu7-second-degree/web-labs-2021/web-labs-2021-elena-makeeva/-/tree/lab1/lab01/static/img).

Были также созданы [index.html](https://git.iu7.bmstu.ru/iu7-second-degree/web-labs-2021/web-labs-2021-elena-makeeva/-/blob/lab1/lab01/static/html/index.html) и hack.html. Сервер был настроен так, что при запросе из браузера отображалась страница index.html. По ТЗ был настроен routing так, чтобы путь /hack отдавал [index.html](https://git.iu7.bmstu.ru/iu7-second-degree/web-labs-2021/web-labs-2021-elena-makeeva/-/blob/lab1/lab01/static/html/index.html), а путь / - hack.html.
Далее, согласно заданию, файл hack.html был переименован в [hack.txt](https://git.iu7.bmstu.ru/iu7-second-degree/web-labs-2021/web-labs-2021-elena-makeeva/-/blob/lab1/lab01/static/html/hack.txt) 

**Что изменилось? Почему? Как сделать так, чтобы страница отображалась корректно?**\
После изменения формата в браузере стал отображаться код HTML в виде текста. По умолчанию формат .txt сопоставляется с text/plain, именно по этой причине браузер показывает код файла, а не корректное отображение страницы. Для корректного отображения страницы в таком формате следует указать заголовок "Content-type" - "text/html".
</details>

<details>
<summary>Знакомство с nginx</summary>

**Для тестирования сервера была использована утилита ApacheBenchmark**

**1. Замерьте скорость отдачи контента на сервере из лабораторной работы №1 (отдача страниц, картинки, запросов к api). Добавьте логирование приходящих запросов.**

Для логирования было использован Express logger (middleware).

Было замерено скорость отдачи контента:

Тестирование с помощью AB:

Страница /:
> ab -c 10 -n 100 http://127.0.0.1:8000/ \
This is ApacheBench, Version 2.3 <$Revision: 1843412 $> \
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/ \
Licensed to The Apache Software Foundation, http://www.apache.org/
>
>Benchmarking 127.0.0.1 (be patient).....done
>
>Server Software:        
Server Hostname:        127.0.0.1 \
Server Port:            8000
>
>Document Path:          / \
Document Length:        261 bytes
>
>Concurrency Level:      10 \
Time taken for tests:   0.133 seconds \
Complete requests:      100 \
Failed requests:        0 \
Total transferred:      54900 bytes \
HTML transferred:       26100 bytes \
Requests per second:    749.31 [#/sec] (mean) \
Time per request:       13.346 [ms] (mean) \
Time per request:       1.335 [ms] (mean, across all concurrent requests) \
Transfer rate:          401.73 [Kbytes/sec] received
>
>Connection Times (ms) \
min  mean[+/-sd] median   max \
Connect:        0    0   0.1      0       1 \
Processing:     3   13   3.0     12      20 \
Waiting:        2   11   2.5     10      18 \
Total:          3   13   3.0     12      20
>
>Percentage of the requests served within a certain time (ms) \
50%     12 \
66%     13 \
75%     14 \
80%     15 \
90%     19 \
95%     19 \
98%     20 \
99%     20 \
100%     20 (longest request)

Страница /hack:

>Server Software:        \
Server Hostname:        127.0.0.1 \
Server Port:            8000
>
>Document Path:          /hack \
Document Length:        256 bytes \
>
>Concurrency Level:      10 \
Time taken for tests:   0.144 seconds \
Complete requests:      100 \
Failed requests:        0 \
Total transferred:      54400 bytes \
HTML transferred:       25600 bytes \
Requests per second:    696.41 [#/sec] (mean) \
Time per request:       14.359 [ms] (mean) \
Time per request:       1.436 [ms] (mean, across all concurrent requests) \
Transfer rate:          369.97 [Kbytes/sec] received
>
>Connection Times (ms) \
min  mean[+/-sd] median   max \
Connect:        0    0   0.1      0       1 \
Processing:     6   13   2.2     13      19 \
Waiting:        5   12   2.1     12      19 \
Total:          6   14   2.2     13      19
>
>Percentage of the requests served within a certain time (ms) \
50%     13 \
66%     14 \
75%     15 \
80%     15 \
90%     16 \
95%     19 \
98%     19 \ 
99%     19 \
100%     19 (longest request)

Тестирование отдачи картинок /img/main.gif, /img/extra.gif:

main.gif:
>Server Software:        \
Server Hostname:        127.0.0.1 \
Server Port:            8000 
>
>Document Path:          /img/main.gif \
Document Length:        887057 bytes
>
>Concurrency Level:      10 \
Time taken for tests:   0.396 seconds \
Complete requests:      100 \
Failed requests:        0 \
Total transferred:      88733500 bytes \
HTML transferred:       88705700 bytes \
Requests per second:    252.38 [#/sec] (mean) \
Time per request:       39.623 [ms] (mean) \
Time per request:       3.962 [ms] (mean, across all concurrent requests) \
Transfer rate:          218697.94 [Kbytes/sec] received
>
>Connection Times (ms) \ 
min  mean[+/-sd] median   max \
Connect:        0    0   0.1      0       1 \
Processing:    25   39   7.9     39      56 \
Waiting:        3   11   3.2     11      23 \
Total:         25   39   7.9     40      56 
>
>Percentage of the requests served within a certain time (ms) \
50%     40 \
66%     42 \
75%     43 \
80%     45 \
90%     51 \
95%     53\
98%     54\
99%     56\
100%     56 (longest request)

extra.gif

>Server Software:        \
Server Hostname:        127.0.0.1 \
Server Port:            8000
>
>Document Path:          /img/extra.gif \
Document Length:        235052 bytes
>
>Concurrency Level:      10 \
Time taken for tests:   0.174 seconds \
Complete requests:      100 \
Failed requests:        0 \
Total transferred:      23533000 bytes \
HTML transferred:       23505200 bytes \
Requests per second:    573.58 [#/sec] (mean) \
Time per request:       17.434 [ms] (mean) \
Time per request:       1.743 [ms] (mean, across all concurrent requests) \
Transfer rate:          131817.42 [Kbytes/sec] received
>
>Connection Times (ms) \
min  mean[+/-sd] median   max \
Connect:        0    0   0.3      0       2 \
Processing:     8   16   3.2     16      26 \
Waiting:        5    9   2.3      8      20 \
Total:          8   17   3.2     16      26
>
>Percentage of the requests served within a certain time (ms) \
50%     16\
66%     17\
75%     18\
80%     19\
90%     21\
95%     25\
98%     26\
99%     26\
100%     26 (longest request)

Тестирование API /serials:

>Server Software:     \   
Server Hostname:        127.0.0.1\
Server Port:            8000
>
>Document Path:          /serials \
Document Length:        6529 bytes
>
>Concurrency Level:      10 \
Time taken for tests:   4.721 seconds \
Complete requests:      100\
Failed requests:        0\
Total transferred:      673300 bytes\
HTML transferred:       652900 bytes\
Requests per second:    21.18 [#/sec] (mean)\
Time per request:       472.061 [ms] (mean)\
Time per request:       47.206 [ms] (mean, across all concurrent requests)\
Transfer rate:          139.29 [Kbytes/sec] received
>
>Connection Times (ms) \
min  mean[+/-sd] median   max \
Connect:        0    0   0.2      0       1 \
Processing:   149  455 706.8    163    3234\
Waiting:      148  451 707.2    159    3231\
Total:        149  455 706.8    163    3234
>
>Percentage of the requests served within a certain time (ms) \
50%    163\
66%    163\
75%    164\
80%    171\
90%   1722\
95%   2471\
98%   3077\
99%   3234\
100%   3234 (longest request)

Также в логах можно наблюдать скорость отдачи контента:

Отдача страниц ( '/', '/hack' )
> {"time":"2021-03-21T20:54:55.895Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /"} \
{"time":"2021-03-21T20:54:55.906Z","lvl":"INFO","msg":"Response with status 200 in 12 ms."} 

> {"time":"2021-03-21T20:57:05.343Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /hack/"} \
{"time":"2021-03-21T20:57:05.352Z","lvl":"INFO","msg":"Response with status 200 in 10 ms."}

Отдача картинок ( '/img/extra.gif',  '/img/main.gif' )
> {"time":"2021-03-21T20:57:05.450Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /img/main.gif"} \
{"time":"2021-03-21T20:57:05.451Z","lvl":"INFO","msg":"Response with status 304 in 4 ms."}

> {"time":"2021-03-21T20:57:05.450Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /img/main.gif"} \
{"time":"2021-03-21T20:57:05.451Z","lvl":"INFO","msg":"Response with status 304 in 9 ms."}

Отдача запросов api
> {"time":"2021-03-21T20:57:41.097Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /serials"} \
{"time":"2021-03-21T20:57:41.262Z","lvl":"INFO","msg":"Response with status 200 in 166 ms."}

> {"time":"2021-03-21T20:58:04.423Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /serials/6046712863b94aedfb0dcdf3"} \
{"time":"2021-03-21T20:58:04.573Z","lvl":"INFO","msg":"Response with status 200 in 150 ms."}

> {"time":"2021-03-21T20:58:51.707Z","lvl":"INFO","msg":"Request from 127.0.0.1: PUT /serials/6046705e896927edcd2760a2"} \
{"time":"2021-03-21T20:58:51.903Z","lvl":"INFO","msg":"Response with status 200 in 196 ms."}

> {"time":"2021-03-21T21:00:09.956Z","lvl":"INFO","msg":"Request from 127.0.0.1: POST /serials"} \
{"time":"2021-03-21T21:00:10.147Z","lvl":"INFO","msg":"Response with status 200 in 191 ms."}

> {"time":"2021-03-21T21:01:17.674Z","lvl":"INFO","msg":"Request from 127.0.0.1: DELETE /serials/6057b403867c5b5892874081"} \
{"time":"2021-03-21T21:01:17.835Z","lvl":"INFO","msg":"Response with status 200 in 161 ms."}

> {"time":"2021-03-22T16:20:57.519Z","lvl":"INFO","msg":"Request from 127.0.0.1: OPTIONS /"} \
{"time":"2021-03-22T16:20:57.522Z","lvl":"INFO","msg":"Response with status 200 in 3 ms."}

**2. Сконфигурируйте nginx сервер таким образом, чтобы запросы проходили через nginx и перенаправлялись на сервер из лабораторной работы №1.**

Для начала работы с nginx он был установлен на ноутбук с помощью команды brew install nginx.

Запуск nginx осуществляется с помощью команды brew services start nginx \
Перезапуск: brew services restart nginx \
Остановка: brew services stop nginx \
Проверка файла конфигурации: nginx -t

Для того, чтобы запросы проходили через nginx и перенаправлялись на сервер, был отредактирован файл конфигурации nginx.conf следующим образом:

>worker_processes  4;
>
>events { \
    worker_connections  1024;\
}
>
>http {\
default_type  application/octet-stream; \
sendfile        on;\
keepalive_timeout  0;
>
>    server {\
>        listen       80;\
        server_name  localhost;\
>        location / {\
>            proxy_pass http://127.0.0.1:8000;\
>        }\
>        error_page   500 502 503 504  /50x.html;\
>        location = /50x.html {\
>            root   html;\
>        }\
>    }
>
>    include servers/*; \
}

где следующий блок отвечает за перенаправление запросов через прокси:

>location / {\
proxy_pass http://127.0.0.1:8000; \
}

**3. Используйте nginx отдачи статического контента. Как изменилось время ответа сервера?**

Для отдачи статического контента был добавлен следующий блок:

> client_max_body_size 128M;\
proxy_max_temp_file_size 0;\
proxy_buffering off;
>
>location ~ ^/(static)/ { \
>root /Users/elenamakeeva/WebstormProjects/web-labs-2021-elena-makeeva/static/html; \
>expires 30d; \
>}

В логах можно увидеть информации о скорости отдачи контента (страницы, картинки), где можно увидеть, что скорость отдачи контента сократилась.

>{"time":"2021-03-22T19:00:59.360Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /"}\
{"time":"2021-03-22T19:00:59.367Z","lvl":"INFO","msg":"Response with status 304 in 7 ms."}\
{"time":"2021-03-22T19:00:59.370Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /img/extra.gif"}\
{"time":"2021-03-22T19:00:59.372Z","lvl":"INFO","msg":"Response with status 304 in 2 ms."}

>{"time":"2021-03-22T19:02:02.354Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /hack"}\
{"time":"2021-03-22T19:02:02.357Z","lvl":"INFO","msg":"Response with status 200 in 3 ms."}\
{"time":"2021-03-22T19:02:02.412Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /img/main.gif"}\
{"time":"2021-03-22T19:02:02.422Z","lvl":"INFO","msg":"Response with status 200 in 1 ms."}

Пробросив ab -c 10 -n 100 http://192.168.0.36/ можно также увидеть, что 'Transfer rate' сократился с 401.73 до 225.16

>Server Software:        nginx/1.19.8\
Server Hostname:        192.168.0.36\
Server Port:            80
>
>Document Path:          /\
Document Length:        261 bytes
>
>Concurrency Level:      10\
Time taken for tests:   0.248 seconds\
Complete requests:      100\
Failed requests:        0\
Total transferred:      57100 bytes\
HTML transferred:       26100 bytes\
Requests per second:    403.79 [#/sec] (mean)\
Time per request:       24.765 [ms] (mean)\
Time per request:       2.477 [ms] (mean, across all concurrent requests)\
Transfer rate:          225.16 [Kbytes/sec] received
>
>Connection Times (ms)\
min  mean[+/-sd] median   max\
Connect:        0    0   0.2      0       1\
Processing:     7   23   9.4     26      48\
Waiting:        5   23   9.4     24      48\
Total:          7   24   9.4     26      48
>
>Percentage of the requests served within a certain time (ms)\
50%     26\
66%     29\
75%     31\
80%     31\
90%     34\
95%     37\
98%     48\
99%     48\
100%     48 (longest request)

**4. Настройте кеширование и gzip сжатие файлов.  Как изменилось время ответа сервера?**\
Nginx умеет кешировать информацию о файлах, с которыми ему приходится работать (например, css стили или картинки). Если к таким файлам происходит много обращений, кеширование может значительно ускорить этот процесс.

Для кеширования был добавлен следующий блок:

>open_file_cache max=200000 inactive=20s; \
open_file_cache_valid 30s;\
open_file_cache_min_uses 2;\
open_file_cache_errors on;

Обязательно нужно использовать сжатие, это значительно уменьшит трафик.

>gzip on;\
gzip_disable “msie6”;\
gzip_types text/plain text/css application/json application/x-javascript text/xml application/xml application/xml+rss text/javascript application/javascript;

Скорость отдачи контента (страницы, картинки) также сократилась до 1 ms:

>{"time":"2021-03-22T19:32:04.903Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /"}\
{"time":"2021-03-22T19:32:04.904Z","lvl":"INFO","msg":"Response with status 304 in 1 ms."}\
{"time":"2021-03-22T19:32:04.907Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /img/extra.gif"}\
{"time":"2021-03-22T19:32:04.908Z","lvl":"INFO","msg":"Response with status 304 in 1 ms."}

>{"time":"2021-03-22T19:33:31.937Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /hack"}\
{"time":"2021-03-22T19:33:31.940Z","lvl":"INFO","msg":"Response with status 304 in 3 ms."}\
{"time":"2021-03-22T19:33:32.007Z","lvl":"INFO","msg":"Request from 127.0.0.1: GET /img/main.gif"}\
{"time":"2021-03-22T19:33:32.008Z","lvl":"INFO","msg":"Response with status 304 in 1 ms."}

Пробросив ab -c 10 -n 100 http://192.168.0.36/ можно также увидеть, что 'Transfer rate' сократился с 401.73 (работа без nginx) до 188.83

>Server Software:        nginx/1.19.8\
Server Hostname:        192.168.0.36\
Server Port:            80
>
>Document Path:          /\
Document Length:        261 bytes
>
>Concurrency Level:      10\
Time taken for tests:   0.295 seconds\
Complete requests:      100\
Failed requests:        0\
Total transferred:      57100 bytes\
HTML transferred:       26100 bytes\
Requests per second:    338.64 [#/sec] (mean)\
Time per request:       29.530 [ms] (mean)\
Time per request:       2.953 [ms] (mean, across all concurrent requests)\
Transfer rate:          188.83 [Kbytes/sec] received
>
>Connection Times (ms)\
min  mean[+/-sd] median   max\
Connect:        0    0   0.3      0       2\
Processing:     6   28   8.4     28      53\
Waiting:        5   27   8.1     28      53\
Total:          6   28   8.5     29      54
>
>Percentage of the requests served within a certain time (ms)\
50%     29\
66%     32\
75%     35\
80%     36\
90%     39\
95%     42\
98%     45\
99%     54\
100%     54 (longest request)

**5. Запустите еще 2 инстанса вашего сервера из лабораторной работы №1, настройте перенаправление таким образом, чтобы на серверы приходили запросы в соотношении 3:1:1.**

Для запуска еще 2 инстансов сервера были добавлены следующие строчки кода в server.js

>app.listen(secondPort, 'localhost', () => {\
console.log('We are live on ' + secondPort);\
});
>
>app.listen(thirdPort, 'localhost', () => {\
console.log('We are live on ' + thirdPort);\
});

Далее в конфиг nginx было добавлено следующее:

>upstream penguins {\
server localhost:8000 weight=3;\
server localhost:8001 weight=1;\
server localhost:8002 weight=1;\
}

И изменено:

> location / {\
proxy_pass http://penguins; \
}

**6. Напишите еще два мини-сервера. Каждый из них должен обрабатывать два GET-запроса.**
* по / отдавать страницу с надписью “Добро пожаловать на сервис #1/#2” и ссылкой, ведущей на /temp
* по /temp  возвращать произвольный контент\
Настройте nginx так, чтобы в дополнение к п.1-5 он перенаправлял запросы по     url /service1 и /service2 на соответствующие сервера.

Были добавлены два мини-сервера server1 и server2. Изменен скрипт запуска серверов на:

>  "start": "node lab01/server.js | node lab02/server1/server1.js | node lab02/server2/server2.js"

Для настройки nginx былы добавлены следующие блоки:

>location ^~ /service2/ {\
proxy_pass http://127.0.0.1:8004/; \
}

>location ^~ /service1/ {\
proxy_pass http://127.0.0.1:8003/; \
}

**7. Настройте отдачу страницы о состоянии сервера**

Для отдачи страницы о состоянии сервера:

>location = /basic_status {\
stub_status; \
}

Дополнительные задания:

**3. Для повышения уровня безопасности необходимо скрывать внутреннюю реализацию вашего сервера. Скройте все заголовки Server (nginx можно оставить) из header ответа, а также дополнительные заголовки, которые дописывает ваш сервер, если есть.**

Для скрыти заголовков была добавлена следующая строчка во все location:

>proxy_hide_header X-Powered-By;

</details>
