-- USERS
INSERT INTO public.application_user(email, last_name, first_name, password, default_application)    VALUES
    ('zack@gmail.com', 'Fair', 'Zack', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 2),
    ('barret@gmail.com', 'Wallace', 'Barret', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 1),
    ('tifa@gmail.com', 'Lockhart', 'Tifa', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 2),
    ('cloud@gmail.com', 'Strife', 'Cloud', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 2),
    ('sephiroth@gmail.com', 'SOLDIER 1st CLASS', 'Sephiroth', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 0);

-- USER PHONE NUMBERS
INSERT INTO public.user_phone_number(phone_number, type, user_id)   VALUES
    ('561-708-0000', 0, 1),
    ('310-563-0177', 2, 1),
    ('812-655-4479', 0, 2),
    ('818-662-8818', 1, 2),
    ('701-346-6869', 2, 3),
    ('619-378-4713', 0, 4),
    ('216-785-5235', 2, 5),
    ('413-736-7664', 1, 5);

-- CONTACT LIST
INSERT INTO public.contact_list(owner_id)	VALUES
    (1),
    (2),
    (3),
    (4),
    (5);

-- CONTACTS (User ID 5)
INSERT INTO public.contact(
	deleted, last_name, first_name, starred, user_id, contact_list_id)	VALUES
	(false, 'M', 'Madam', false, null, 1),
	(false, 'Gainsborough', 'Aerith', true, null, 1),
	(false, 'Turk', 'Reno', true, null, 1),
	(false, 'Rubio', 'Ahyan', false, null, 1),
	(false, 'Calderon', 'Joel', false, null, 1),
	(false, 'Turk', 'Rude', true, null, 1),
	(false, 'Shinra', 'Rufus', false, null, 1),
	(true, 'Karsyn', 'Clarette', false, null, 1),
	(true, 'Astor', 'Jack', false, null, 1),
	(false, 'Strife', 'Cloud', true, 4, 1),
	(false, 'Salahuddin', 'Blackmore', false, null, 1),
	(false, 'Moshe', 'Kiran', false, null, 1);

INSERT INTO public.contact_phone_number(phone_number, type, contact_id)	VALUES
    ('212-200-1436', 1, 1),
    ('212-205-5074', 2, 2),
    ('510-566-9659', 0, 3),
    ('212-288-1436', 1, 4),
    ('212-219-7204', 2, 5),
    ('212-226-3940', 0, 6),
    ('212-236-0225', 0, 7),
    ('212-238-1681', 1, 7),
    ('212-247-6592', 2, 10),
    ('402-707-6416', 2, 9),
    ('212-258-5956', 1, 3);

INSERT INTO public.contact_email_address(email, type, contact_id)	VALUES
    ('roseli785@gmail.com', 1, 1),
    ('rubio78@gmail.com', 2, 1),
    ('ahyyyyan@gmail.com', 0, 4),
    ('joeljeol@hotmail.com', 0, 5),
    ('blue.rivers@hotmail.com', 2, 6),
    ('cloud@gmail.com', 2, 10),
    ('salahuddinlikescats@yahoo.com', 1, 6);


