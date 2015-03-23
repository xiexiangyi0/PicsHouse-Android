var mongoose = require("mongoose")
    , Schema = mongoose.Schema
    , user_plugin = require("../util/auth").mongoose_user_plugin;

var schema = new Schema({
    username : {type : String, required : true, index : {unique : true}}
    , email : {type : String, required : true, index : {unique : true}}
    , token : {type : String, required : true, index : {unique : true}}

    , gender : {type : Number, min : 0, max : 1} // male 1, femail 0

    , avatar : String

    , join_date : Date
    , last_login : Date

});

schema.plugin(user_plugin);

schema.method("getJsonPublic", function() {
    return {
        id : this._id
        , username : this.username
        , email : this.email
        , gender : this.gender
    }
});

module.exports = mongoose.model("User", schema);
