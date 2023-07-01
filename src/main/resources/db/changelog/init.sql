CREATE TABLE shop
(
    id            BIGINT                NOT NULL PRIMARY KEY,
    name          VARCHAR,
    address       VARCHAR,
    lat           FLOAT                 NOT NULL,
    lon           FLOAT                 NOT NULL,
    working_hours VARCHAR,
    position      geometry(POINT, 4326) NOT NULL
);

CREATE TABLE city
(
    id            BIGINT                NOT NULL PRIMARY KEY,
    name          VARCHAR,
    coordinates   geometry              NOT NULL
);

INSERT INTO shop(id, name, address, lat, lon, working_hours, position)
values (1, 'Столичный рожок', 'г. Москва, Красная площадь', 55.7538337, 37.6211812,
        'Пн - Пт: 9:00 - 21:00 Сб, Вс - выхоные дни', st_setsrid(st_makepoint(37.6211812, 55.7538337), 4326));
INSERT INTO shop(id, name, address, lat, lon, working_hours, position)
values (2, 'Ленинградское', 'г. Санкт - Петербург, Дворцовая площадь', 59.938879, 30.315212,
        'Пн - Пт: 9:00 - 21:00 Сб, Вс - выхоные дни', st_setsrid(st_makepoint(30.315212, 59.938879), 4326));
INSERT INTO shop(id, name, address, lat, lon, working_hours, position)
values (3, 'Сила Сибири', 'Иркутская обл., оз. Байкал', 51.90503, 126.62002,
        'Пн - Пт: 9:00 - 21:00 Сб, Вс - выхоные дни', st_setsrid(st_makepoint(126.62002, 51.90503), 4326));
INSERT INTO shop(id, name, address, lat, lon, working_hours, position)
values (4, 'Ракушка Сахалина', 'о. Сахалин', 46.96411, 142.73476,
        'Пн - Пт: 9:00 - 21:00 Сб, Вс - выхоные дни', st_setsrid(st_makepoint(142.73476, 46.96411), 4326));
INSERT INTO shop(id, name, address, lat, lon, working_hours, position)
values (5, 'Золотой слиток', 'р. Колыма', 64.748, 153.80,
        'Без выходных и перерывов', st_setsrid(st_makepoint(153.80, 64.748), 4326));
INSERT INTO shop(id, name, address, lat, lon, working_hours, position)
values (6, 'Мороженное Хинкали', 'г. Тбилиси', 41.6941100, 44.8336800,
        'Без выходных и перерывов.', st_setsrid(st_makepoint(44.8336800, 41.6941100), 4326));

INSERT INTO city(id, name, coordinates)
values (1, 'Москва',
        ST_GeometryFromText('POLYGON((37.5938 55.5277, 37.3962 55.6879, 37.4449 55.8894, 37.7367 55.9099, 37.9381 55.797, 37.5938 55.5277))', 4326));
INSERT INTO city(id, name, coordinates)
values (2, 'Санкт-Петербург',
        ST_GeometryFromText('POLYGON((30.3795 59.8072,30.6245 59.9302, 30.3938 60.0687, 30.0092 60.0139, 30.1662 59.7904, 30.3795 59.8072))', 4326));