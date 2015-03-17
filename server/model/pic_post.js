/**
 * Created by imlyc on 3/6/15.
 */

var mongoose = require("mongoose")
    , Schema = mongoose.Schema
    , ObjId = Schema.Types.ObjectId
    , app_config = require("../config")
    , path = require("path")
    , image_plugin = require("../util/image").imagePlugin
;

var schema = new Schema({
    user_id : {type : ObjId, ref : "User"}
    , desc : String
    , comments : [ObjId]
    , pub_date : Date
});

schema.plugin(image_plugin, {
    name : "image"
    , upload_to : function(file) {
        return path.join(app_config.IMAGE_DIR, this.user_id.toString(), file.name);
    }
    , relative_to : function(file) {
        return "/image/" + this.user_id.toString() + "/" + file.name;
    }
});

schema.method("getJsonPublic", function(user_json) {
    return {
        id : this._id
        , user : user_json
        , desc : this.desc
        , image : {
            src : this.image.rel
        }
        , comments : this.comments
    };
});

module.exports = mongoose.model("PicPost", schema);
