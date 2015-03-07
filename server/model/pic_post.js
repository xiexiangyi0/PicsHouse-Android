/**
 * Created by imlyc on 3/6/15.
 */
var mongoose = require("mongoose")
    , Schema = mongoose.Schema
    , ObjId = mongoose.Types.ObjectId
;

var schema = new Schema({
    pic : String
    , user : ObjId
    , comments : [ObjId]
    , pub_date : Date
});

module.exports = mongoose.model("PicPost", schema);
