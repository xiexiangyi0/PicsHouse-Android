/**
 * Created by imlyc on 3/6/15.
 */
var mongoose = require("mongoose")
    , Schema = mongoose.Schema
    , ObjId = Schema.Types.ObjectId
;

var schema = new Schema({
    pic : String
    , user : ObjId
    , desc : String
    , comments : [ObjId]
    , pub_date : Date
});

module.exports = mongoose.model("PicPost", schema);
