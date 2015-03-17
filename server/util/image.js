/**
 * Created by imlyc on 3/16/15.
 */

var mongoose = require("mongoose")
    //, Schema = mongoose.Schema
    , fs = require("fs")
    , path = require("path")
    , mkdirp = require("mkdirp")
    , file_plugin = require("mongoose-file").filePlugin
;

var imagePlugin = function(schema, option) {

    var upload_to = null;
    var pathname = option.name || "image";

    if(option.upload_to) {
        upload_to = option.upload_to;
        delete option.upload_to;
    }

    schema.plugin(file_plugin, option);

    schema.method("setFile", function(image_file, cb) {

        var _this = this;

        if(upload_to) {
            var dest = "";
            if (typeof upload_to === "function") {
                dest = upload_to.call(_this, image_file);
            } else {
                dest = path.join(upload_to, image_file.name);
            }

            var dest_dir = path.dirname(dest);

            mkdirp(dest_dir, function (err) {
                if (err) {

                    return cb(err);
                }

                fs.rename(image_file.path, dest, function (err) {
                    if (err) {
                        return cb(err);
                    }

                    image_file.path = dest;
                    _this[pathname].file = image_file;

                    return cb(null, _this, image_file);
                });
            });
        } else {

            _this[pathname].file = image_file;
            return cb(null, _this, image_file);

        }

    });
};

module.exports = {
    imagePlugin : imagePlugin
};
