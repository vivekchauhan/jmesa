/*global jQuery*/
/*jslint evil: true*/
// verified with JSLint at http://www.jslint.com/

// We'll wrap this in a function to properly scope the variables and so that
// we can safely use '$' instead of 'jQuery'.
(function ($) {

    if (!$) {
        throw "jQuery is required for the JMesa library.";
    }

    var global = this;  // I can't imagine why this wouldn't be window, but
    // we'll do it like this just in case.

    // Dynamically download the jQuery plugin if needed.
    // This is just a hack for backwards-compatibility.  If it does not work
    // right, add the jquery.jmesa.js script to your pages manually.
    if (!$.jmesa) {
        $("script[src$='jmesa.js']").each(function () {
            var src = this.src.replace(/jmesa\.js$/, 'jquery.jmesa.js');
            var success = false;
            $.ajax({
                url : src,
                async : false,
                type : 'GET',
                success : function (data) {
                    eval(data);  // as jslint says, "eval is evil"...
                    // if you're relying this automatic download
                    // feature, in the near future your JSPs
                    // should be updated to include the script
                    // properly.
                    success = true;
                },
                error : function () {
                    success = false;
                }
            });
            return !success;
        });
    }

    // Copy the JMesa functions and classes into the global namespace.
    $.extend(global, $.jmesa);

    // See if the legacy onInvokeAction and onInvokeExportAction are used.
    // If an invoke action has not already been set, we will use the global
    // invoke* actions for backwards compatibility.
    $(function () {
        $.each(['invokeAction', 'invokeExportAction'], function (i, name) {
            var eventName = 'on' + name.charAt(0).toUpperCase() + name.substring(1);
            var fn = global[eventName];
            if (typeof(fn) === 'function') {
                $.jmesa.getAllTables().each(function () {
                    var $this = $(this);
                    if (!$this.hasJMesaEvent(name)) {
                        $this[name](function (action) {
                            fn(this.id, action);
                        });
                    }
                });
            }
        });
    });

})(jQuery);