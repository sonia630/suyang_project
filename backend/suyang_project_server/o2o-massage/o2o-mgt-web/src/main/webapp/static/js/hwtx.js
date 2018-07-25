var HWTX = {
    regPlugins: [], // [function($parent){} ...]
    // sbar: show sidebar
    keyCode: {
        ENTER: 13, ESC: 27, END: 35, HOME: 36,
        SHIFT: 16, TAB: 9,
        LEFT: 37, RIGHT: 39, UP: 38, DOWN: 40,
        DELETE: 46, BACKSPACE: 8
    },
    eventType: {
        pageClear: "pageClear",	// 用于重新ajaxLoad、关闭nabTab, 关闭dialog时，去除xheditor等需要特殊处理的资源
        resizeGrid: "resizeGrid"	// 用于窗口或dialog大小调整
    },
    isOverAxis: function (x, reference, size) {
        //Determines when x coordinate is over "b" element axis
        return (x > reference) && (x < (reference + size));
    },
    isOver: function (y, x, top, left, height, width) {
        //Determines when x, y coordinates is over "b" element
        return this.isOverAxis(y, top, height) && this.isOverAxis(x, left, width);
    },

    pageInfo: {pageNum: "pageNum", numPerPage: "numPerPage", orderField: "orderField", orderDirection: "orderDirection"},
    statusCode: {ok: 200, error: 300, timeout: 301},
    keys: {statusCode: "statusCode", message: "message"},
    ui: {
        sbar: true,
        hideMode: 'display' //navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
    },
    frag: {}, //page fragment
    _msg: {}, //alert message
    _set: {
        loginUrl: "", //session timeout
        loginTitle: "", //if loginTitle open a login dialog
        debug: false
    },
    msg: function (key, args) {
        var _format = function (str, args) {
            args = args || [];
            var result = str || "";
            for (var i = 0; i < args.length; i++) {
                result = result.replace(new RegExp("\\{" + i + "\\}", "g"), args[i]);
            }
            return result;
        }
        return _format(this._msg[key], args);
    },
    debug: function (msg) {
        if (this._set.debug) {
            if (typeof(console) != "undefined") console.log(msg);
            else alert(msg);
        }
    },

    gDialogCreate: function (title, url, param, width, height) {
        gDialog.fCreate({
            title: title,
            url: url,
            param: param,
            width: width || 880,
            height: height || 400
        }).show();
    },
    refreshTablePipeline: function (table) {
        var $table = $(table).DataTable();
        cacheLower = -1;
        $table.ajax.reload();
    },
    refreshTable: function (tableId, param) {
        var table = $(tableId).DataTable();
        var oSettings = table.fnSettings();
        $.getJSON(oSettings.sAjaxSource, param, function (json) {
            table.fnClearTable(this);
            for (var i = 0; i < json.aaData.length; i++) {
                table.oApi._fnAddData(oSettings, json.aaData[i]);
            }
            oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
            table.fnDraw(false);
        });
    },
    gDialogClose: function () {
        gDialog.fClose();
    }
};


/**
 * You can use this map like this:
 * var myMap = new Map();
 * myMap.put("key","value");
 * var key = myMap.get("key");
 * myMap.remove("key");
 */
function Map() {

    this.elements = new Array();

    this.size = function () {
        return this.elements.length;
    }

    this.isEmpty = function () {
        return (this.elements.length < 1);
    }

    this.clear = function () {
        this.elements = new Array();
    }

    this.put = function (_key, _value) {
        this.remove(_key);
        this.elements.push({key: _key, value: _value});
    }

    this.remove = function (_key) {
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    this.elements.splice(i, 1);
                    return true;
                }
            }
        } catch (e) {
            return false;
        }
        return false;
    }

    this.get = function (_key) {
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    return this.elements[i].value;
                }
            }
        } catch (e) {
            return null;
        }
    }

    this.element = function (_index) {
        if (_index < 0 || _index >= this.elements.length) {
            return null;
        }
        return this.elements[_index];
    }

    this.containsKey = function (_key) {
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    return true;
                }
            }
        } catch (e) {
            return false;
        }
        return false;
    }

    this.values = function () {
        var arr = new Array();
        for (i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].value);
        }
        return arr;
    }

    this.keys = function () {
        var arr = new Array();
        for (i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].key);
        }
        return arr;
    }
}
