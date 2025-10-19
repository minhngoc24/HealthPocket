import { EmailClient } from "@azure/communication-email";
import dotenv from "dotenv";
dotenv.config();

const emailClient = new EmailClient(process.env.AZURE_EMAIL_CONNECTION_STRING);

export const sendEmailOtp = async (email, otp) => {
  try {
    const message = {
      senderAddress: "DoNotReply@3b4cbce8-923f-45d1-8e0f-f850fb86f4ca.azurecomm.net", // 👈 your verified domain
      content: {
        subject: "Your HealthPocket Verification Code",
        plainText: `Your HealthPocket verification code is ${otp}`,
      },
      recipients: {
        to: [{ address: email }],
      },
    };

    const poller = await emailClient.beginSend(message);
    const result = await poller.pollUntilDone();
    console.log("Email sent successfully:", result);
  } catch (error) {
    console.error("Error sending email:", error);
  }
};
