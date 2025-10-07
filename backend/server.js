import express from "express";
import cors from "cors";
import helmet from "helmet";

const app = express();
app.use(cors());
app.use(helmet());
app.use(express.json());

// Test route
app.get("/health", (req, res) => res.json({ ok: true }));

// Mock OTP
let otpStore = {};

app.post("/api/request-otp", (req, res) => {
  const { email } = req.body;
  const otp = Math.floor(100000 + Math.random() * 900000).toString();
  otpStore[email] = otp;
  console.log(`OTP for ${email}: ${otp}`);
  res.json({ ok: true, message: "OTP sent" });
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
app.listen(PORT, () => console.log(`✅ API running on port ${PORT}`));

