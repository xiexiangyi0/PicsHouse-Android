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
    var gender = post.gender;


    console.log(post);

    User.findOne({$or : [{username : username}, {email : email}]}, function(err, user) {
        if(err) {
            throw err;
        }

        if(user) {
            res.status(403);
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
                , gender : gender
            });

            auth.register(new_user, function(err, user) {
                if(err) {

                    throw err;
                }

                auth.login(user, function(err, u) {
                    if(err) {

                        throw err;
                    }

                    res.send({token: u.token});
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

        res.send({token : u.token});
    });

});

router.get("/get", auth.authToken(), function(req, res) {
    console.log(req.user.username);
    res.send({user : req.user.getJsonPublic()});
});

router.post("/login", auth.authLocal(), function(req, res) {

    auth.login(req.user, function(err, u) {
        if(err) {
            throw err;
        }

        res.send({token: u.token});
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

router.get("/facebook", auth.authFB(), function(req, res) {
    console.log(req.user);
    res.send("OK");
});

//check existance
router.post("/exist", function(req, res) {

    var q_arr = [];

    if(req.body.username) {
        q_arr.push({username : req.body.username});
    }

    if(req.body.email) {
        q_arr.push({email : req.body.email});
    }

    console.log(q_arr);

    User.findOne({$or:q_arr}, function(err, user) {

        username = req.body.username || "";
        email = req.body.email || "";

        console.log(username);
        console.log(email);
        console.log(user);

        if(user) {
            if(user.username == username) {
                res.send({ecode : "username_exist"});
            } else if(user.email == email) {
                res.send({ecode : "email_exist"})
            } else {
                throw "db error";
            }
        } else {
            res.send({ecode : "OK"});
        }

    });

});

module.exports = router;
