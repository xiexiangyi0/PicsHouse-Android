var mongoose = require("mongoose")
    , Schema = mongoose.Schema
    , bcrypt = require("bcrypt")
    , SALT_WORK_FACTOR = 10
    , image_plugin = require("./plugin/image").imagePlugin
    , path = require("path")
    , app_config = require("../config")
    ;

var schema = new Schema({
    username : {type : String, required : true, index : {unique : true}}
    , email : {type : String, required : true, index : {unique : true}}
    , password : {type : String, required : true}
    , token : {type : String, required : true, index : {unique : true}}
    , gender : {type : Number, min : 0, max : 1} // male 1, femail 0
    , facebook : {id:String, first_name : String, last_name : String}
    , join_date : Date
    , last_login : Date
});

schema.plugin(image_plugin, {
    name : "avatar"
    , upload_to : function(file) {
        return path.join(app_config.AVATAR_DIR, this._id.toString(), file.name);
    }
    , relative_to : function(file) {
        return "/avatar/" + this._id.toString() + "/" + file.name;
    }
});

//hash password
schema.pre('save', function(next) {
    var user = this;

    // only hash the password if it has been modified (or is new)
    if (!user.isModified('password')) return next();

    // generate a salt
    bcrypt.genSalt(SALT_WORK_FACTOR, function(err, salt) {
        if (err) return next(err);

        // hash the password along with our new salt
        bcrypt.hash(user.password, salt, function(err, hash) {
            if (err) return next(err);

            // override the cleartext password with the hashed one
            user.password = hash;
            next();
        });
    });
});

schema.methods.comparePassword = function(candidatePassword, cb) {
    bcrypt.compare(candidatePassword, this.password, function(err, isMatch) {
        if (err) return cb(err);
        cb(null, isMatch);
    });
};

schema.method("getJsonPublic", function() {
    return {
        id : this._id
        , username : this.username
        , email : this.email
        , gender : this.gender
        , avatar : {
            src : this.avatar.rel
        }
    }
});

module.exports = mongoose.model("User", schema);
