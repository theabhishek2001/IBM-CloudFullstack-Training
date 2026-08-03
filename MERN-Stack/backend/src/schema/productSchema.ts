import mongoose from "mongoose";

import IProduct from "../model/product.js";

const productSchema = new mongoose.Schema<IProduct>({
    
    name: { type: String, required: true },
    description: { type: String, required: true },
    price: { type: Number, required: true },
    productCode: { type: String, required: true },
    rating: { type: Number, required: true },
    image_url: { type: String, required: true },
    productAvailable: { type: String, required: true }
});


const Product = mongoose.model<IProduct>("Product", productSchema);

export default Product;