/**
 * Created by imlyc on 3/6/15.
 */

function webappAjax(options) {
    var token = localStorage.getItem("token");
    if(token) {
        options.headers = {"Authorization": "Bearer " + token};
    }

    return $.ajax(options);
}

function mydebug_info(str) {
    console.log("[INFO] " + str);
}


function onClickSignup() {
    var username = $("#register input[type=text]").val();
    var email = $("#register input[type=email]").val();
    var password = $("#register input[type=password]").val();

    console.log(username);
    console.log(email);
    console.log(password);

    $.ajax({
        url : "/user/create/"
        , type : "POST"
        , data : {
            username : username
            , email : email
            , password : password
            , gender : 0
        }
    }).done(function(data) {
        console.log(data);
        localStorage.setItem("token", data.token);
    }).fail(function() {
        console.log("Fail");
    });
}

function onClickGetProfile() {
    var token = localStorage.getItem("token");
    if(token) {
        $.ajax({
            url : "/user/get/"
            , headers : {"Authorization": "Bearer " + token}
        }).done(function(data) {

            $("#profile .screen").html("user name = " + data.user.username + " email = " + data.user.email);
            localStorage.setItem("user_id", data.user.id);
            console.log("id = " + data.user.id);

        }).fail(function() {
            console.log("fail");
        });
    } else {
        console.log("no token");
    }
}

function onClickLogin() {
    var username = $("#login input[type=text]").val();
    var password = $("#login input[type=password]").val();

    $.ajax({
        url : "/user/login/"
        , type : "POST"
        , data : {
            username : username
            , password : password
        }
    }).done(function(data) {
        localStorage.setItem("token", data.token);
        console.log("Success");
    }).fail(function(xhr) {


        console.log("Fail");
        console.log(xhr);
    })
}

function onClickLogout() {
    var token = localStorage.getItem("token");
    $.ajax({
        url : "/user/logout/"
        , type : "POST"
        , headers : {"Authorization": "Bearer " + token}

    }).done(function() {

    }).fail(function() {
        console.log("Fail");
    });


}

function onClickFBTest() {
    $.ajax({
        url : "/user/facebook/"
        , type : "GET"
        , access_token : "abcdef"

    }).done(function() {

    }).fail(function() {
        console.log("Fail");
    });
}

function onClickUploadImage() {

    var form = $("#upload_image form")[0];
    var form_data = new FormData(form);

    console.log(form_data);

    form_data.append("desc", "lalalal");

    webappAjax({
        url : "/post/create/"
        , type : "POST"
        , cache : false
        , contentType : false
        , processData : false
        , data : form_data

    }).done(function(data) {

        $("#upload_image img").attr("src", data.post.image.src);
        console.log(data);

    }).fail(function() {
        console.log("fail");
    });
}

function onClickGetImage() {
    webappAjax({
        url : "/post/get/"
        , type : "GET"
//        , data: {
//            user_id: localStorage.getItem("user_id")
//        }
    }).done(function(data) {
        var posts = data.posts;
        var image_list = $("#get_image div");

        console.log(data);

        for (var i=0; i<posts.length; i++) {
            console.log(posts[i]);
            var html = "<p>Image " + i + ": " + posts[i].desc + "</p>" +
                       "<img src=" + posts[i].image.src + ">" +
                       "<button onclick='onClickDeleteImage(\"" + posts[i].id + "\")'>Delete Image</button>" +
                       "<p>Comments: </p>";

            for (var j=0; j<posts[i].comments.length; j++) {
                html += "<p>" + j + ". " + posts[i].comments[j].content + "</p>";
                html += "<p>from " + posts[i].comments[j].user.username + "</p>";
            }

            html += "<button onclick='onClickAddComment(\"" + posts[i].id + "\")'>Add comment</button>";

            image_list[0].innerHTML += html;
        }
    }).fail(function() {
       console.log("fail");
    });
}

function onClickGetImageThumbnail() {
    webappAjax({
        url: "/post/getthumbnail/"
        , type: "GET"
    }).done(function (data) {
        var posts = data.posts;
        var image_list = $("#get_image div");

        for (var i=0; i<posts.length; i++) {
            var html = "<img src=" + posts[i].image.src + ">";
            image_list[0].innerHTML += html;

            mydebug_info(posts[i].user.id);
        }
    }).fail(function () {
        console.log("fail");
    })
}

function onClickAddComment(post) {
    console.log("add comment");
    webappAjax({
        url: "/post/comment/create/"
        , type: "POST"
        , data: {
            post: post
            , content: "this is a comment"
        }
    }).done(function(data) {
        onClickGetImage();
    }).fail(function() {
      console.log("Oops")
    });
}

function onClickDeleteImage(post) {
    console.log("delete image");
    webappAjax({
        url: "/post/delete/"
        , type: "POST"
        , data: {
            post_id: post
        }
    }).done(function(data) {
        console.log("Image deleted");
    }).fail(function(xhr) {
        console.log(xhr);
    });
}