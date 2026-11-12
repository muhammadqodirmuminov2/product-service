CREATE TABLE products (
                          id           UUID          PRIMARY KEY,
                          company_id   UUID          NOT NULL,
                          sku          VARCHAR(64)   NOT NULL,
                          barcode      VARCHAR(64),
                          name         VARCHAR(255)  NOT NULL,
                          description  TEXT,
                          price        NUMERIC(19,2) NOT NULL CHECK (price >= 0),
                          status       VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
                          created_at   TIMESTAMPTZ   NOT NULL DEFAULT now(),
                          updated_at   TIMESTAMPTZ   NOT NULL DEFAULT now(),
                          version      BIGINT        NOT NULL DEFAULT 0,

                          CONSTRAINT uk_products_company_sku UNIQUE (company_id, sku)
);

CREATE INDEX idx_products_company_barcode ON products (company_id, barcode);