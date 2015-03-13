/**
 * Created by imlyc on 3/6/15.
 */
var mongoose = require("mongoose")
    , Schema = mongoose.Schema
    , ObjId = Schema.Types.ObjectId
    , file_plugin = require("mongoose-file").filePlugin
    , app_config = require("../config")
    , path = require("path")
;

var schema = new Schema({
    user : ObjId
    , desc : String
    , comments : [ObjId]
    , pub_date : Date
});

schema.plugin(file_plugin, {
    name : "image"
    , upload_to : function (file) {
        var p =  path.join(app_config.IMAGE_DIR, this.user.toString());
        p = path.join(p, file.name);

        return p;
    }
    , relative_to : function(file) {
        return app_config.IMAGE_DIR;

    }
    /*
    , change_cb : function(field_name, new_path, old_path) {

        console.log("file-plugin");
        console.log(field_name);
        console.log(new_path);
        console.log(old_path);

    }*/
});


schema.pre("save", function(next) {

    console.log("pre save");

//    this.image.file = this.image;

    console.log(this.image);

    return next();

});


module.exports = mongoose.model("PicPost", schema);
