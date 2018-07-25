var cityName = '北京市'; // 重要！选址会用到
var cartInfo = {};
cartInfo.location = ""; // 重要

//弹出层控制
var input_location = cartInfo.location;
var map;
function close_address() {
    map = null;
    if (timer != null) {
        window.clearTimeout(timer);
    }
    $('#commonAddress').val('');
}

var timer = null;
var oldinput;

function address_changed() {
    if (map) {
        var newaddr = $('#commonAddress').val();
        if (newaddr == undefined) {
            close_address();
            return;
        }
        if (newaddr.length < 1) {
            $('#autocomplete').children().remove();
            return;
        }
        if (oldinput != newaddr && newaddr != input_location) {
            oldinput = $('#commonAddress').val();
            searchaddress(oldinput);
        }
    }
}

var local;

function searchaddress(keyword) {
    // 清空相关数据
    addrlist = new Array();
    $('#autocomplete').children().remove();
    if (keyword.length > 1) {
        local = new BMap.LocalSearch(map, { //智能搜索
            onSearchComplete: function (results) {
                myFun(results);
            }
        });
        var city = cityName;
        local.search(city + keyword);
    }
}

function myFun(results) {
    $(".autocomplete").show();
    $('#autocomplete').children().remove();

    if (local.getStatus() == 2 || local.getStatus() == BMAP_STATUS_SUCCESS) {
        for (var i = 0; i < results.getCurrentNumPois(); i++) {
            var obj = results.getPoi(i);
            addrlist[addrlist.length] = {
                city: obj.city,
                title: obj.title, address: obj.address,
                point: obj.point
            };
            $('<li index="' + i + '">' + obj.title + '<div>'
              + obj.address + '</div></li>').appendTo($('#autocomplete')).slideDown();
        }
    } else {
        console.log('搜索失败： ' + local.getStatus());
    }

    $('#autocomplete').children().click(function () {
        var index = $(this).attr('index');
        var addr = addrlist[index];
        if (addr.city == undefined || addr.city.length < 1) {
            alert('对不起，您选择的地址不是具体的地址，请重新输入或选择');
            return;
        }
        var addrinfo = addr.title + '(' + addr.address + ')';
        $('#commonAddress').val(addrinfo);
        $("#latitude").val(addr.point.lat);
        $("#longitude").val(addr.point.lng);
        $("#autocomplete").html("");
        $(".autocomplete").hide();
    });
}

function start_map() {
    map = new BMap.Map("map");
}