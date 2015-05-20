var express = require('express');
var router = express.Router();
var user = require("./users");
var post = require("./posts");


//log
router.use('/', function(req, res, next) {

    var oend = res.end;
    res.end = function(chunk, encoding) {
        res.end = oend;
        res.end(chunk, encoding);

        console.log("=== After all handlers ===");
        //console.log(this);
        console.log("=== End ===");
    };

  console.log("=== Before all handlers ===");
  //console.log(req);
  console.log("=== Hanlders ===");

  next();

});

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.use('/user', user);
router.use("/post", post);

module.exports = router;
