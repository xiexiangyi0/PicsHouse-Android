/**
 * Created by imlyc on 3/7/15.
 */

var router = require("express").Router();
var auth = require("../util/auth");
var PicPost = require("../model/pic_post");
var Comment = require("../model/comment");

var mydebug = require("../util/debug");

function getQueryFromReq(req) {
    var cond = {};

    if("id" in req.query) {
        cond._id = req.query.id;
    }

    if ("user_id" in req.query) {
        cond.user_id = req.query.user_id;
    }

    if ("user_id" in req.body) {
        cond.user_id = req.body.user_id;
    }

    return cond;
}

//get list of all the posts
router.get("/get", function(req, res, next) {
    var cond = getQueryFromReq(req);

    PicPost.find(cond).populate("user_id comments")
        .exec(function(err, posts) {
            if(err) {
                return next(err);
            }

            var jarr = [];

            posts.forEach(function(p, idx, arr) {
                var jdata = p.getJsonPublic(p.user_id.getJsonPublic());
                jarr.push(jdata);
            });

            //console.log(jarr);
            res.send({"posts" : jarr});
        });
});

router.get("/getthumbnail", function (req, res, next) {
    var cond = getQueryFromReq(req);

    PicPost.find(cond, function(err, posts) {
        if (err) {
            return next(err);
        }

        var jarr = [];

        posts.forEach(function(p, idx, arr) {
            jarr.push(p.getJsonPublic({id: p.user_id}));
        });

        res.send({"posts": jarr});
    });
});

//post a picture
router.post("/create", auth.authToken(), function(req, res, next) {
    var post = req.body;
    var user = req.user;
    var files = req.files;
    var desc = post.desc || "";

    var image_file = files.image;

    if(image_file) {
        image_file = image_file[0];
    } else {
        res.status(403);
        res.send({ecode : "syntax_error"});
        return;
    }

//    console.log(image_file);


    var pic_post = new PicPost({
        user_id : user._id
        , desc : desc
        , comments : []
    });

    pic_post.setFile(image_file, function(err, pp) {
        pp.save(function(err, p){
            if(err) {
                return next(err);
            }

            res.send({"post" : p.getJsonPublic(req.user.getJsonPublic())});
        });
    });

});

// delete a post
router.post("/delete", auth.authToken(), function(req, res, next) {
    var post = req.body;
    var user = req.user;

    var post_id = post.post_id || "";

    mydebug.log("delete post " + post_id);

    PicPost.findById(post_id, function (err, p) {
        if (err) {
            mydebug.log(err);
            return next(err);
        }

        if (!p) {
            // no post found
            mydebug.log("no post");
            res.status(403);
            res.send({ecode: "invalid_object"});
            return;
        }

        mydebug.log("p.user_id="+p.user_id);
        mydebug.log("user._id="+user._id);

        if (!p.user_id.equals(user._id)) {
            mydebug.log("incorrect user");
            res.status(403);
            res.send({ecode: "invalid_permission"});
            return;
        }

        p.remove(function (err, p) {
            if (err) {
                return next(err);
            }

            mydebug.log("removed");

            res.send({});
        })


    });
});

//comment a post
router.post("/comment/create", auth.authToken(), function(req, res, next) {
    var post = req.body;
    var user = req.user;

    var pic_post = post.post || -1;
    var content = post.content || "";
    //TODO: check reply_to. undefined value means commenting on post
    var reply_to = post.reply_to;
    
    var comment = new Comment({
        user_id : user.id
        , content : content
        , pub_date : Date.now()
        , reply_to : reply_to
        , post : pic_post
    });

    PicPost.findById(pic_post, function(err, p) {
        if(err) {
            return next(err);
        }

        if(p) {
            comment.save(function(err, c) {
                if (err) {
                    return next(err);
                }
                p.comments.push(c);
                p.save(function(err, post_ret) {
                    if (err) {
                        return next(err);
                    }

                    res.send({comment: c});

                });
            });
        } else {
            res.status(403);
            res.send({ecode: "invalid_object"});

        }
    });
});

module.exports = router;
