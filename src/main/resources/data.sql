-- user
INSERT INTO users (id, username) VALUES (1, 'nguyenanhtai');

-- currencies
INSERT INTO currency (id, symbol, name) VALUES (1, 'USDT', 'Tether USD');
INSERT INTO currency (id, symbol, name) VALUES (2, 'BTC', 'Bitcoin');
INSERT INTO currency (id, symbol, name) VALUES (3, 'ETH', 'Ethereum');

-- trading pairs
INSERT INTO trading_pair (id, symbol, base_currency_id, quote_currency_id)
VALUES (1, 'BTCUSDT', 2, 1);

INSERT INTO trading_pair (id, symbol, base_currency_id, quote_currency_id)
VALUES (2, 'ETHUSDT', 3, 1);

-- price sources
INSERT INTO price_source (id, code, base_url) VALUES (1, 'BINANCE', 'https://api.binance.com/api/v3/ticker/bookTicker');
INSERT INTO price_source (id, code, base_url) VALUES (2, 'HUOBI', 'https://api.huobi.pro/market/tickers');

-- initial wallet balances: user starts with 50,000 USDT, 0 BTC, 0 ETH
INSERT INTO wallet (user_id, currency_id, available, locked) VALUES (1, 1, 50000.00, 0.00);
INSERT INTO wallet (user_id, currency_id, available, locked) VALUES (1, 2, 0.0000000000, 0.0000000000);
INSERT INTO wallet (user_id, currency_id, available, locked) VALUES (1, 3, 0.0000000000, 0.0000000000);
