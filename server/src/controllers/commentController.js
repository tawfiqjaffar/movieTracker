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
    comment.userId = req.headers.id;
    comment.movieId = req.headers.movieid;
    comment.name = req.headers.name;

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

exports.findByMovie = (req, res) => {
    Comment.find(
        {movieId: req.headers.movieid},
        (err, found) => {
            if (err || !found) {
                res.status(404).json(responseMaker({
                    msg: 'error, could not create comment',
                    error: err
                }, 404));
            } else {
                res.status(200).json(responseMaker({
                    msg: 'success',
                    data: [...found]
                }));
            }
        }
    );
};