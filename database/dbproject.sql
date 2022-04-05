CREATE DATABASE dbproject
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;


CREATE TABLE IF NOT EXISTS public.positions
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 ),
    title text NOT NULL,
    salary money NOT NULL,
    bonus_percentage double precision NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE public.positions
    OWNER to postgres;



CREATE TABLE IF NOT EXISTS public.products
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 ),
    title text NOT NULL,
    price money NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE public.products
    OWNER to postgres;




CREATE TABLE IF NOT EXISTS public.services
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 ),
    title text NOT NULL,
    price money NOT NULL,
    term bigint NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE public.services
    OWNER to postgres;

COMMENT ON COLUMN public.services.term
    IS 'срок выполнения услуги в днях';


CREATE TABLE IF NOT EXISTS public.managers
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 ),
    fio text NOT NULL,
    position_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_managers_positions FOREIGN KEY (position_id)
        REFERENCES public.positions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.managers
    OWNER to postgres;



CREATE TABLE IF NOT EXISTS public.clients
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 ),
    title text NOT NULL,
    manager_id bigint NOT NULL,
    date_contract date,
    PRIMARY KEY (id),
    CONSTRAINT fk_managers FOREIGN KEY (manager_id)
        REFERENCES public.managers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.clients
    OWNER to postgres;



CREATE TABLE IF NOT EXISTS public.rendered_services
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 ),
    service_id bigint NOT NULL,
    final_price money NOT NULL,
    client_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_services FOREIGN KEY (service_id)
        REFERENCES public.services (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_clients FOREIGN KEY (client_id)
        REFERENCES public.clients (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.rendered_services
    OWNER to postgres;



CREATE TABLE IF NOT EXISTS public.sold_products
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 ),
    product_id bigint NOT NULL,
    final_price money NOT NULL,
    client_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_clients FOREIGN KEY (client_id)
        REFERENCES public.clients (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_products FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.sold_products
    OWNER to postgres;



set lc_monetary to "ru_RU.UTF-8";
SELECT id, title, salary, bonus_percentage
	FROM public.positions;



    set lc_monetary to "ru_RU.UTF-8";
SELECT 
	fio as "ФИО", 
	pos.title as "Должность",
	public.CALCBONUS(pos.salary, pos.bonus_percentage, 2, 2)
FROM public.managers man
JOIN public.positions pos ON pos.id = position_id
JOIN public.clients cli ON cli.manager_id = man.id
CROSS JOIN public.services ser
CROSS JOIN public.products pro
JOIN public.rendered_services rs ON rs.client_id = cli.id
GROUP BY fio, pos.title, pos.salary

ORDER BY fio asc;


set lc_monetary to "ru_RU.UTF-8";

WITH 

managers AS
(SELECT id, position_id, fio from public.managers),

clients AS
(select id, manager_id from public.clients),

managers_info AS
(SELECT managers.id AS manager_id,
        managers.fio AS fio,
        managers.position_id AS position_id,
        clients.id AS client_id
FROM managers
JOIN clients
ON managers.id = clients.manager_id),

rendered_services AS
(SELECT client_id, COUNT(service_id) AS count_service_id FROM public.rendered_services WHERE date >= '2021-01-01' GROUP BY client_id),
 
calculation_services AS
(SELECT managers_info.manager_id AS manager_id,
        managers_info.client_id AS client_id,
        managers_info.position_id AS position_id,
        managers_info.fio AS fio,
        COALESCE(rendered_services.count_service_id,0) as count_service_id
FROM managers_info
LEFT JOIN rendered_services
ON rendered_services.client_id = managers_info.client_id),

sold_products AS
(SELECT client_id, COUNT(product_id) AS count_product_id FROM public.sold_products GROUP BY client_id),

calculation_products AS
(SELECT calculation_services.manager_id,
        calculation_services.client_id,
        calculation_services.count_service_id,
        calculation_services.position_id,
        calculation_services.fio AS fio,
        COALESCE(sold_products.count_product_id,0) AS count_product_id
FROM calculation_services
LEFT JOIN sold_products
ON sold_products.client_id = calculation_services.client_id),

positions AS
(SELECT id, title, salary, bonus_percentage FROM public.positions),

results AS
(SELECT calculation_products.fio,
       calculation_products.position_id,
       calculation_products.client_id,
       positions.title,
       (positions.salary * positions.bonus_percentage * (calculation_products.count_service_id + calculation_products.count_product_id)) / 2 AS bonus
FROM calculation_products
JOIN positions
ON positions.id = calculation_products.position_id)

SELECT results.fio AS "ФИО", 
		results.title AS "Должность", 
		SUM(results.bonus) AS "Бонус"
FROM results 
GROUP BY results.fio, results.title
ORDER BY results.fio asc