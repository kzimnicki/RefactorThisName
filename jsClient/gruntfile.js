'use strict';
var path = require('path');

module.exports = function(grunt) {

  grunt.initConfig({
    express: {
      defaults: {
        options: {
          server: path.resolve('./test/fixtures/express/lib/server'),
          serverreload: true
        }
      }
    }
  });
};