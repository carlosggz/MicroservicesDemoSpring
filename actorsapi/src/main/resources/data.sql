insert into actor(id, first_name, last_name, likes) values ('A1', 'Actor', 'First', 0);
insert into actor(id, first_name, last_name, likes) values ('A2', 'Actor', 'Second', 0);
insert into actor(id, first_name, last_name, likes) values ('A3', 'Actor', 'Third', 0);
insert into movie(id, reference, title, actor_id) values ('1', 'M1', 'Movie First', 'A1');
insert into movie(id, reference, title, actor_id) values ('2', 'M2', 'Movie Second', 'A1');
insert into movie(id, reference, title, actor_id) values ('3', 'M1', 'Movie First', 'A2');
insert into movie(id, reference, title, actor_id) values ('4', 'M1', 'Movie First', 'A3');
insert into movie(id, reference, title, actor_id) values ('5', 'M3', 'Movie Third', 'A3');

