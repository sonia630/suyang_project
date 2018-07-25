var EventsList = function(options) {
    var $list = $('#events-list');
    var $pullDown = $('#pull-down');
    var $pullUp = $('#pull-up');

    this.compiler = Handlebars.compile($('#tpi-list-item').html());
    this.prev = this.next = this.start = options.params.start;
    this.total = null;

    this.getOptions=function () {
        return options;
    };

    this.getURL = function(params) {
        var queries = [];
        for (var key in  params) {
            if (key !== 'start') {
                queries.push(key + '=' + params[key]);
            }
        }
        queries.push('start=');
        return options.api + '?' + queries.join('&');
    };

    this.renderList = function(start, type) {
        var _this = this;
        var $el = $pullDown;

        if (type === 'load') {
            $el = $pullUp;
        }

        $.getJSON(this.URL + start).then(function(e) {
            if (e.total == 0 || e.total == e.data.length) {
                $(".weui-loadmore").html("");
            }
            _this.total = e.total;
            var html = _this.compiler(e.data);
            if (type === 'refresh') {
                $list.children('li').first().before(html);
            } else if (type === 'load') {
                $list.append(html);
            } else {
                $list.html(html);
            }
        }, function() {
            $.toptip(e.message, e.status);
        });
    };

    this.setLoading = function($el) {
        $el.addClass('loading');
    };

    this.resetLoading = function($el) {
        $el.removeClass('loading');
    };

    this.init = function() {
        this.URL = this.getURL(options.params);
        this.renderList(options.params.start);
    };

    this.handlePullDown = function() {
        console.log('handle pull down');
        if (this.prev > 0) {
            this.setLoading($pullDown);
            this.prev -= options.params.count;
            this.renderList(this.prev, 'refresh');
        } else {
            console.log('别刷了，没有了');
        }
    };

    this.handlePullUp = function() {
        console.log('handle pull up');
        if (this.next < this.total) {
            this.setLoading($pullUp);
            this.next += options.params.count;
            this.renderList(this.next, 'load');
        } else {
            $(".weui-loadmore").html("");
        }
    }
};