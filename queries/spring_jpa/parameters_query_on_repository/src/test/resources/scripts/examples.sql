-- examples.sql
INSERT INTO example(id, name, age, date) VALUES(20, 'test name 20', 20, PARSEDATETIME('2020-01-03 22:59:52', 'yyyy-MM-dd hh:mm:ss'));
INSERT INTO example(id, name, age, date) VALUES(21, 'test name 21', 30, PARSEDATETIME('2020-02-03 22:59:52', 'yyyy-MM-dd hh:mm:ss'));
INSERT INTO example(id, name, age, date) VALUES(30, 'test name 30', 40, PARSEDATETIME('2020-03-03 22:59:52', 'yyyy-MM-dd hh:mm:ss'));
INSERT INTO example(id, name, age, date) VALUES(31, 'test name 31', 12, PARSEDATETIME('2020-04-03 22:59:52', 'yyyy-MM-dd hh:mm:ss'));
INSERT INTO example(id, name, age, date) VALUES(40, null, 15, PARSEDATETIME('2020-05-03 22:59:52', 'yyyy-MM-dd hh:mm:ss'));
INSERT INTO example(id, name, age, date) VALUES(41, 'test name 41', 18, PARSEDATETIME('2020-06-03 22:59:52', 'yyyy-MM-dd hh:mm:ss'));