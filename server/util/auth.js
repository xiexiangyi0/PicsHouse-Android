var passport = require("passport")
    , BearerStrategy = require("passport-http-bearer").Strategy
    , LocalStrategy = require("passport-local").Strategy
    , jwt = require("jsonwebtoken")
    , JWT_SECRET = "sEcReT"
    , FBTokenStrategy = require("passport-facebook-token").Strategy
    , app_config = require("../config")
;

//passport
passport.use(
    new LocalStrategy(function(username, password, done) {
        require("../model/user").findOne({username : username}, function(err, user) {

            if(err) {
                return done(err);
            }

            if(user) {
                user.comparePassword(password, function(err, is_match) {
                    if(err) {
                        return done(err);
                    }

                    if(is_match) {
                        return done(null, user);
                    } else {
                        return done(null, false, {message : "invalid_password"});
                    }
                });
            } else {
                return done(null, false, {message : "invalid_username"});
            }

        });
    }));

passport.use(new BearerStrategy(function(token, done) {

        require("../model/user").findOne({token : token}, function(err, user) {
            if(err) {
                return done(err);
            }

            if(user) {
                return done(null, user);
            } else {
                return done(null, false, {message : "invalid_token"});
            }
        });
    })
);

passport.use(
    new FBTokenStrategy({
        clientID : app_config.FB_CLIENT_ID
        , clientSecret : app_config.FB_CLIENT_SECRET
    }
    , function(accessToken, refreshToken, profile, done) {
            var User = require("../model/user");
            //console.log(profile);
            User.findOne({"facebook.id" : profile.id}, function(err, user) {

                if(err) {
                    return done(err);
                }

                if(user) {
                    user.token = accessToken;
                } else {

                    var username = profile.username || profile.displayName.replace(" ", "_");
                    var password = profile.id + (new Date()).getTime();
                    var email = profile.emails[0].value || (username + "@" + profile.provider);
                    user = new User({
                        username : username
                        , password : password
                        , email : email
                        , token : accessToken
                        , facebook : {
                            id : profile.id
                            , first_name : profile.name.givenName
                            , last_name : profile.name.familyName
                        }
                    });


                }

                user.save(function(err, u) {
                    return done(null, u);
                });

            });
    }
));

//middleware
var router = require("express").Router();

router.use(passport.initialize());

router.all("/", function(req, res, next) {

    next();
});

//private
function _getJWTByUser(user) {
    var info = {
        username : user.username
        , password : user.password
        , today : (new Date()).getTime()
    };
    return jwt.sign(info, JWT_SECRET);
}

function _passportAuthWrapper(name, option) {
    return function(req, res, next) {
        (passport.authenticate(name, option, function (err, user, info) {
            if (err) {
                return next(err);
            }

            if(user) {
                req.user = user;
                return next(null, user);
            } else {
                res.status(401);
                if(info && info.message) {
                    res.send({"ecode": info.message});
                } else {
                    res.send({"ecode" : "unauthorized"});
                }
            }
        }))(req, res, next);
    }
}

//public
//User -> (err -> User -> void) -> void
function register(user, cb) {
    user.token = _getJWTByUser(user);
    user.save(function(err, u) {
        if(err) {
            return cb(err);
        } else {
            return cb(null, u);
        }
    });
}

//User -> (err -> User -> void) -> void
function login(user, cb) {

    var token = _getJWTByUser(user);
    user.token = token;
    user.save(function(err, u) {

        if(err) {
            return cb(err);
        } else {
            return cb(null, u);
        }
    });
}

function authLocal() {
    return _passportAuthWrapper("local", {session: false});
}

function authToken() {
    return _passportAuthWrapper("bearer", {session:false});
}

function authFB() {
    return _passportAuthWrapper("facebook-token", {session:false});
}

module.exports = {
    register : register
    , login : login
    , middleware : router
    , authLocal : authLocal
    , authToken : authToken
    , authFB : authFB
};
