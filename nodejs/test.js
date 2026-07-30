// const multiply=(a,b)=>a*b;

// console.log(multiply(3,4));

// const math=require("./math");

// console.log(math.add(2,4));

const express = require('express');

const app =express();

app.get('/',(req,res)=>res.send("Learning Nodejs"));

app.listen(console.log("Server is running "), 8080)