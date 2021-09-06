const express = require('express'),
    expressLogging = require('express-logging'),
    logger = require('logops');
const MongoClient = require('mongodb').MongoClient;
const bodyParser = require('body-parser');
const db = require('./config/db');
const path = require("path");
const app = express();
const firstPort = 8000;
const secondPort = 8001;
const thirdPort = 8002;

app.use(expressLogging(logger));

app.use(
    express.static(__dirname + "/../static", {
        index: false,
    })
);

app.get("/", function (req, res) {
    res.header("Content-type", "text/html");
    res.sendFile(path.join(__dirname + "/../static/html/hack.txt"));
});

app.get('/hack', function (req, res) {
    res.sendFile(path.join(__dirname + '/../static/html/index.html'));
})

app.use(bodyParser.json({extended: true}));

MongoClient.connect(db.url, (err, database) => {
    const db = database.db("webLab");

    if (err) return console.log(err)
    require('./app/routes')(app, db);
})

app.listen(firstPort, 'localhost', () => {
    console.log('We are live on ' + firstPort);
});

app.listen(secondPort, 'localhost', () => {
    console.log('We are live on ' + secondPort);
});

app.listen(thirdPort, 'localhost', () => {
    console.log('We are live on ' + thirdPort);
});