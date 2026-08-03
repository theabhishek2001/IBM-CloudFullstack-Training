import IProduct from '../model/product.js';
import Product from '../schema/productSchema.js';


export const getAllProducts = async () : Promise<IProduct[]> => {
    return await Product.find();
}