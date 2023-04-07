INSERT INTO `pizzas` (`id`, `description`, `name`, `price`) VALUES (NULL, 'Pomodoro, Mozzarella, Tonno, Cipolle', 'Tonno e Cipolle', '8.00')
INSERT INTO `pizzas` (`id`, `description`, `name`, `price`) VALUES (NULL, 'Pomodoro, Mozzarella, Acciughe, Olive', 'Acciughe e Olive', '8.99')
INSERT INTO `pizzas` (`id`, `description`, `name`, `price`) VALUES (NULL, 'Pomodoro, Mozzarella, Prosciutto Cotto, Olive, Carciofi, Capperi, Funghi, Acciughe', 'Capricciosa', '8.99')

INSERT INTO `ingredients` (`description`, `name`) VALUES('Carciofini Italiani DOC', 'Carciofini');
INSERT INTO `ingredients` (`description`, `name`) VALUES('Rovagnati DOC', 'Prosciutto Cotto');
INSERT INTO `ingredients` (`description`, `name`) VALUES('Olive Nere Siciliane DOC', 'Olive Nere');

INSERT INTO users (email, first_name, last_name, password) VALUES('john@email.it', 'John', 'Doe', '{noop}john');
INSERT INTO users (email, first_name, last_name, password) VALUES('jane@email.it', 'Jane', 'Smith','{noop}jane');

INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');

INSERT into users_roles(user_id, roles_id) VALUES(1, 1);
INSERT into users_roles(user_id, roles_id) VALUES(2, 2);