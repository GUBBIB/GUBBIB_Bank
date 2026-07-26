CREATE SEQUENCE member_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE members
(
    id               NUMBER(19, 0)                                  NOT NULL,
    created_at       TIMESTAMP(6)                                   NOT NULL,
    updated_at       TIMESTAMP(6)                                   NOT NULL,
    deleted_at       TIMESTAMP(6),
    email            VARCHAR2(100)                                  NOT NULL,
    password         VARCHAR2(255)                                  NOT NULL,
    name             VARCHAR2(30)                                   NOT NULL,
    phone            VARCHAR2(20)                                   NOT NULL,
    role             VARCHAR2(20)                                   NOT NULL,
    status           VARCHAR2(20)                                   NOT NULL,
    CONSTRAINT pk_members PRIMARY KEY (id)
);

ALTER TABLE members
    ADD CONSTRAINT uc_members_email UNIQUE (email);