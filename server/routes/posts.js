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

module.exports = router;
