/**
 * Aperture JS - Local Stub Implementation
 *
 * This is a minimal stub for the Aperture JavaScript visualization framework.
 * Replace with actual implementation for production use.
 */
(function(window) {
    'use strict';

    var aperture = window.aperture || {};

    // Core namespace
    aperture.VERSION = '1.0.9.1-local';

    // Utility functions
    aperture.util = {
        isString: function(obj) {
            return typeof obj === 'string';
        },
        isNumber: function(obj) {
            return typeof obj === 'number' && isFinite(obj);
        },
        isArray: function(obj) {
            return Object.prototype.toString.call(obj) === '[object Array]';
        },
        isFunction: function(obj) {
            return typeof obj === 'function';
        },
        isObject: function(obj) {
            return obj && typeof obj === 'object';
        },
        extend: function(dest) {
            var sources = Array.prototype.slice.call(arguments, 1);
            for (var i = 0; i < sources.length; i++) {
                var src = sources[i];
                if (src) {
                    for (var key in src) {
                        if (src.hasOwnProperty(key)) {
                            dest[key] = src[key];
                        }
                    }
                }
            }
            return dest;
        },
        forEach: function(obj, fn, context) {
            if (obj == null) return;
            if (Array.prototype.forEach && obj.forEach === Array.prototype.forEach) {
                obj.forEach(fn, context);
            } else if (obj.length === +obj.length) {
                for (var i = 0, length = obj.length; i < length; i++) {
                    fn.call(context, obj[i], i, obj);
                }
            } else {
                for (var key in obj) {
                    if (obj.hasOwnProperty(key)) {
                        fn.call(context, obj[key], key, obj);
                    }
                }
            }
        }
    };

    // Config stub
    aperture.config = {
        get: function(key, defaultValue) {
            return defaultValue;
        },
        register: function(module, config) {
            // Stub
        }
    };

    // Log stub
    aperture.log = {
        level: 'info',
        info: function() {
            if (window.console && window.console.log) {
                window.console.log.apply(window.console, arguments);
            }
        },
        warn: function() {
            if (window.console && window.console.warn) {
                window.console.warn.apply(window.console, arguments);
            }
        },
        error: function() {
            if (window.console && window.console.error) {
                window.console.error.apply(window.console, arguments);
            }
        },
        debug: function() {
            if (window.console && window.console.log) {
                window.console.log.apply(window.console, arguments);
            }
        }
    };

    // IO stub for REST calls
    aperture.io = {
        rest: function(url, method, callback, options) {
            // Basic REST implementation using XMLHttpRequest
            var xhr = new XMLHttpRequest();
            xhr.open(method || 'GET', url, true);

            if (options && options.contentType) {
                xhr.setRequestHeader('Content-Type', options.contentType);
            }

            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (callback) {
                        callback(xhr.responseText, {
                            status: xhr.status,
                            success: xhr.status >= 200 && xhr.status < 300
                        });
                    }
                }
            };

            xhr.send(options && options.postData);
        }
    };

    // Class definition helper
    aperture.Class = function() {};
    aperture.Class.extend = function(props) {
        var parent = this;
        var child = function() {
            if (props.init) {
                props.init.apply(this, arguments);
            }
        };

        child.prototype = Object.create(parent.prototype);
        child.prototype.constructor = child;

        for (var key in props) {
            if (props.hasOwnProperty(key)) {
                child.prototype[key] = props[key];
            }
        }

        child.extend = parent.extend;
        return child;
    };

    window.aperture = aperture;

})(window);
