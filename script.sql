CREATE TYPE role_enum AS ENUM ('ADMIN', 'AUDITOR', 'MANAGER', 'TELLER', 'CLIENT');

-- Type de compte (corrigé : noms standards)
CREATE TYPE account_type AS ENUM ('CREDIT', 'CURRENT', 'SAVINGS');


CREATE TYPE operation_type AS ENUM (
    'DEPOSIT',
    'WITHDRAW',
    'TRANSFER_INTERNAL',
    'TRANSFER_EXTERNAL',
    'CREDIT_DISBURSEMENT'
);

-- Type de transaction (corrigé : orthographe et ajout de types manquants)
CREATE TYPE transaction_type AS ENUM (
    'DEPOSIT',
    'WITHDRAW',
    'TRANSFER_OUT',
    'TRANSFER_IN',
    'EXTERNAL_TRANSFER',
    'FEE',
    'FEE_INCOME',
    'DEBIT'
);

-- Statut de virement (corrigé : orthographe)
CREATE TYPE transaction_status AS ENUM ('SETTLED', 'PENDING', 'FAILED', 'CANCELLED');

CREATE TYPE mode_rule AS ENUM ('FIXED', 'PERCENT');

-- Type de crédit (corrigé : orthographe française)
CREATE TYPE credit_type AS ENUM ('SIMPLE', 'COMPOUND');

-- Statut de crédit (ajout de statuts manquants)
CREATE TYPE credit_status AS ENUM ('PENDING', 'ACTIVE', 'LATE', 'CLOSED', 'REJECTED');

-- Type de devise
CREATE TYPE currency_type AS ENUM ('MAD', 'EUR', 'USD');


CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       title role_enum NOT NULL UNIQUE,
                       description TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- TABLE DES UTILISATEURS
-- =============================================

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(20),
                       role_id INTEGER NOT NULL, -- Correction : ajout de la colonne manquante
                       is_active BOOLEAN DEFAULT true,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Clé étrangère corrigée
                       FOREIGN KEY (role_id) REFERENCES roles(id)
    );


CREATE TABLE clients (
                         id SERIAL PRIMARY KEY,
                         salary DECIMAL(15,2), -- Correction : DECIMAL au lieu de BigDecimal
                         monthly_income DECIMAL(15,2),
                         profession VARCHAR(200),
                         cin VARCHAR(20), -- Carte d'identité nationale
                         address TEXT,
                         user_id INTEGER NOT NULL UNIQUE, -- Correction : nom de table

    -- Clé étrangère corrigée
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,


);

CREATE TABLE accounts (
                          id SERIAL PRIMARY KEY,
                          account_number VARCHAR(20) UNIQUE NOT NULL,
                          balance DECIMAL(15,2) NOT NULL DEFAULT 0.00, -- Correction : DECIMAL au lieu de float
                          overdraft_limit DECIMAL(15,2) DEFAULT 0.00,
                          currency currency_type DEFAULT 'MAD',
                          is_active BOOLEAN DEFAULT true, -- Correction : nom de colonne
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Correction : nom de colonne
                          closed_at TIMESTAMP,
                          type account_type NOT NULL,
                          client_id INTEGER NOT NULL, -- Correction : référence vers clients

    -- Clé étrangère corrigée
                          FOREIGN KEY (client_id) REFERENCES clients(id),

    );

- TABLE DES TRANSACTIONS
-- =============================================

CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              transaction_id VARCHAR(50) UNIQUE NOT NULL,
                              amount DECIMAL(15,2) NOT NULL, -- Correction : DECIMAL au lieu de float
                              currency currency_type DEFAULT 'MAD',
                              description TEXT,
                              source_account_id INTEGER, -- Correction : nom plus clair
                              target_account_id INTEGER, -- Correction : nom plus clair
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Correction : nom de colonne
                              settled_at TIMESTAMP,
                              type transaction_type NOT NULL,
                              status transaction_status NOT NULL DEFAULT 'PENDING', -- Correction : ajout de virgule
                              executed_by INTEGER,
                              fee_amount DECIMAL(15,2) DEFAULT 0.00,

    -- Clés étrangères corrigées
                              FOREIGN KEY (source_account_id) REFERENCES accounts(id),
                              FOREIGN KEY (target_account_id) REFERENCES accounts(id),
                              FOREIGN KEY (executed_by) REFERENCES users(id),


    );
CREATE TABLE fee_rules ( -- Correction : nom de table
                           id SERIAL PRIMARY KEY,
                           operation_type operation_type NOT NULL, -- Correction : nom de colonne
                           mode mode_rule NOT NULL, -- Correction : nom de colonne
                           value DECIMAL(10,4) NOT NULL, -- Correction : DECIMAL au lieu de float
                           currency currency_type DEFAULT 'MAD', -- Correction : type de données
                           is_active BOOLEAN DEFAULT true, -- Correction : nom de colonne
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE credits (
                         id SERIAL PRIMARY KEY,
                         credit_number VARCHAR(30) UNIQUE NOT NULL,
                         amount DECIMAL(15,2) NOT NULL, -- Correction : DECIMAL au lieu de float
                         duration_months INTEGER NOT NULL, -- Correction : nom en anglais
                         interest_rate DECIMAL(5,4) NOT NULL, -- Correction : nom en anglais et type
                         monthly_payment DECIMAL(15,2),
                         remaining_amount DECIMAL(15,2),
                         fee_rule_id INTEGER, -- Correction : nom de colonne
                         justification TEXT,
                         credit_type credit_type NOT NULL, -- Correction : nom de colonne
                         status credit_status DEFAULT 'PENDING', -- Correction : nom de colonne
                         account_id INTEGER NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         approved_at TIMESTAMP,
                         approved_by INTEGER,
                         next_payment_date DATE,

    -- Clés étrangères
                         FOREIGN KEY (account_id) REFERENCES accounts(id),
                         FOREIGN KEY (fee_rule_id) REFERENCES fee_rules(id),
                         FOREIGN KEY (approved_by) REFERENCES users(id),


);



CREATE TABLE credit_payments
(
    id               SERIAL PRIMARY KEY,
    credit_id        INTEGER        NOT NULL,
    payment_number   INTEGER        NOT NULL,
    principal_amount DECIMAL(15, 2) NOT NULL,
    interest_amount  DECIMAL(15, 2) NOT NULL,
    penalty_amount   DECIMAL(15, 2)     DEFAULT 0.00,
    total_amount     DECIMAL(15, 2) NOT NULL,
    due_date         DATE           NOT NULL,
    paid_date        DATE,
    status           transaction_status DEFAULT 'PENDING',
    transaction_id   INTEGER,

    -- Clés étrangères
    FOREIGN KEY (credit_id) REFERENCES credits (id) ON DELETE CASCADE,
    FOREIGN KEY (transaction_id) REFERENCES transactions (id)
);



CREATE TABLE exchange_rates
(
    id            SERIAL PRIMARY KEY,
    from_currency currency_type  NOT NULL,
    to_currency   currency_type  NOT NULL,
    rate          DECIMAL(10, 6) NOT NULL,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active     BOOLEAN   DEFAULT true,
    updated_by    INTEGER,

    -- Clé étrangère
    FOREIGN KEY (updated_by) REFERENCES users (id)
);




/