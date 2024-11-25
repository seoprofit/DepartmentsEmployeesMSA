INSERT INTO department (name, date_of_creation, parent_department) VALUES
('Main', '1990-01-01', null),
('IT', '1995-04-04', 'Main'),
('HR', '1996-05-05', 'Main'),
('HR in BA', '1998-02-05', 'HR'),
('DevOps', '1999-10-01', 'IT'),
('Devs', '2001-01-10', 'IT'),
('Engineer', '2002-10-10', 'IT');