let mongoose = require('mongoose');
let commentSchema = mongoose.Schema({
    movieId: {
        type: String,
        required: true
    },
    content: {
        type: String,
        required: true
    },
    userId: {
        type: String,
        required: true
    },
    name: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('comment', commentSchema);