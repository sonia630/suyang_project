<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/taglib.jsp"%>
<style>

    .jarviswidget > div {
        border-width: 0;
    }

    .smart-form footer {
        background: none;
    }
</style>

<script>
    $(document).ready(function () {
        validateForm($("#inputForm"));
    });
</script>

<div class="jarviswidget" id="wid-id-8" data-widget-editbutton="false"
     data-widget-custombutton="false">
    <div>
        <div class="jarviswidget-editbox">
            <!-- This area used as dropdown edit box -->
        </div>
        <!-- end widget edit box -->

        <!-- widget content -->
        <div class="widget-body no-padding">
            <form action="/sys/menu/save" method="post" id="inputForm" class="smart-form">
                <input type="hidden" name="id" value="${menu.id}"/>
                <fieldset>
                    <div class="row">
                        <section class="col col-6">
                            <label class="control-label">上级菜单:</label>
                            <label class="input">
                                <tags:treeselect id="permission" name="parentId"
                                                 value="${menu.parent.id}" labelName="pname"
                                                 labelValue="${menu.parent.name}" title="菜单"
                                                 url="/sys/menu/treeData"
                                                 extId="${menu.id}"/>
                            </label>
                        </section>
                        <section class="col col-6">
                            <label class="control-label">名称:</label>
                            <label class="input">
                                <input type="text" name="name"
                                       value="${menu.name}" maxlength="50" required/>
                            </label>

                            <div class="help-block with-errors"></div>
                        </section>
                    </div>
                    <div class="row">
                        <section class="col col-6">
                            <label class="control-label">链接:</label>
                            <label class="input">
                                <input type="text" name="url"
                                       value="${menu.url}" maxlength="200" required/>
                            </label>

                            <div class="help-block with-errors"></div>
                        </section>
                        <section class="col col-6">
                            <label class="control-label">排序:</label>
                            <label class="input">
                                <input type="text" name="sort"
                                       value="${menu.sort}" required/>
                            </label>

                            <div class="help-block with-errors"></div>
                        </section>
                    </div>
                    <div class="row">
                        <section class="col col-3">
                            <label class="control-label">图标:</label>
                            <label class="input">
                                <tags:iconselect id="icon" name="menuIcon"
                                                 value="${menu.menuIcon}"></tags:iconselect>
                            </label>
                        </section>
                        <section class="col col-3">
                            <label class="control-label">可见:</label>

                            <div class="inline-group">
                                <label class="radio">
                                    <input id="show" type="radio"
                                           name="show" value="1"
                                           <c:if test="${menu.show eq 1}">checked="checked"</c:if>
                                           <c:if test="${empty menu.show}">checked="checked"</c:if>/>
                                    <i></i>显示
                                </label>
                                <label class="radio">
                                    <input id="hide" type="radio"
                                           name="show" value="0"
                                           <c:if test="${menu.show eq 0}">checked="checked"</c:if>/>
                                    <i></i>隐藏
                                </label>
                            </div>
                        </section>
                    </div>
                </fieldset>
                <footer>
                    <input id="btnSubmit" class="btn btn-primary" type="submit"
                           value="保 存"/>&nbsp; <input id="btngoback" class="btn btn-primary"
                                                      type="button"
                                                      value="返 回"
                                                      onclick="javascript:backTo('/sys/menu/')"/>
                </footer>
            </form>
        </div>
    </div>
</div>