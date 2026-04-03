CREATE TABLE wallets (
id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
external_id UUID           NOT NULL UNIQUE,
user_id     BIGINT         NOT NULL UNIQUE REFERENCES users(id),
balance     NUMERIC(19, 2) NOT NULL DEFAULT 0.00,
created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE transactions (
id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
external_id UUID           NOT NULL UNIQUE,
wallet_id   BIGINT         NOT NULL REFERENCES wallets(id),
type        VARCHAR(30)    NOT NULL,
amount      NUMERIC(19, 2) NOT NULL,
description VARCHAR(255),
created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_transactions_wallet_id  ON transactions(wallet_id);
CREATE INDEX idx_transactions_created_at ON transactions(created_at);
