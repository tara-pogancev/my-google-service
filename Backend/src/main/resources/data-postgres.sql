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