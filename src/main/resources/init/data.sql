insert into `user` (`email`,`password`,`enable`,`created`) values ("2321608955@qq.com","123456",0,new data );
INSERT INTO `user`(`id`, `created`, `email`, `enabled`, `password`) VALUES (2, '2021-03-10 15:18:10.562000', 'a', b'1', '$2a$10$laU/ktvdqG2yVK1ZQv6oP.nd1l2D1EbNkB0yMIlj06.AKrNSF.Wza');

INSERT INTO `role`(`rid`, `name`) VALUES (1, 'ROLE_USER');
INSERT INTO `role`(`rid`, `name`) VALUES (2, 'ROLE_ADMIN');

INSERT INTO `permission`(`pid`, `url`) VALUES (1, '/user/test/ac');
INSERT INTO `permission`(`pid`, `url`) VALUES (2, '/user/test/ou');

INSERT INTO `user_role`(`id`, `rid`) VALUES (1, 1);
INSERT INTO `user_role`(`id`, `rid`) VALUES (2, 2);

INSERT INTO `role_permission`(`pid`, `rid`) VALUES (1, 1);
INSERT INTO `role_permission`(`pid`, `rid`) VALUES (2, 2);