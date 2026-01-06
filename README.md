# 🧠 Financial Data Insights Platform using Setu Account Aggregator + AI

This project is a **student academic project** focused on **secure financial data aggregation**, **transaction analysis**, and **personal finance insights** using the **Setu Account Aggregator Framework** and an integrated **AI module**.

---

## 🚀 Features

- ✅ **Consent-based access** to user financial data via Setu AA APIs
- 📊 **Visualization-ready FI Data** (bank accounts, balances, transactions)
- 🧠 **AI Module** powered by OpenAI to:
  - Summarize financial behavior
  - Answer user queries like: *"Where did I spend the most last month?"*
  - Detect unusual patterns or spending spikes
- 🧮 **Insights Use Case** using Setu's Insights APIs:
  - Categorized transactions (e.g., food, bills, travel)
  - Monthly income/expense summaries
  - Spend distribution charts
  - Financial health metrics

---

## 🧩 Tech Stack

| Layer              | Technology                             |
|--------------------|----------------------------------------|
| Backend API        | Java Spring Boot                       |
| Financial APIs     | [Setu AA APIs](https://docs.setu.co/)  |
| AI Integration     | OpenAI (GPT-based)                     |
| Database           | DynamoDB (for scalability)             |
| Frontend           | React.js                               |
| Authentication     | JWT-based login                        |
| Deployment (demo)  | AWS EC2 + S3                           |

---

## 📦 Modules

### 1. **Consent Flow (Setu AA)**
- User initiates consent
- Data request sent to FIPs
- Webhook listener implemented to handle FIU data response

### 2. **FI Data Handling**
- FI Data parsed and stored securely
- Mapped to account summary and transaction list

### 3. **Insights Generation**
- Categorization using Setu Insights API *(requested access)*
- Monthly spend reports, income tracking, and top categories
- Time-series data visualizations for graphs

### 4. **AI Chat Module**
- Users can type questions like:
  - *"How much did I spend on groceries?"*
  - *"Suggest areas to save money"*
- AI fetches transactions, categorizes, and generates natural-language replies

---

## 📈 Sample Use Cases

| Use Case                                 | Powered By                  |
|------------------------------------------|-----------------------------|
| Monthly Expense Pie Chart                | Setu Insights API           |
| Financial Summary (Income vs Expenses)   | Setu Insights + AI          |
| NLP Q&A on Transactions                  | OpenAI + Spring Boot        |
| Real-time Data Pull                      | Setu Webhooks               |

---

## 🔒 Security Considerations

- All consents are user-approved and time-bound
- FI Data stored securely with encryption
- Tokens and session IDs are hashed
- JWT authentication ensures safe access to endpoints

---

## 🧪 Sandbox Access & Demo

- Integrated with Setu Sandbox AA APIs
- Requesting sandbox access to Setu Insights APIs (ticket `#74194`)
- Demo backend + frontend available upon request or GitHub link

---


---

## 🚀 Workflow

1. **User Enters Phone Number**  
2. App calls **Setu `/consents` API** to initiate consent request  
3. Consent artefact is tracked using `/status`  
4. Once approved, **FI Data is fetched** using `/v3/fidata`  
5. AI Module (Optional): Analyze the transactions and visualize insights

---

## 🧪 API Endpoints

### 🔹 `/api/consents` (POST)  
Initiates a consent request to Setu AA

### 🔹 `/api/consents/status/{handle}` (GET)  
Checks the status of consent request

### 🔹 `/api/fetch` (POST)  
Fetches encrypted data from FIPs

### 🔹 `/api/v3/fidata` (POST)  
Submits FI data block to create a session

---

## 🔒 Security Notes

- Secrets like `clientId`, `clientSecret`, `apiKey` are stored securely using `application.properties` or `.env`
- Planning to integrate **OAuth2 login** for better access control and session trackin

---

## 📚 References

- [Setu Developer Docs](https://docs.setu.co)
- [Account Aggregator Framework - Sahamati](https://sahamati.org.in/)


---

## 👤 Author

**Prabal Pratap Singh**  
Student | Backend Developer | DevOps Enthusiast  
📧 940pps@gmail.com  
🌐 [LinkedIn](https://www.linkedin.com/in/prabal864/) | [GitHub](https://github.com/prabal864)

---

## 📌 Disclaimer

This project is developed for educational/demo purposes using the Setu sandbox environment. No real user data is being used or stored.





