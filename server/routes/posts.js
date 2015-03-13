/**
 * Created by imlyc on 3/7/15.
 */

var router = require("express").Router();
var auth = require("../util/auth");
var PicPost = require("../model/pic_post");

//get list of all the posts
router.get("/get", function(req, res) {
    var cond = {};

    if("id" in req.query) {
        cond._id = req.query.id;
    }

    PicPost.find(cond).populate("user comments")
        .exec(function(err, posts) {
            if(err) {
                throw err;
            }

            console.log(posts);
            res.send("OK");
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

    //console.log(image_file);


    var pic_post = new PicPost({
        user : user._id
        , desc : desc
        //, image : image_file
        , comments : []
    });

    pic_post.image.file = image_file;

    console.log(pic_post);

    pic_post.save(function(err, p) {
        if(err) {
            throw err;
        }

        console.log(p);

        res.send({"post" : p});

    });

});

module.exports = router;
