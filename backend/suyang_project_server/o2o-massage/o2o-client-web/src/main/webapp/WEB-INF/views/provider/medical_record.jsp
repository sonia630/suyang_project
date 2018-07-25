<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
    <jsp:include page="../include/header.jsp"/>
</head>

<body>
<!-- Topbar -->
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">疾病/症状表现</li>
        <li class="am-u-sm-1 am-text-right"></li>
    </ul>
    <div class="process am-dropdown-up">
        <ul class="am-dropdown-content">
            <li>已填写0%</li>
        </ul>
        <div class="weui-progress">
            <div class="weui-progress__bar">
                <div class="weui-progress__inner-bar js_progress" style="width: 0%;"></div>
            </div>
        </div>

    </div>
</div>

<!-- service order-->
<div id="item-form">
    <div class="layout-case open-case">
        <form class="am-form am-form-horizontal">
            <h2>宝宝信息</h2>
            <ul class="am-list">
                <li class="am-g ">
                    <div class="am-u-sm-12">
                        <label class="am-margin-right-lg">宝宝姓名</label><label class="am-margin-right-lg">男</label><label>五岁三个月</label>
                    </div>
                </li>

            </ul>
            <h2>主诉症状</h2>
            <ul class="am-list">
                <li class="am-g">
                 <textarea name="" class="am-form-field  am-radius" placeholder="主诉症状" cols="30"
                           rows="4"></textarea>
                </li>
            </ul>

            <h2>医学资料</h2>
            <div class="weui-cells weui-cells_form">
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <div class="weui-uploader">
                            <div class="weui-uploader__bd">
                                <ul class="weui-uploader__files" id="uploaderFiles">
                                    <li class="weui-uploader__file" style="background-image:url(../../../images/download.png)"></li>
                                </ul>
                                <div class="weui-uploader__input-box">
                                    <input id="uploaderInput" class="weui-uploader__input" type="file"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <h2>疾病/症状表现
                <button type="button" class="am-btn-success am-fr am-btn am-margin-right-sm add" type="button" onclick="addItem()">
                    <i class="am-icon-plus"></i> 新增
                </button>
            </h2>


            <div class="page__bd" id="swiped">
                <div class="weui-cells">
                    <div class="weui-cell weui-cell_swiped">
                        <div class="weui-cell__bd">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <p>
                                        <i class="weui-icon-success"></i>
                                        <label>咳嗽</label>
                                        <span>白天咳嗽，干咳，夜间不咳嗽</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="weui-cell__ft">
                            <a class="weui-swiped-btn weui-swiped-btn_warn delete-swipeout" href="javascript:">删除</a>
                            <a class="weui-swiped-btn weui-swiped-btn_default modify-swipeout" href="javascript:">修改</a>
                        </div>
                    </div>


                </div>

                <div class="weui-cells">
                    <div class="weui-cell weui-cell_swiped">
                        <div class="weui-cell__bd">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <p>
                                        <i class="weui-icon-success"></i>
                                        <label>咳嗽</label>
                                        <span>白天咳嗽，干咳，夜间不咳嗽</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="weui-cell__ft">
                            <a class="weui-swiped-btn weui-swiped-btn_warn delete-swipeout" href="javascript:">删除</a>
                            <a class="weui-swiped-btn weui-swiped-btn_default modify-swipeout" href="javascript: ">修改</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="weui-loadmore weui-loadmore_line">
                <span class="weui-loadmore__tips">展开更多</span>
            </div>

        </form>

    </div>

    <!-- commit -->
    <div class="am-navbar navbar-button am-g">
        <div class="am-u-sm-12">
            <button type="button" class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg">下一步</button>
        </div>
    </div>
</div>
<div id="add-form" style="display: none">
    <form class="am-form am-form-horizontal">
        <div class="layout-case open-case">
            <form class="am-form am-form-horizontal">
                <h2>疾病/症状表现</h2>
                <input name="illness" style="margin:5px 1% 15px;width:98%;border: 1px solid #e8e8e8;" type="text" placeholder="">
                <h2>详细描述</h2>
                <textarea name="desc" class="am-form-field  am-radius" placeholder="详细描述" cols="30"
                          rows="12"></textarea>
            </form>
        </div>
    </form>
    <!-- commit -->
    <div class="am-navbar navbar-button am-g">
        <div class="am-u-sm-5">
            <button type="button" class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg" onclick="saveItem()">保存</button>
        </div>
        <div class="am-u-sm-5">
            <button type="button" class="am-btn am-btn-block am-btn-default am-vertical-align-middle am-btn-lg" onclick="cancel()">取消</button>
        </div>
    </div>
</div>


<jsp:include page="../include/js.jsp"/>
<script>
    function addItem () {
        $('#item-form').hide()
        $('#add-form').show()
    }

    function saveItem () {
        let saveItemform = $('#add-form form').serializeArray()
        let html = itemHtml(saveItemform)
        $('#swiped').prepend(html)
        $('.weui-cell_swiped').swipeout()
        cancel()
    }

    function itemHtml (saveItemform) {
        if (!saveItemform) {
            return ''
        }
        let html = "<div class='weui-cells'>
                    <div class='weui-cell weui-cell_swiped'>
                        <div class='weui-cell__bd'>
                            <div class='weui-cell'>
                                <div class='weui-cell__bd'>
                                    <p>
                                        <i class='weui-icon-success'></i>
                                        <label>${saveItemform[0].value}</label>
                                        <span>${saveItemform[1].value}</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class='weui-cell__ft'>
                            <a class='weui-swiped-btn weui-swiped-btn_warn delete-swipeout' href='javascript:'>删除</a>
                            <a class='weui-swiped-btn weui-swiped-btn_default modify-swipeout' href='javascript: '>修改</a>
                        </div>
                    </div>
                </div>"
        console.log('html', html)

        return html

    }

    $('.open-case').delegate('.delete-swipeout', 'click', function () {
        console.log('delete...')
        $(this).parent().parent().parent().remove()
    })
    $('.open-case').delegate('.modify-swipeout', 'click', function () {
        let $pDom = $(this).parent().parent().parent().find('p')
        let illness = $pDom.find('label').text()
        let desc = $pDom.find('span').text()
        $(this).parent().parent().parent().remove()
        addItem()

        $("#add-form form input[name='illness']").val(illness)
        $("#add-form form textarea[name='desc']").val(desc)
    })

    function cancel () {
        $('#item-form').show()
        $('#add-form').hide()
    }

    function deleteItem () {
        console.log('delete...')
    }

    function modifyItem () {
        console.log('modifyItem...')
    }
</script>

</body>

</html>
