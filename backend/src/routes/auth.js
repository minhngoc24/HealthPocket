import { Router } from "express";
const router = Router();

// Mock saves OTP in RAM (dev): production use DB/ Redis
const otpStore = new Map();
// request OTP
router.post("/request-otp", (req, res) => {
  const { email } = req.body;
  const code = ("" + Math.floor(100000 + Math.random() * 900000));
  otpStore.set(email, { code, exp: Date.now() + 5*60*1000 });
  // TODO: send real emails
  console.log("OTP for", email, "=", code);
  res.json({ ok: true });
});
// verify OTP
router.post("/verify-otp", (req, res) => {
  const { email, code } = req.body;
  const item = otpStore.get(email);
  if (!item || item.exp < Date.now() || item.code !== code) {
    return res.status(400).json({ ok: false, message: "OTP invalid" });
  }
  // mock role: patient/hospital
  const role = email.endsWith("@hospital.com") ? "hospital" : "patient";
  const token = "MOCK_TOKEN_" + role;
  res.json({ ok: true, token, role });
});
export default router;
