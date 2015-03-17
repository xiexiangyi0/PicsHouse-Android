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

            $("#profile .screen").html("user name = " + data.username + " email = " + data.email);

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
    }).fail(function() {
        console.log("Fail");
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

    }).fail(function() {
        console.log("fail");
    });
}