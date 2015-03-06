var mongoose = require("mongoose")
    , Schema = mongoose.Schema
    , user_plugin = require("../util/auth").mongoose_user_plugin;

var schema = new Schema({
    username : {type : String, required : true, index : {unique : true}}
    , email : {type : String, required : true, index : {unique : true}}
    , token : {type : String, required : true, index : {unique : true}}
});

schema.plugin(user_plugin);

module.exports = mongoose.model("User", schema);
