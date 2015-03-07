var express = require('express');
var router = express.Router();
var User = require("../model/user");

var auth = require("../util/auth");

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});


router.post("/create", function(req, res) {
    var post = req.body;
    var username = post.username;
    var password = post.password;
    var email = post.email;

    User.findOne({$or : [{username : username}, {email : email}]}, function(err, user) {
        if(err) {
            throw err;
        }

        if(user) {
            if(user.username == username) {
                res.send({ecode : "username_exist"});
            } else {
                res.send({ecode : "email_exist"});
            }
        } else {

            var new_user = new User ({
                username : username
                , password : password
                , email : email
            });

            auth.register(new_user, function(err, user) {
                if(err) {

                    throw err;
                }

                auth.login(user, function(err, u) {
                    if(err) {

                        throw err;
                    }

                    res.send({ecode:"OK", token: u.token});
                });

            });

        }
    });
});

router.post("/update", auth.authToken(), function(req, res) {

    var post = req.body;
    var user = req.user;

    if(post.password) {
        user.password = password;
    }

    auth.login(user, function(err, u) {
        if(err) {
            throw err;
        }

        res.send({ecode : "OK", token : u.token});
    });

});

router.get("/get", auth.authToken(), function(req, res) {
    console.log(req.user.username);
    res.send({username : req.user.username, email : req.user.email});
});

router.post("/login", auth.authLocal(), function(req, res) {

    auth.login(req.user, function(err, u) {
        if(err) {
            throw err;
        }

        res.send({ecode:"OK", token: u.token});
    });

});

router.post("/logout", auth.authToken(), function(req, res) {

    //update token to invalidate client token
    auth.login(req.user, function(err, user) {

        if(err) {
            throw err;
        }

        res.send("logout");

    });
});

module.exports = router;
