import express, { Request, Response } from "express";
import { getAllProducts } from "../service/productService.js";

const router = express.Router();

router.route("/").get(async (req: Request, res: Response) => {
    try {
        const products = await getAllProducts();
        res.json(products);
    } catch (error) {
        console.error(`Error: ${(error as Error).message}`);
        res.status(500).json({
            message: (error as Error).message,
        });
    }
});

export default router;