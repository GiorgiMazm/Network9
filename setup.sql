DROP TABLE IF EXISTS device;
DROP TABLE IF EXISTS department;

CREATE TABLE department
(
    id          serial  not null primary key,
    name        text    not null,
    location        text    not null
);

CREATE TABLE device
(
    id          serial  not null primary key,
    name        text    not null,
    department_id        integer not null references department(id) on delete  cascade,
    ip text not null 
);

INSERT INTO department(name, location)
VALUES
  ('Finanz', 'Hamburg'),
  ('IT', 'Berlin'),
  ('Personal', 'München');

INSERT INTO device (name, department_id, ip)
VALUES
  ('PC1', 1, '2001:0db8:85a3:0000:0000:8a2e:0370:7334'),
  ('Laptop2', 2, '2001:0db8:85a3:0000:0000:8a2e:0370:7a1b'),
  ('Printer3', 1, '2001:0db8:85a3:0000:0000:8a2e:0370:6b9f'),
  ('Laptop1', 3, '2001:0db8:85a3:0000:0000:8a2e:0370:5c8c'),
  ('PC2', 3, '2001:0db8:85a3:0000:0000:8a2e:0370:4d7a'),
  ('PC3', 1, '2001:0db8:85a3:0000:0000:8a2e:0370:3e69'),
  ('Printer1', 2, '2001:0db8:85a3:0000:0000:8a2e:0370:2f57'),
  ('Laptop3', 3, '2001:0db8:85a3:0000:0000:8a2e:0370:1e46'),
  ('Printer2', 1, '2001:0db8:85a3:0000:0000:8a2e:0370:0d34'),
  ('Scanner1', 2, '2001:0db8:85a3:0000:0000:8a2e:0370:ac23');