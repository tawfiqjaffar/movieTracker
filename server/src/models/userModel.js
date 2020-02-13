let mongoose = require('mongoose');

let userSchema = mongoose.Schema({
    username: {
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    favorites: {
        type: [String],
        required: false
    }
});
userSchema.plugin(require('mongoose-unique-validator'));
module.exports = mongoose.model('user', userSchema);