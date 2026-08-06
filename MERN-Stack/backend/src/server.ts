import express from "express";
import productRouter from "./router/productRouter.js";
import { connectDB } from "./config/dbConfig.js";
import cors from "cors";

const app = express();
const PORT = process.env.PORT || 5000;

// Enable CORS
app.use(
  cors({
    origin: "http://localhost:5173", // Vite frontend
    methods: ["GET", "POST", "PUT", "DELETE"],
    credentials: true,
  })
);

app.use(express.json());

app.use("/api/products", productRouter);

connectDB(
  "mongodb+srv://abhishek26kashyap_db_user:Xc53Y6Silqrbo3tv@expresscluster.bfaaxje.mongodb.net/productsdb"
)
  .then(() => {
    console.log("Connected to the DB");

    app.listen(PORT, () => {
      console.log(`Server is running on port ${PORT}`);
    });
  })
  .catch((error: Error) => {
    console.log("Error connecting to db", error);
    process.exit(1);
  });