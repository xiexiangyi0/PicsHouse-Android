var express = require('express');
//var express_session =require("express-session");
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var auth = require("./util/auth");

var routes = require('./routes/index');

var app = express();

//database setup
var mongoose = require("mongoose");
var conn = "mongodb://localhost:27017/mydb";
mongoose.connect(conn, function(err) {
    if(err) {
        console.log("Fail to connect db");
        throw err;
    }

    console.log("Success to connect db");
});

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(auth.middleware);
app.use('/', routes);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
    console.log("error: ");
    console.log(err);
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});


module.exports = app;

function destructor() {
    mongoose.disconnect(function() {
        process.exit();
    });
}

process.on("SIGINT", destructor);
process.on("SIGTERM", destructor);
