CREATE TYPE role_enum AS ENUM ('ADMIN', 'AUDITOR', 'MANAGER', 'TELLER');

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
                       role_id INTEGER NOT NULL,
                       is_active BOOLEAN DEFAULT true,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (role_id) REFERENCES roles(id)
    );


CREATE TABLE clients (
                         id SERIAL PRIMARY KEY,
                         first_name varchar(50),
                         last_name varchar(50),
                         salary DECIMAL(15,2),
                         cin VARCHAR(20),
                         is_active BOOLEAN DEFAULT true,
                         address TEXT
) ;

CREATE TABLE accounts (
                          id SERIAL PRIMARY KEY,
                          account_number VARCHAR(20) UNIQUE NOT NULL,
                          balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
                          overdraft_limit DECIMAL(15,2) DEFAULT 0.00,
                          currency currency_type DEFAULT 'MAD',
                          is_active BOOLEAN DEFAULT true,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          closed_at TIMESTAMP,
                          type account_type NOT NULL,
                          client_id INTEGER NOT NULL,
                          FOREIGN KEY (client_id) REFERENCES clients(id)

    );

CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              amount DECIMAL(15,2) NOT NULL,
                              currency currency_type DEFAULT 'MAD',
                              description TEXT,
                              transfer_out_id INTEGER,
                              transfer_in_id INTEGER,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              settled_at TIMESTAMP,
                              type transaction_type NOT NULL,
                              status transaction_status NOT NULL DEFAULT 'PENDING',
                              fee_rule_id integer,
                              fee_amount DECIMAL(15,2) DEFAULT 0.00,
                              FOREIGN KEY (transfer_out_id) REFERENCES accounts(id),
                              FOREIGN KEY (transfer_in_id) REFERENCES accounts(id),
                              FOREIGN KEY (fee_rule_id) REFERENCES  fee_rules(id)
    );
CREATE TABLE fee_rules (
                           id SERIAL PRIMARY KEY,
                           operation_type operation_type NOT NULL,
                           mode mode_rule NOT NULL,
                           value DECIMAL(10,4) NOT NULL,
                           currency currency_type DEFAULT 'MAD',
                           is_active BOOLEAN DEFAULT true,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE credits (
                         id SERIAL PRIMARY KEY,
                         amount DECIMAL(15,2) NOT NULL, -- Correction : DECIMAL au lieu de float
                         duration_months INTEGER NOT NULL, -- Correction : nom en anglais
                         interest_rate DECIMAL(5,4) NOT NULL, -- Correction : nom en anglais et type
                         monthly_payment DECIMAL(15,2),
                         remaining_amount DECIMAL(15,2),
                         fee_rule_id INTEGER,
                         justification TEXT,
                         credit_type credit_type NOT NULL, -- Correction : nom de colonne
                         status credit_status DEFAULT 'PENDING', -- Correction : nom de colonne
                         account_id INTEGER NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         approved_at TIMESTAMP,
                         approved_by INTEGER,
                         next_payment_date DATE,
                         FOREIGN KEY (account_id) REFERENCES accounts(id),
                         FOREIGN KEY (fee_rule_id) REFERENCES fee_rules(id),
                         FOREIGN KEY (approved_by) REFERENCES users(id),

);



CREATE TABLE credit_payments
(
    id               SERIAL PRIMARY KEY,
    credit_id        INTEGER        NOT NULL,
    principal_amount DECIMAL(15, 2) NOT NULL,
    interest_amount  DECIMAL(15, 2) NOT NULL,
    penalty_amount   DECIMAL(15, 2)     DEFAULT 0.00,
    total_amount     DECIMAL(15, 2) NOT NULL,
    due_date         DATE           NOT NULL,
    paid_date        DATE,
    status           transaction_status DEFAULT 'PENDING',
    transaction_id   INTEGER,
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
    FOREIGN KEY (updated_by) REFERENCES users (id)
);




