import express from "express";
import bodyParser from "body-parser";
import { Request, Response } from "express";
import http from "http";
import cookieParser from "cookie-parser";
import cors from "cors";
import compression from "compression";


const app =express();

app.use(cors({
    credentials:true,
}))

app.use(compression());
app.use(cookieParser());
app.use(bodyParser.json());