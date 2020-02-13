let router = require('express').Router();
let makeResponse = require('./responses');
let userController = require('./controllers/userController');

router.get('/info', (req, res) => {
    res.status(200).json(makeResponse({
        msg: 'success'
    }));
});

router.route('/users/all')
    .get(userController.index);

router.route('/users/register')
    .post(userController.new);

router.route('/users/login')
    .post(userController.login);

router.route('/users/favorite')
    .put(userController.favUnfav);

module.exports = router;