🚀 Crypto Trading System --- Spring Boot (H2 In-Memory)
=====================================================

This project implements a simplified **crypto trading system** using **Spring Boot** and an **H2 in-memory database**, based on the assignment requirements.\
It supports price aggregation, trading, wallet tracking, and transaction history for two major crypto pairs.

* * * * *

📌 Features
-----------

### ✅ **1\. Price Aggregation (Every 10 seconds)**

A scheduler retrieves order book prices from:

-   **Binance**\
    Endpoint: `https://api.binance.com/api/v3/ticker/bookTicker`

-   **Huobi**\
    Endpoint: `https://api.huobi.pro/market/tickers`

From each exchange, the system extracts:

-   **Bid Price** → Used for **SELL** orders

-   **Ask Price** → Used for **BUY** orders

The system stores **only the best aggregated price** in the `aggregated_price` table every 10 seconds.

* * * * *

### ✅ **2\. Latest Aggregated Price API**

Users can fetch the **latest best price** for supported trading pairs:

-   `ETHUSDT`

-   `BTCUSDT`

* * * * *

### ✅ **3\. User Trading API**

Supports:

-   **BUY**

-   **SELL**

Trades are executed **only using the latest aggregated price**, and each trade will be marked as:

-   `FILLED` (successful)

-   `FAILED` (e.g., insufficient balance)

* * * * *

### ✅ **4\. Wallet Balance API**

Each user has a crypto wallet containing balances for:

-   `USDT` (default 50,000)

-   `ETH`

-   `BTC`

Wallet updates automatically after every trade.

* * * * *

### ✅ **5\. User Trading History API**

Returns a list of:

-   Buy/Sell trades

-   Quantity

-   Price at execution

-   Timestamp

-   Trading pair

-   Order status

* * * * *

🏗️ Tech Stack
--------------

| Component | Technology |
| --- | --- |
| Backend | Spring Boot 3+ |
| Database | H2 (In-Memory) |
| ORM | Spring Data JPA / Hibernate |
| JSON | Jackson |
| Build | Maven |
| Scheduler | Spring @Scheduled |

* * * * *

📂 Database Structure (ERD)
---------------------------
<img width="1419" height="1609" alt="ERD" src="https://github.com/user-attachments/assets/35f5b979-1a2d-41d2-a23f-d3383ebdbbd7" />


📡 API Endpoints
----------------

### **Price**

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/prices/latest` | Get latest aggregated price |

### **Trade**

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/trade` | Execute BUY/SELL trade |

### **Wallet**

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/balance/{user_id}` | Get user wallet balance |

### **History**

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/transaction-history/{user_id}` | Get trade transaction list |

* * * * *

🔄 Price Aggregation Logic
--------------------------

1.  Fetch prices from Binance & Huobi

2.  Convert to internal `PriceSource` format

3.  Compare both sources

4.  Pick the **best ask** and **best bid**

5.  Store as a new record in `aggregated_price` every 10 seconds

This ensures trading always uses the freshest available market price.

✔️ Git Workflow (As required by assignment)
-------------------------------------------

1.  **Initial commit** → after generating project structure

2.  Frequent commits throughout development

3.  **End commit** → before submitting assignment
