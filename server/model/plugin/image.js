/**
 * Created by imlyc on 3/16/15.
 */

var fs = require("fs")
    , path = require("path")
    , mkdirp = require("mkdirp")
    , file_plugin = require("mongoose-file").filePlugin
;

var mydebug = require("../../util/debug");

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

        var setFileOnly = function() {
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
        };

        // remove if already set
        var old_file = _this[pathname].path;

        if (old_file) {
            mydebug.log("[WARNING] file exist: " + old_file + " remove first");
            fs.exists(old_file, function(exist) {
                if (exist) {
                    fs.unlink(old_file, function() {
                        setFileOnly();
                    });
                } else {
                    setFileOnly();
                }
            });
        } else {
           setFileOnly();
        }
    });

    schema.pre("remove", function(next) {
        var _this = this;
        mydebug.log("path = " + _this[pathname].file);
        fs.unlink(_this[pathname].path, function() {
            next();
        });
    });
};

module.exports = {
    imagePlugin : imagePlugin
};
