var express = require('express');
var router = express.Router();
var User = require("../model/user");

var auth = require("../util/auth");
var mydebug = require("../util/debug");
var errorHandler = require("../util/error").errorHandler;
var errorSyntax = require("../util/error").errorSyntax;
var errorNoObj = require("../util/error").errorNoObj;
/* GET users listing. */

router.post("/create", function(req, res, next) {
    var post = req.body;
    var username = post.username;
    var password = post.password;
    var email = post.email;
    var gender = post.gender;
    var files = req.files;

    if (!username || !password) {
        errorSyntax(res);
        return;
    }

    var avatar_file = undefined;

    if (files && files.image) {
        avatar_file = files.image[0];
    }

    mydebug.log("avatar_file = " + avatar_file);

    User.findOne({$or : [{username : username}, {email : email}]}, function(err, user) {
        if(err) {
            return errorHandler(err, next);
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

            var onUserRegistered = function(err, user) {
                if (err) {
                    return errorHandler(err, next);
                }

                auth.login(user, function(err, u) {
                    if (err) {
                        return errorHandler(err, next);
                    }

                    res.send({token : u.token});
                });
            };

            if (avatar_file) {
                new_user.setFile(avatar_file, function(err, user) {
                    if (err) {
                        auth.register(new_user, onUserRegistered);
                    } else {
                        auth.register(user, onUserRegistered);
                    }
                });
            } else {
                auth.register(new_user, onUserRegistered);
            }
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
            errorHandler(err, next);
        }

        res.send({token : u.token});
    });

});

router.post("/avatar/update", auth.authToken(), function(req, res, next) {
    var files = req.files;
    var avatar_file = files.image;

    if (avatar_file) {
        avatar_file = avatar_file[0];
    } else {
        errorSyntax(res);
        return;
    }

    User.findById(req.user._id, function(err, user) {
        if (err) {
            return errorHandler(err, next);
        }

        if (user) {
            user.setFile(avatar_file, function(err, u) {
                u.save(function(err, u) {
                    if (err) {
                        errorHandler(err, next);
                    }
                    res.send({user : u.getJsonPublic()});
                });
            });
        } else {
          errorNoObj(res);
        }
    });
});

router.get("/get", auth.authToken(), function(req, res) {
    console.log(req.user.username);
    res.send({user : req.user.getJsonPublic()});
});

router.post("/login", auth.authLocal(), function(req, res) {

    auth.login(req.user, function(err, u) {
        if(err) {
            return errorHandler(err, next);
        }

        res.send({token: u.token});
    });

});

router.post("/fblogin", auth.authFB(), function(req, res, next) {
    console.log(req.body);

    console.log(req.user);

    res.send({});
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
