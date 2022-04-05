/**
* Создание БД
*/
CREATE DATABASE "Database Lab"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;


/**
* Создание таблицы Языки
*/
CREATE TABLE IF NOT EXISTS public.languages
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    title text NOT NULL,
    CONSTRAINT languages_pkey PRIMARY KEY (id)
)

ALTER TABLE public.languages
    OWNER to postgres;
/**
* Добавление языков в таблицу Языки
*/
INSERT INTO public.languages(
	title)
	VALUES ('Русский')
	, ('Английский')
	, ('Испанский')
	, ('Японский')
	, ('Китайский')
	, ('Корейский')
	, ('Немецкий')
	, ('Французский')
	;


/**
* Просмотр содержимого таблицы Языки
*/
SELECT id, title
	FROM public.languages;

/**
* Создание таблицы Разработчики
*/
CREATE TABLE IF NOT EXISTS public.developers
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    fullname name NOT NULL,
    CONSTRAINT developers_pkey PRIMARY KEY (id)
)

ALTER TABLE public.developers
    OWNER to postgres;


/**
* Добавление имен в таблицу Разработчики
*/
INSERT INTO public.developers(
	fullname)
	VALUES ('Rick Novak')
	, ('Susan Connor')
	, ('Margaret Adelman')
	, ('Ronald Barr')
	, ('Marie Broadbet')
	, ('Roger Lum')
	, ('Marlene Donahue')
	, ('Jeff Johnson')
	, ('Melvin Forbis')
	, ('Mike James')
	;


/**
* Просмотр содержимого таблицы Разработчики
*/
SELECT id, fullname
	FROM public.developers;


/**
* Создание таблицы Возрастной рейтинг
*/
CREATE TABLE IF NOT EXISTS public.age_rating
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 ),
    title text NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE public.age_rating
    OWNER to postgres;


/**
* Добавление значений в таблицу Возврастной рейтинг
*/
INSERT INTO public.age_rating(
	title)
	VALUES ('4+')
	, ('6+')
	, ('9+')
	, ('12+')
	, ('16+')
	, ('18+')
	;

/**
* Просмотр содержимого таблицы Возрастной рейтинг
*/
SELECT id, title
	FROM public.age_rating;


/**
* Создание таблицы Приложения
*/
CREATE TABLE IF NOT EXISTS public.apps
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 ),
    title text NOT NULL,
    description text NOT NULL,
    size double precision NOT NULL,
    age_rating_id bigint NOT NULL,
    developer_id bigint NOT NULL,
    language_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_age_rating FOREIGN KEY (age_rating_id)
        REFERENCES public.age_rating (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_developers FOREIGN KEY (developer_id)
        REFERENCES public.developers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_languages FOREIGN KEY (language_id)
        REFERENCES public.languages (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.apps
    OWNER to postgres;

/**
* Добавление значений в таблицу Приложения
*/
INSERT INTO public.apps(
	title, description, size, age_rating_id, developer_id, language_id)
	VALUES ('Авито', 'Интернет-сервис для размещения объявлений о товарах, недвижимости, вакансиях и резюме на рынке труда', '197.5', 4, 2, 1)
    , ('Radio France FM', 'Radios France est la meilleure façon decouter la radio de votre pays sur votre iPod Touch, iPhone et iPad.', '629.3', 1, 5, 8)
	, ('TYKO:Korean', 'If you are willing to learning Korean or getting into Korean culture, type Korean with TYKO.', '44.9', 1, 7, 6)
	, ('Grammatisch', 'Mit Grammatisch lernst du Deutsch besser.', '72.4', 2, 5, 3)
	, ('Monopoly', 'Monopoly is a multi-player economics-themed board game.', '649.7', 1, 2, 2)
	, ('Genshin Impact', 'Genshin Impact — это красочная анимешная Action RPG с открытым миром от китайской компании miHoYo.', '3200', 4, 8, 1)
	;


/**
* Просмотр содержимого таблицы Приложения
*/
SELECT a.id, a.title as "App Name", description, size, ari.title as "Age rating", di.fullname AS "Developer", li.title AS "Language"
	FROM public.apps a
	JOIN public.languages li ON li.id = language_id
	JOIN public.age_rating ari ON ari.id = age_rating_id
	JOIN public.developers di ON di.id = developer_id
	;