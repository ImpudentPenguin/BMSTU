const ObjectID = require('mongodb').ObjectID;

module.exports = function (app, db) {
    app.get('/serials', (req, res) => {
        db.collection('serials').find({}).toArray(function (err, result) {
            if (err) {
                res.send(err);
            } else {

                res.send(JSON.stringify(result));
            }
        })
    });

    app.get('/serials/:id', (req, res) => {
        const id = req.params.id;
        const details = {'_id': new ObjectID(id)};
        db.collection('serials').findOne(details, (err, item) => {
            if (err) {
                res.send({'error': 'An error has occurred'});
            } else {
                res.send(item);
            }
        });
    });

    app.post('/serials', (req, res) => {
        const serial = {name: req.body.name, info: req.body.info};
        db.collection('serials').insert(serial, (err, result) => {
            if (err) {
                res.send({'error': 'An error has occurred'});
            } else {
                res.send({
                    message: "Successful addition"
                });
            }
        });
    });

    app.delete('/serials/:id', (req, res) => {
        const id = req.params.id;
        const details = {'_id': new ObjectID(id)};
        db.collection('serials').remove(details, (err, item) => {
            if (err) {
                res.send({'error': 'An error has occurred'});
            } else {
                res.send({
                    message: 'Serial ' + id + ' deleted!'
                });
            }
        });
    });

    app.put('/serials/:id', (req, res) => {
        const id = req.params.id;
        const details = {'_id': new ObjectID(id)};
        const serial = {name: req.body.name, info: req.body.info};
        db.collection('serials').update(details, serial, (err, result) => {
            if (err) {
                res.send({'error': 'An error has occurred'});
            } else {
                res.send({
                    message: "Successful change"
                });
            }
        });
    });

    app.options('/', function (req, res) {
        res.header('Allow', 'GET, POST, PUT, DELETE, OPTIONS');
        res.send("");
    });
};