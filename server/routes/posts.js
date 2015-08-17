/**
 * Created by imlyc on 3/7/15.
 */

var router = require("express").Router();
var auth = require("../util/auth");
var PicPost = require("../model/pic_post");
var Comment = require("../model/comment");

//get list of all the posts
router.get("/get", function(req, res) {
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

    PicPost.find(cond).populate("user_id comments")
        .exec(function(err, posts) {
            if(err) {
                throw err;
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

//post a picture
router.post("/create", auth.authToken(), function(req, res) {
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
                throw err;
            }

            //console.log(p);

            res.send({"post" : p.getJsonPublic(req.user.getJsonPublic())});
        });
    });

});

//comment a post
router.post("/comment/create", auth.authToken(), function(req, res) {
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
            throw err;
        }

        if(p) {
            comment.save(function(err, c) {
                if (err) {
                    throw err;
                }
                p.comments.push(c);
                p.save(function(err, post_ret) {
                    if (err) {
                        throw err;
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
