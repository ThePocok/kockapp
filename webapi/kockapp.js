var express = require('express');
var app = express();
var mysql = require('mysql');

var server = app.listen(3092, '192.168.1.157', function () {
    var host = server.address().address
    var port = server.address().port

    console.log("app listening at http://%s:%s", host, port)
});

app.use(express.json());
app.use(express.urlencoded({extended: false}));


app.get('/records', function (req, res) {
    var connection = connectToDatabase();

    connection.connect();
    connection.query('SELECT COUNT(device_id) AS rank FROM Kockapp.Result WHERE cube_size = ' + req.query.cube_size + ' AND result < ' + req.query.result + ';', function(error, results, fields) {
        if (error) {
            console.log(error);
        }

        res.end(JSON.stringify(results[0]));
    });
    connection.end()
});

app.post('/records', async function (req, res) {
    var result = await checkIfExists(req);

    var connection = connectToDatabase();

    connection.connect();

    if (result.length === 0) {
        connection.query('INSERT INTO Kockapp.Result(device_id, cube_size, result) VALUES("' + req.body.device_id
            + '", ' + req.body.cube_size + ', ' + req.body.result + ');', function(error, results, fields) {
            if (error) {
                console.log(error);
            }
        });
    } else if (result[0].result > req.body.result) {
        connection.query('UPDATE Kockapp.Result SET result = ' + req.body.result + ' WHERE device_id = "' + req.body.device_id
            + '" AND cube_size = ' + req.body.cube_size + ';', function(error, results, fields) {
            if (error) {
                console.log(error);
            }
        });
    }

    connection.end()

    res.end("Success!");
});

app.delete('/records/:device_id', function (req, res) {
    var connection = connectToDatabase();

    connection.connect();
    connection.query('DELETE FROM Kockapp.Result WHERE device_id = ' + req.params.device_id + ';', function(error, results, fields) {
            if (error) {
                console.log(error);
            }

            res.end("Success!");
        });
        connection.end()
});

function checkIfExists(req) {
    return new Promise((resolve, reject) => {
        var connection = connectToDatabase();

        connection.connect();
        var result;
        connection.query('SELECT * FROM Kockapp.Result WHERE device_id = "' + req.body.device_id
            + '" AND cube_size = ' + req.body.cube_size + ';', function(error, results, fields) {
            if (error) {
                console.log(error);
                reject(err);
            }
            console.log(results);
            resolve(results);
        });
        connection.end();
    });
}

function connectToDatabase() {
    return mysql.createConnection({
        host: 'localhost',
        user: 'root',
        password: 'XXXXXXXX',
        database: 'Kockapp'
    });
}
