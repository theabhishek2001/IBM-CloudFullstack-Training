
import {Document,ObjectId} from 'mongoose';

export default interface IProduct extends Document{
    id: ObjectId;
    name: string;
    description: string;
    price: number;
    productCode: string;
    rating: number;
    image_url: string;
    productAvailable: string;
}