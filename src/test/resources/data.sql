INSERT INTO `rol_entity` (id, roles) VALUES ("e89e9c80-de49-4c34-ae9a-bea8a5a954b6", 'ROLE_USER');
INSERT INTO `user` (`id`, `email`, `password`, `user_name`) VALUES ('81898a0e-cd02-432b-a49d-0f3aa558e9f7', 'lucas@lucas.com', 'Luc12345', 'Lucas');
INSERT INTO `user_rol` (`user_id`, `rol_id`) VALUES ('81898a0e-cd02-432b-a49d-0f3aa558e9f7', 'e89e9c80-de49-4c34-ae9a-bea8a5a954b6');