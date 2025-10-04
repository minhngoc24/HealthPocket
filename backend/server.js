import express from "express";
import cors from "cors";
import helmet from "helmet";
import dotenv from "dotenv";
dotenv.config();

const app = express();
app.use(helmet());
app.use(cors());
app.use(express.json({ limit: "5mb" }));

// test route
app.get("/health", (req, res) => res.json({ ok: true }));

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => console.log(`API running`));
