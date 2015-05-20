/**
 * Created by imlyc on 5/19/15.
 */

var mongoose = require("mongoose")
    , Schema = mongoose.Schema
    , ObjId = Schema.Types.ObjectId
;

var schema = new Schema({
    user_id : {type: ObjId, ref: "User"}
    , content : String
    , pub_date : Date
    , reply_to : {type: ObjId, ref: "Comment"}
    , post : {type: ObjId, ref: "PicPost"}
});

module.exports = mongoose.model("Comment", schema);
