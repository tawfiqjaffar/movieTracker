let express = require('express');
let app = express();
let routes = require('./routes');
let makeResponse = require('./responses');
var port = process.env.PORT || 8080;
let mongoose = require('mongoose');
let cors = require('cors');

app.use(cors());
mongoose.connect('mongodb://localhost:27017/movieApp', {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

var db = mongoose.connection;

if (!db) {
    console.error('Error connecting to db');
} else {
    console.log('DB connected successfully');
}
app.use('/api', routes);

app.use(function (req, res) {
    res.status(404).json(
        makeResponse({
            msg: 'route not found'
        }, 404)
    );
});

app.listen(port, () => {
    console.log(`Running on port ${port}`);
});

