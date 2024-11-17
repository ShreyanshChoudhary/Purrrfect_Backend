CREATE TABLE users (
    userId INT PRIMARY KEY,
    userName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(15) NOT NULL,
    address VARCHAR(255) NOT NULL,
    accountCreatedDate TIMESTAMP NOT NULL,
    isActive BOOLEAN NOT NULL
);
