const os = require('os');
const fs = require('fs').promises;
const http = require('http');
const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');

 function getHTMLFileSync(filename) {
    try {
        const data =  fs.readFile(
            path.join(__dirname, '..', filename),
            'utf8'
        );
        // console.log(data);
        return data;
    } catch (err) {
        console.log(err);
        return "Error loading file";
    }
}

// const myServer = http.createServer(async (req, res) => {
//     if (req.url === '/start') {
//         res.writeHead(200, { 'Content-Type': 'text/html' });
//         res.end(await getHTMLFileSync('login.html'));
//     } else {
//         res.writeHead(404);
//         res.end("Page Not Found");
//     }
// });

// myServer.listen(8000, () => console.log("Started Server"));


const app = express();

app.use