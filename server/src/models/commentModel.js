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
    user: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('comment', commentSchema);