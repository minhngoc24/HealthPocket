import express from "express";
import cors from "cors";
import helmet from "helmet";
import dotenv from "dotenv";
import { sendEmailOtp } from "./src/services/otpService.js";

dotenv.config();

const app = express();
app.use(cors());
app.use(helmet());
app.use(express.json());

// ✅ Health check route
app.get("/api/health", (req, res) => res.json({ ok: true }));

// ✅ In-memory OTP store
let otpStore = {};

// ✅ Send OTP
app.post("/api/request-otp", async (req, res) => {
  try {
    const { email } = req.body;
    const otp = Math.floor(100000 + Math.random() * 900000).toString();

    otpStore[email] = otp;
    console.log(`Generated OTP for ${email}: ${otp}`);

    await sendEmailOtp(email, otp);

    res.status(200).json({ ok: true, message: "OTP sent to your email" });
  } catch (error) {
    console.error("Error sending OTP:", error);
    res.status(500).json({ ok: false, message: "Failed to send OTP" });
  }
});

app.post("/api/verify-otp", (req, res) => {
  const { email, otp } = req.body;
  if (otpStore[email] === otp) {
    res.json({ ok: true, token: "mock_token_123" });
  } else {
    res.status(400).json({ ok: false, message: "Invalid OTP" });
  }
});

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => console.log(`API running on port ${PORT}`));
