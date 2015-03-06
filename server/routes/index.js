var express = require('express');
var router = express.Router();
var user = require("./users");

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.use('/user', user);

module.exports = router;
