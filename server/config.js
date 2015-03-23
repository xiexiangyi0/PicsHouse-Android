/**
 * Created by imlyc on 3/12/15.
 */

var path = require("path");

var _base_dir = __dirname;
var _tmp_dir = path.join(_base_dir, "public/tmp");
var _image_dir = path.join(_base_dir, "public/image");

module.exports = {
    BASE_DIR : _base_dir
    , TMP_DIR : _tmp_dir
    , IMAGE_DIR : _image_dir
    , MONGODB_ADDR : "mongodb://localhost:27017/mydb"
    , FB_CLIENT_ID : "1036438616383973"
    , FB_CLIENT_SECRET : "b8dfeb819d42f9f48ef36c3c7fd5ca6b"
};
