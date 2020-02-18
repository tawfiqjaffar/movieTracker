let User = require('../models/userModel');
let responseMaker = require('../responses');
let hash = require('../hash');

exports.index = (req, res) => {
    User.find({}, (err, users) => {
        if (err) {
            res.status(500).json(responseMaker({
                msg: 'could not retrieve users'
            }, 500));
        } else {
            res.status(200).json(responseMaker({
                msg: 'success',
                users: [...users]
            }));
        }
    });
};

exports.findOne = (req, res) => {
    User.findOne({ _id: req.headers.id }, (err, found) => {
        if (err) {
            res.status(404).json(responseMaker({
                msg: `could not find user ${req.headers.id}`
            }, 404));
        } else {
            res.status(200).json(responseMaker({
                msg: 'success',
                user: { ...found._doc }
            }));
        }
    });
};

exports.new = async (req, res) => {
    var user = new User();
    const hashed = await hash.passwordToHash(req.headers.password);
    user.username = req.headers.username;
    user.password = hashed;

    user.save((err) => {
        if (err) {
            console.error(err);
            res.status(409).json(responseMaker({
                msg: 'error, could not create user'
            }, 500));
        } else {
            res.status(200).json(responseMaker({
                msg: 'success',
                user: user
            }));
        }
    });
};

exports.login = async (req, res) => {
    let user = User.findOne({
        username: req.headers.username
    }, async (err, found) => {
        if (err || !found) {
            console.error(err);
            res.status(404).json(responseMaker({
                msg: `error, could find user ${user.usernamee}`
            }, 404));
        } else {
            console.log(found);
            let passwordMatch = await hash.hashToPassword(
                req.headers.password, found.password);
            if (!passwordMatch) {
                res.status(401).json(responseMaker({
                    msg: `error, wrong password for ${user.usernamee}`
                }, 401));
            } else {
                res.status(200).json(responseMaker({
                    msg: 'success',
                    user: found
                }));
            }
        }
    });
};

exports.favUnfav = (req, res) => {
    User.findOne(
        { _id: req.headers.id },
        (err, found) => {
            if (err) {
                console.error(err);
                res.status(404).json(responseMaker({
                    msg: `error, could find user ${req.headers.id}`
                }, 404));
            } else {
                let favList = found.favorites;
                let movieId = req.headers.movieid;
                let action = 'favorited';
                let index = favList.indexOf(movieId);
                if (index !== -1) {
                    action = 'unfavorited';
                    favList.splice(index, 1);
                } else {
                    favList.push(movieId);
                }
                found.favorites = favList;
                found.save((err) => {
                    if (err) {
                        res.status(500).json(responseMaker({
                            msg: 'error',
                            error: err
                        }, 500));
                    } else {
                        res.status(200).json(responseMaker({
                            msg:
                                `success, movie ${movieId} has been ${action} for user ${req.headers.id}`,
                            action: action,
                            user: found
                        }));
                    }
                });
            }
        });
};