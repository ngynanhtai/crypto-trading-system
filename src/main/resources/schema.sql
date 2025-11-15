-- 1. USERS
CREATE TABLE users
(
    id         BIGINT auto_increment PRIMARY KEY,
    username   VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. CURRENCY (eg. USDT, BTC, ETH)
CREATE TABLE currency
(
    id     BIGINT auto_increment PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL UNIQUE,
    name   VARCHAR(100)
);

-- 3. TRADING PAIR (base/quote)
CREATE TABLE trading_pair
(
    id                BIGINT auto_increment PRIMARY KEY,
    symbol            VARCHAR(20) NOT NULL UNIQUE,-- e.g. BTCUSDT, ETHUSDT
    base_currency_id  BIGINT NOT NULL,-- BTC or ETH
    quote_currency_id BIGINT NOT NULL, -- USDT
    FOREIGN KEY (base_currency_id) REFERENCES currency(id),
    FOREIGN KEY (quote_currency_id) REFERENCES currency(id)
);

CREATE INDEX idx_trading_pair_symbol ON trading_pair(symbol);

-- 4. WALLET: track available and locked balances (both for quote and base currencies)
CREATE TABLE wallet
(
    id          BIGINT auto_increment PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    currency_id BIGINT NOT NULL,
    available   DECIMAL(30, 10) NOT NULL DEFAULT 0,-- usable funds
    locked      DECIMAL(30, 10) NOT NULL DEFAULT 0,
    -- reserved for pending orders/trades
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, currency_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (currency_id) REFERENCES currency(id)
);

CREATE INDEX idx_wallet_user ON wallet(user_id);

-- 5. PRICE SOURCE (Binance, Huobi)
CREATE TABLE price_source
(
    id       BIGINT auto_increment PRIMARY KEY,
    code     VARCHAR(50) NOT NULL UNIQUE,-- BINANCE, HUOBI
    base_url VARCHAR(255)
);

-- 6. RAW PRICE FEED (store what we fetch from each source each time)
--    Save both bid and ask that source returns for a pair (null if source uses different fields)
CREATE TABLE price_feed
(
    id              BIGINT auto_increment PRIMARY KEY,
    trading_pair_id BIGINT NOT NULL,
    source_id       BIGINT NOT NULL,
    bid             DECIMAL(30, 10),
    ask             DECIMAL(30, 10),
    fetched_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    raw_json        CLOB, -- optional store of raw payload for debugging
    FOREIGN KEY (trading_pair_id) REFERENCES trading_pair(id),
    FOREIGN KEY (source_id) REFERENCES price_source(id)
);

CREATE INDEX idx_price_feed_pair_time ON price_feed(trading_pair_id, fetched_at
                                                    DESC);

-- 7. AGGREGATED BEST PRICE (computed from price_feed every scheduler run)
--    This stores the best bid and best ask across sources at the aggregated timestamp.
CREATE TABLE aggregated_price
(
    id                 BIGINT auto_increment PRIMARY KEY,
    trading_pair_id    BIGINT NOT NULL,
    best_bid           DECIMAL(30, 10),
    -- highest bid among sources (use for SELL orders)
    best_bid_source_id BIGINT,
    best_ask           DECIMAL(30, 10),
    -- lowest ask among sources (use for BUY orders)
    best_ask_source_id BIGINT,
    aggregated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (trading_pair_id) REFERENCES trading_pair(id),
    FOREIGN KEY (best_bid_source_id) REFERENCES price_source(id),
    FOREIGN KEY (best_ask_source_id) REFERENCES price_source(id)
);

CREATE INDEX idx_aggregated_latest ON aggregated_price(trading_pair_id,
                                                       aggregated_at DESC);

-- 8. TRADE ORDER (incoming user request; executed immediately using latest aggregated price)
CREATE TABLE trade_order
(
    id              BIGINT auto_increment PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    trading_pair_id BIGINT NOT NULL,
    side            VARCHAR(4) NOT NULL,-- 'BUY' or 'SELL'
    amount          DECIMAL(30, 10) NOT NULL,
    -- amount of base currency to buy/sell
    price           DECIMAL(30, 10) NOT NULL,
    -- price used (aggregated price at execution)
    total           DECIMAL(30, 10) NOT NULL,
    -- price * amount (in quote currency)
    status          VARCHAR(20) NOT NULL,-- NEW, FILLED, REJECTED, CANCELLED
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    executed_at     TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (trading_pair_id) REFERENCES trading_pair(id)
);

CREATE INDEX idx_trade_order_user ON trade_order(user_id);

CREATE INDEX idx_trade_order_status ON trade_order(status);

-- 9. TRADE TRANSACTION (ledger record for completed trades)
CREATE TABLE trade_transaction
(
    id              BIGINT auto_increment PRIMARY KEY,
    trade_order_id  BIGINT NOT NULL,
    user_id         BIGINT NOT NULL,
    trading_pair_id BIGINT NOT NULL,
    side            VARCHAR(4) NOT NULL,
    price           DECIMAL(30, 10) NOT NULL,
    amount          DECIMAL(30, 10) NOT NULL,
    total           DECIMAL(30, 10) NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (trade_order_id) REFERENCES trade_order(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (trading_pair_id) REFERENCES trading_pair(id)
);

CREATE INDEX idx_trade_tx_user_time ON trade_transaction(user_id, created_at
                                                         DESC);