let Comment = require('../models/commentModel');
let responseMaker = require('../responses');

exports.index = (req, res) => {
    Comment.find({}, (err, comments) => {
        if (err){
            res.status(500).json(responseMaker({
                msg: 'could not retrieve comments'
            }, 500));
        } else {
            res.status(200).json(responseMaker({
                msg: 'success',
                comments: [...comments]
            }));
        }
    });
};

exports.new = (req, res) => {
    let comment = new Comment();
    comment.content = req.headers.content;
    comment.user = req.headers.user;
    comment.movieId = req.headers.movieid;

    comment.save((err) => {
        if (err) {
            res.status(500).json(responseMaker({
                msg: 'error, could not create comment',
                error: err
            }, 500));
        } else {
            res.status(200).json(responseMaker({
                msg: 'success',
                comment: comment
            }));
        }
    });
};