const express = require('express'),
    expressLogging = require('express-logging'),
    logger = require('logops');
const path = require("path");
const app = express();
const port = 8004;

app.use(expressLogging(logger));

app.use(
    express.static(__dirname + "/../../static", {
        index: false,
    })
);

app.get("/", function (req, res) {
    res.sendFile(path.join(__dirname + "/../../static/html/server2.html"));
});

app.get('/temp', function (req, res) {
    res.sendFile(path.join(__dirname + '/../../static/html/temp2.html'));
})

app.listen(port, 'localhost', () => {
    console.log('We are live on ' + port);
});