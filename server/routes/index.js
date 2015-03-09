var express = require('express');
var router = express.Router();
var user = require("./users");
var post = require("./posts");

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.use('/user', user);
router.use("/post", post);

module.exports = router;
