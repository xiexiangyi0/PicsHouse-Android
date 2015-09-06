/**
 * Created by imlyc on 9/6/15.
 */

errorSystem = function(err, next) {
    throw err;
};

errorSyntax = function(res) {
    res.status(403);
    res.send({ecode: "syntax_error"});
};

errorNoObj = function(res) {
    res.status(403);
    res.send({ecode: "invalid_obj"});
};

module.exports = {
    errorHandler : errorSystem
    , errorSyntax : errorSyntax
    , errorNoObj : errorNoObj
};
