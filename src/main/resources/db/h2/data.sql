INSERT INTO users (id, username, fullname, password, email, create_date) VALUES (default, 'admin', 'System Administrator', '$2a$10$foZUnFlOto5M8Ei50MCqFuRU.FIxUqW0Y2AffRr8Dg29FyBdgVgBm', 'admin@admin.com', current_timestamp());
INSERT INTO users (id, username, fullname, password, email, create_date) VALUES (default, 'charles', 'Charles Wust', '$2a$10$GJwoS0LmcaZrjvmtg6zLvuFDpj7vVaLOBI2U31mNAmSMEEpdYdUwu', 'charles.wust@gmail.com', current_timestamp());

INSERT INTO user_authorities (id, user_id, authority, create_date) VALUES (default, 1, 'ADMIN', current_timestamp());
INSERT INTO user_authorities (id, user_id, authority, create_date) VALUES (default, 2, 'USER', current_timestamp());