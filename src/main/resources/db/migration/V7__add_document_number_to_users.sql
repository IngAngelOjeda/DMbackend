ALTER TABLE users
    ADD COLUMN document_number VARCHAR(50),
    ADD CONSTRAINT uq_users_document_number UNIQUE (document_number);